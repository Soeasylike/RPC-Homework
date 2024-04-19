import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            //创建Socket对象，指定连接的服务器端地址和端口号
            Socket socket = new Socket("localhost", 8888);
            System.out.println("已连接服务器！");

            //启动读取服务器端消息的子线程
            Thread thread = new Thread(new ReceiveMessage(socket));
            thread.start();

            //启动发送消息的主线程
            sendMessage(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //发送消息的方法
    public static void sendMessage(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //获取输出流，向服务器端发送数据
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
            String message = reader.readLine();
            printWriter.println(message);
        }
    }
}// 客户端程序结束
