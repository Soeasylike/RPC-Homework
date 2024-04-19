import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            //创建ServerSocket对象，绑定端口号
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务器启动成功，等待客户端连接...");

            //等待客户端连接，阻塞式方法
            Socket socket = serverSocket.accept();
            System.out.println("客户端" + socket.getInetAddress().getHostAddress() + "已连接！");
            //启动读取客户端消息的子线程
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
        //获取输出流，向客户端发送数据
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
            String message = reader.readLine();
            printWriter.println(message);
        }
    }
}

class ReceiveMessage implements Runnable {
    private Socket socket;

    public ReceiveMessage(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //获取输入流，接收数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String message = reader.readLine();
                System.out.println("收到消息：" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
