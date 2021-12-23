package hexlet.code.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedDto {
    private static final int MIN = 3;

    @NotEmpty
    @Size(min = 1, message = "firstName longer than 1 character")
    private String firstName;

    @NotEmpty
    @Size(min = 1, message = "lastName longer than 1 character")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty
    @Size(min = MIN, message = "password longer than 3 character")
    private String password;
}
