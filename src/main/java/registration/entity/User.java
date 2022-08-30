package registration.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private int id;

    private String name;

    private String surname;

    private String country;

    private String email;

    private String password;

    private String role;

    public User(String name, String surname, String country, String email, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.email = email;
        this.password = password;
        this.role = role;
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
