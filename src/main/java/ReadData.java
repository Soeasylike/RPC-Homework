
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
            fis=new FileInputStream(".\\card_1.dat");
        } catch (FileNotFoundException e) {
            System.out.println("未创建");
        }
        if(fis!=null){
            in=new ObjectInputStream(fis);
            try{
                Object objectList=in.readObject();
                if(objectList!=null){
                    cardList= (ArrayList<Card>) objectList;
                }
                else{
                    System.out.println("objectList为空");
                }
            }
            //cardList=(ArrayList<Card>) in.readObject();}
            catch (IOException e){
                System.out.println("读文件获得列表"+e.getMessage());
            }
            fis.close();
        }else{
            System.out.println("文件为空");
        }
        for(Card card:cardList){
            System.out.println(card.getCardId()+" "+card.getMoney());
        }
    }
}
