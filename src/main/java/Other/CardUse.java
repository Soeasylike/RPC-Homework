package Other;

import java.io.Serializable;

public class CardUse implements Serializable {
    String cardId;
    int type;
    int money;

    public CardUse(String cardId, int type, int money) {
        this.cardId = cardId;
        this.type = type;
        this.money = money;
    }

    public CardUse(){
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
