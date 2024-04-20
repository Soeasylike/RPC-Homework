import java.io.*;
import java.util.ArrayList;

/**
 * Created by lyriccn on 18/3/27.
 */
public class RPCServiceImpl implements  RPCService {
    //充值
    @Override
    public String recharge(Card card) {
        //读取参数信息
        String id= card.getCardId();
        int amount=card.getAmount();
        //读文件得到一个列表
        FileInputStream fis = null;
        ObjectInputStream in;
        try {
            fis = new FileInputStream(".\\card.dat");
        } catch (FileNotFoundException e) {
            System.out.println("card文件还没创建"+e.getMessage());
        }
        ArrayList<Card> cardList = null;
        if (fis != null) {
            try {
                in = new ObjectInputStream(fis);
                cardList = (ArrayList<Card>) in.readObject();
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        //在列表中找到卡号
        //写文件
        FileOutputStream fos = null;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream(".\\card.dat");
            out = new ObjectOutputStream(fos);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int flag=0;
        for (Card card1 :cardList){
            if(card1.getCardId().equals(id)) {
                //找到一卡通
                flag = 1;
                int index = cardList.indexOf(card1);
                //充值
                cardList.get(index).setAmount(card.getAmount() + amount);
                //更新完列表，把一整个列表重写回文件
                try {
                    out.writeObject(cardList);
                } catch (IOException e) {
                    System.out.println("写回文件失败" + e.getMessage());
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    System.out.println("关闭文件失败" + e.getMessage());
                }
                return "充值成功";
            }
        }
        if(flag==0){
            //未找到卡号
            Card newCard=new Card(id,0);
            cardList.add(newCard);
            try {
                out.writeObject(cardList);
            } catch (IOException e) {
                System.out.println("未找到卡号时写入文件失败"+e.getMessage());
            }
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("未找到卡号时关闭文件失败"+e.getMessage());
            }
            return "未找到卡号，已为您创建卡号,若要充值请重新输入";
        }

        return null;
    }
    //消费
    @Override
    public String consumu(Card card) {
        //读取参数信息
        String id= card.getCardId();
        int amount=card.getAmount();
        //读文件得到一个列表
        FileInputStream fis = null;
        ObjectInputStream in;
        try {
            fis = new FileInputStream(".\\card.dat");
        } catch (FileNotFoundException e) {
            System.out.println("card文件还没创建"+e.getMessage());
        }
        ArrayList<Card> cardList = null;
        if (fis != null) {
            try {
                in = new ObjectInputStream(fis);
                cardList = (ArrayList<Card>) in.readObject();
                fis.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        //在列表中找到卡号
        //写文件
        FileOutputStream fos = null;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream(".\\card.dat");
            out = new ObjectOutputStream(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int flag=0;
        for(Card card1 :cardList){
            if(card1.getCardId().equals(id)){
                //找到该一卡通
                flag=1;
                int index=cardList.indexOf(card1);
                if(card1.amount<amount){
                    try {
                        out.writeObject(cardList);
                    } catch (IOException e) {
                        System.out.println("列表写回文件失败"+e.getMessage());
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        System.out.println("关闭文件失败"+e.getMessage());
                    }
                    return "余额不足";
                }
                else{
                    //余额充足可消费
                    cardList.get(index).setAmount(card.getAmount()-amount);
                    try {
                        out.writeObject(cardList);
                    } catch (IOException e) {
                        System.out.println("写回文件失败"+e.getMessage());
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        System.out.println("关闭文件失败"+e.getMessage());
                    }
                    return "消费成功";
                }
            }
        }
        return null;
    }
}
