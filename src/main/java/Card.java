import java.io.Serializable;

public class Card implements Serializable {
    String cardId;
    int money;

    public Card(String cardId, int money) {
        this.cardId = cardId;
        this.money = money;
    }
    public Card(){}

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
