/**
 * Created by lyriccn on 18/3/27.
 */

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Server {
    public void service() throws Exception {// 创建基于流的Socket,并在8000 端口监听
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println(" 服务器启动......");

        Executor cachedThread1 = Executors.newFixedThreadPool(1);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("连接中...");
            ServerThread st = new ServerThread(socket);
            cachedThread1.execute(st);
        }

    }

    public static void main( String args[]) throws Exception {
        Server server = new Server();
        server.service();
    }
}

class ServerThread implements Runnable {
    private Socket client;
    InputStream in;
    ObjectInputStream ois;
    OutputStream out;
    ObjectOutputStream oos;

    public ServerThread(Socket client) throws IOException {
        this.client = client;// 初始化client变量
        this.in = this.client.getInputStream();
        this.ois = new ObjectInputStream(in);
        this.out = this.client.getOutputStream();
        this.oos = new ObjectOutputStream(out);

    }

    public void run() {// 线程主体
        System.out.println("连接成功.......");
        //接收客户发送的Call 对象
        RemoteCall remotecallobj = null;
        try {
            remotecallobj = (RemoteCall) ois.readObject();
            System.out.println(remotecallobj);
            // 调用相关对象的方法
            System.out.println("calling......");
            remotecallobj = invoke(remotecallobj);
            // 向客户发送包含了执行结果的remotecallobj 对象
            oos.writeObject(remotecallobj);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ois.close();
            oos.close();
            client.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public RemoteCall invoke( RemoteCall call) {
        Object result = null;
        try {
            String className = call.getClassName();
            String methodName = call.getMethodName();
            Object[] params = call.getParams();
            Class<?> classType = Class.forName(className);
            Class<?>[] paramTypes = call.getParamTypes();
            Method method = classType.getMethod(methodName, paramTypes);
            // 从配置文件中取出相关的远程对象Object
            Object remoteObject =ConfigReader.register(className);
            if (remoteObject == null) {
                throw new Exception(className + " 的远程对象不存在");
            } else {
                System.out.println("method开始执行");
                //可以执行方法 方法内部有错误
                result = method.invoke(remoteObject, params);
                //
                System.out.println("method的result="+result);
                System.out.println("远程调用结束:remoteObject:"+remoteObject.toString()+",params:"+params.toString());
            }
        } catch (Exception e) {
            System.out.println("error"+e.getMessage());
            result = null;
        }
        System.out.println("result="+result);
        call.setResult(result);
        return call;
    }

}
