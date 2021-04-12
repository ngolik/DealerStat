package by.golik.dealerstat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * @author Nikita Golik
 */
@Entity
@Table(name = "users")
@Getter
@Setter

public class User extends AbstractEntity {

    public User() {
    }

    @Size(min = 2, max = 32)
    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Size(min = 2, max = 32)
    @Column(name = "last_name")
    @NotNull
    private String lastName;


    @Size(min = 8, max = 50)
    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "role")
    @Enumerated
    private Role role;

}
