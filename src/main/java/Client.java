/**
 * Created by lyriccn on 18/3/27.
 */

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

//import rpc.client.DynamicProxyFactory;

public class Client {
    private final static String CLASS_PATH="rpc.clientRemote.";

    public static void main(String[] args) throws Exception {

        RPCService service=(RPCService)DynamicProxyFactory.getProxy(RPCService.class, "localhost", 8888);//new RPCServiceImpl();
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("请选择：1-充值；0-消费；-1-exit");
            int type=sc.nextInt();
            if(type==-1){
                break;
            }
            System.out.println("请输入卡号");
            String cardId=sc.next();

            System.out.println("请输入充值/消费金额");
            int amount=sc.nextInt();
            Card card=new Card(cardId,amount);
            if(type==1){
                //充值
                String result=service.recharge(card);
                System.out.println("结果为"+result);
            }
            if(type==0){
                //消费
                String result= service.consumu(card);
                System.out.println("结果为"+result);
            }
        }
        //RPCServiceImpl service1=(RPCServiceImpl)DynamicProxyFactory.getProxy(RPCService.class, "localhost", 8000);
        //RPCService service=new RPCServiceImpl();
//        String result= service.request("你好！");
//        System.out.println("结果为："+result);
//        String result;
//         result=invoke("你好！");
         //System.out.println("远程执行结果为："+result);
    }




    public static String invoke(String infomation) throws Exception {
        Socket socket = new Socket("localhost", 8000);
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        InputStream in = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(in);
        RemoteCall call = new RemoteCall(CLASS_PATH+"RPCService", "request", new Class[]{String.class}, new Object[]{infomation});// 向服务器发送Call 对象
        oos.writeObject(call);
        //接收包含了方法执行结果的Call 对象
        call = (RemoteCall) ois.readObject();
        System.out.println(call.getResult());
        ois.close();
        oos.close();
        socket.close();
        return (String)call.getResult();
    }

}
