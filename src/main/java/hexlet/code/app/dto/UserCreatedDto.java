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

    @NotEmpty(message = "User_created_dto first_name field must not be empty")
    @Size(min = 1, message = "firstName longer than 1 character")
    private String firstName;

    @NotEmpty(message = "User_created_dto last_name field must not be empty")
    @Size(min = 1, message = "lastName longer than 1 character")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "User_created_dto password field must not be empty")
    @Size(min = MIN, message = "password longer than 3 character")
    private String password;
}
