package ee.raunokivi.PangaRakendus;

public class Account {
    private String number;
    private int id;
    private int balance;
    private boolean is_locked;

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setBalance(int balance) {
        this.balance = balance;
    }


    public String getNumber() {
        return number;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isIs_locked() {
        return is_locked;
    }

    public void setIs_locked(boolean is_locked) {
        this.is_locked = is_locked;
    }

}
