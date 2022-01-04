package hexlet.code.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotEmpty
    @Size(min = 1, message = "name longer than 1 character")
    private String name;

    private String description;
    private long executorId;

    @NotNull
    private long taskStatusId;

}
