package Other;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class AddData{
    public static void main(String args[])throws Exception{
        FileOutputStream fos=new FileOutputStream(".\\card.txt");
        ObjectOutputStream out=new ObjectOutputStream(fos);
        int n=1;
        String CardId="";
        int money=0;
        ArrayList<Card> cardList=new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        while(n==1){
            System.out.println("请输入卡号：");
            CardId=sc.next();
            System.out.println("请输入金额：");
            money=sc.nextInt();
            System.out.println("请输入是否结束循环：");
            n=sc.nextInt();
            Card cardId=new Card(CardId,money);
            cardList.add(cardId);
            System.out.println("--------------");
        }
        out.writeObject(cardList);
        sc.close();
        out.close();
    }


}
