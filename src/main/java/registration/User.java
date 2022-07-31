package registration;

public class User {

    private int id;

    private String name;

    private String surname;

    private String country;

    private String email;

    private String password;

    public User() {}

    public User(String name, String surname, String country, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String surname, String country, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", name: '" + name + '\'' +
                ", email: '" + email + '\'' +
                ", country: '" + country + '\''
                +"\n";
    }
}
