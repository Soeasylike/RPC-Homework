import java.io.Serializable;

public class Card implements Serializable {
    String cardId;
    int amount;

    public Card(String cardId, int amount) {
        this.cardId = cardId;
        this.amount = amount;
    }
    public Card(){}

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
