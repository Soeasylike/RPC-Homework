
import Other.Card;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class AddData {
    public static void main(String[]args) throws IOException {
        ArrayList<Card> cardList=new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        String id;
        int amount;
        int flag;
        FileOutputStream fos=new FileOutputStream(".\\card_1.dat");
        ObjectOutputStream out=new ObjectOutputStream(fos);
        while(true){
            System.out.println("请输入卡号");
            id=sc.next();
            System.out.println("请输入金额");
            amount=sc.nextInt();
            Card card=new Card(id,amount);
            cardList.add(card);
            System.out.println("是否需要继续输入：是-1；否-0");
            flag=sc.nextInt();
            if(flag==0){
                break;
            }
        }
        out.writeObject(cardList);
    }
}
