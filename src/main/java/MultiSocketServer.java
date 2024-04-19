import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MultiSocketServer {
    static int num = 0;// 客户端计数

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = null;
        Socket client = null;
        FileInputStream fis=null;
        ObjectInputStream in;
        ArrayList<Card> cardList=new ArrayList<>();
        Executor cachedThread1 = Executors.newFixedThreadPool(1);
        while (true) {
            try {
                serverSocket = new ServerSocket(8888);// 绑定端口4444监听客户请求
            } catch (Exception e) {
                System.out.println("Error:" + e);// 屏幕打印出错信息
                System.exit(-1);
            }
            try {
                if(num<1)
                {
                    client = serverSocket.accept(); // 使用accept()阻塞等待客户请求，请求到来时
                    ServerThread st = new ServerThread(client);
                    cachedThread1.execute(st);
                    num++;
                }else {
                    //

                }
                // 产生一个Socket对象
            } catch (Exception e) {
                System.out.println("接受请求失败!");
                System.exit(-1);
            }



            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("关闭失败!");
            }

        }
    }
}

class ServerThread implements Runnable {
    private Socket client;

    public ServerThread(Socket client) {
        this.client = client;// 初始化client变量
    }

    public void run() {// 线程主体
        try {
            String str="123";
//            // 实现数据传输
            BufferedReader is = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
//            // 由Socket对象得到输入流，并构造相应的BufferedReader对象
            PrintWriter os = new PrintWriter(client.getOutputStream());
            InputStream iss=client.getInputStream();
            ObjectInputStream oiss=new ObjectInputStream(iss);
//            // 由Socket对象得到输出流，并构造PrintWriter对象
//            BufferedReader sin = new BufferedReader(new InputStreamReader(
//                    System.in));
            // 由系统标准输入设备构造BufferedReader对象
            //System.out.println("Client:" + is.readLine()); // 在标准输出上打印从客户端读入的字符串
            //String inputString = sin.readLine();// 从标准输入读入一字符串
            while (!str.equals("1")) {// 如果输入的字符串为"quit"
                ObjectInputStream in;
                FileInputStream fis=null;
                CardUse cu= (CardUse) oiss.readObject();
                String cardID=cu.cardId;
                int type=cu.type;
                int money=cu.money;
                ArrayList<Card> cardList=new ArrayList<>();
                try {
                    fis = new FileInputStream(".\\card.txt");//打开文件
                }catch (FileNotFoundException e){
                    System.out.println("card文件还没创建，没有card数据表");
                }
                if(fis!=null){
                    in=new ObjectInputStream(fis);
                    cardList=(ArrayList<Card>) in.readObject();//读文件，返回列表
                    fis.close();
                }
                for(Card card:cardList)
                {
                    if(card.cardId.equals(cu.cardId))
                    {
                        if(type==1)
                        {
                            card.money+=cu.money;
                            str="充值成功,您的余额为"+card.money;
                            break;
                        }else {
                            if(card.money-cu.money<0)
                            {
                                str="余额不足，消费失败,您的余额仅为"+card.money;
                                break;
                            }
                            if(card.money-cu.money>=0)
                            {
                                card.money-=cu.money;
                                str="消费成功，余额为"+card.money;
                                break;
                            }
                        }
                    }
                }
                if(str.equals(""))
                {
                    str="卡号不存在，为您创建卡号"+cu.cardId;
                    Card ca=new Card(cu.cardId,0);
                    cardList.add(ca);
                }


                FileOutputStream fos=new FileOutputStream(".\\card.txt");
                ObjectOutputStream out=new ObjectOutputStream(fos);
                out.writeObject(cardList);
                out.close();


                // 则退出循环
                //System.out.println("进入循环开始--------------");
                os.println(str);// 向客户端输出该字符串
                os.flush();// 刷新输出流，使得client马上收到该字符串
                System.out.println("Server发送的消息为:" + str);
                //System.out.println("Client发送的消息为:" + is.readLine()); // 在标准输出上打印从客户端读入的字符串
                //inputString = sin.readLine();// 从系统标准输入读入一字符串
                //System.out.println("进入循环末尾");
                //str=result();
                //System.out.println("进入循环后的str是："+str);

            }// 继续循环
            os.close();// 关闭Socket输出流
            is.close();// 关闭Socket输入流
            client.close();// 关闭Socket
            System.out.println("聊天结束!");



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

