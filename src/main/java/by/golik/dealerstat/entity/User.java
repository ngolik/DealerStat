package by.golik.dealerstat.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

/**
 * @author Nikita Golik
 */
@Entity
@Table (name="user")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class User extends AbstractEntity {

    @Size(min=3, max=50)
    @Column(unique = true)
    @NotNull
    private String name;

    @Size(min=8, max=20)
    @NotNull
    private String password;

    private String email;

    @Column (name = "created_at")
    private Date createdAt;

    @Column (name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany (mappedBy = "owner")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference(value = "user-gameObject")
    List<GameObject> gameObjects;

    @OneToMany (mappedBy = "author")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference(value = "user-comment")
    private List<Comment> comments;

}
