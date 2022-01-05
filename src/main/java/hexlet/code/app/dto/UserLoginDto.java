package hexlet.code.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotEmpty(message = "User_login_dto email field must not be empty")
    private String email;

    @NotEmpty(message = "User_login_dto password field must not be empty")
    private String password;

}
