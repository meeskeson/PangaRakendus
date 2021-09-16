package ee.raunokivi.PangaRakendus;

public class History {
    private String number;
    private int amount;
    private String number_to;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public History(String number, int amount, String number_to) {
        this.number = number;
        this.amount = amount;
        this.number_to = number_to;
    }

    public History() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNumber_to() {
        return number_to;
    }

    public void setNumber_to(String number_to) {
        this.number_to = number_to;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
