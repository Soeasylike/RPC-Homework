import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        Gson gson=new Gson();
        List<Card> cardList = null;
        String fileName="src/main/java/json_data.json";
        //读文件得到一个列表
        try{
            BufferedReader reader=new BufferedReader(new FileReader(fileName));
            TypeToken<List<Card>> typeToken=new TypeToken<List<Card>>(){};
            cardList=gson.fromJson(reader,typeToken.getType());
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件"+e.getMessage());
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName);
        } catch (IOException e) {
            System.out.println("创建writer失败"+e.getMessage());
        }

        int flag=0;
        for (Card card1 :cardList){
            if(card1.getCardId().equals(id)) {
                //找到一卡通
                flag = 1;
                int index = cardList.indexOf(card1);
                //充值
                cardList.get(index).setAmount(card1.getAmount() + amount);
                //更新完列表，把一整个列表重写回文件
                gson.toJson(cardList, writer);
                try {
                    writer.flush();
                } catch (IOException e) {
                    System.out.println("写入文件失败");
                }

                return "充值成功";
            }
        }
        if(flag==0){
            //未找到卡号
            Card newCard=new Card(id,0);
            cardList.add(newCard);
            gson.toJson(cardList,writer);
            try {
                writer.flush();
            } catch (IOException e) {
                System.out.println("写入文件失败"+e.getMessage());
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
        Gson gson=new Gson();
        List<Card> cardList = null;
        String fileName="src/main/java/json_data.json";
        try{
            BufferedReader reader=new BufferedReader(new FileReader(fileName));
            TypeToken<List<Card>> typeToken=new TypeToken<List<Card>>(){};
            cardList=gson.fromJson(reader,typeToken.getType());
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件"+e.getMessage());
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName);
        } catch (IOException e) {
            System.out.println("创建writer失败"+e.getMessage());
        }
        //读文件得到一个列表

        int flag=0;
        for(Card card1 :cardList){
            if(card1.getCardId().equals(id)){
                //找到该一卡通
                flag=1;
                int index=cardList.indexOf(card1);
                if(card1.amount<amount){
                    gson.toJson(cardList,writer);
                    try {
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("写入文件失败");
                    }
                    return "余额不足,无法消费";
                }
                else{
                    //余额充足可消费
                    cardList.get(index).setAmount(card1.getAmount()-amount);
                    gson.toJson(cardList,writer);
                    try {
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("写入文件失败");
                    }
                    return "消费成功";
                }
            }
        }
        if(flag==0){
            return "不存在该卡号，无法消费";
        }
        return null;
    }
}
