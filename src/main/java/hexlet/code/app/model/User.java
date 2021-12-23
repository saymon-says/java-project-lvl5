package hexlet.code.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final int MIN = 3;

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotEmpty
    @Size(min = 1, message = "firstName longer than 1 character")
    private String firstName;

    @NotEmpty
    @Size(min = 1, message = "lastName longer than 1 character")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @JsonIgnore
    @Size(min = MIN, message = "password longer than 3 character")
    private String password;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date createdAt;

}
