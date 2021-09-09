package ee.raunokivi.PangaRakendus;

public class Client {
    private int id;
    private String firstname;
    private String lastname;
    private String address;

    public String getLastname() {
        return lastname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }
}
