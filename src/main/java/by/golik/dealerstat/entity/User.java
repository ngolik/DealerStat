package by.golik.dealerstat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Nikita Golik
 */
@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class User extends AbstractEntity implements UserDetails {

    private String firstName;

    private String lastName;

    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @NaturalId
    private String email;

    @Column(nullable = false)
    private boolean enabled;

    @Transient
    private Double rate;

    @JsonIgnore
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(nullable = false)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<GameObject> gameObjects;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Comment> comments;

    @Setter
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Token token;

    @Setter
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private ReplyCode replyCode;


    public User(String firstName, String lastName, String password,
                String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Role> list = new ArrayList<Role>();
        list.add(role);
        return list;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
