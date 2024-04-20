
import Other.Card;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReadData {
    public static void main(String[]args) throws IOException, ClassNotFoundException {
        FileInputStream fis=null;
        ObjectInputStream in;
        ArrayList<Card> cardList=null;
        try {
            fis=new FileInputStream(".\\card.dat");
        } catch (FileNotFoundException e) {
            System.out.println("未创建");
        }
        if(fis!=null){
            in=new ObjectInputStream(fis);
            cardList=(ArrayList<Card>) in.readObject();
            fis.close();
        }
        for(Card card:cardList){
            System.out.println(card.getCardId()+" "+card.getMoney());
        }
    }
}
