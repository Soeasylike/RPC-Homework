import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadJsonData {
    public static void main(String[]args){
        Gson gson=new Gson();
        List<Card> cards;
        try{
            BufferedReader reader=new BufferedReader(new FileReader("src/main/java/json_data.json"));
            TypeToken<List<Card>> typeToken=new TypeToken<List<Card>>(){};
            cards=gson.fromJson(reader,typeToken.getType());
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(cards!=null){
            for(Card card:cards){
                System.out.println(card.cardId+" "+card.amount);
            }
        }
    }
}
