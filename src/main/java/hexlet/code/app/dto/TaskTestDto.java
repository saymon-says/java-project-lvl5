package hexlet.code.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTestDto {

    @NotEmpty(message = "Task name field must not be empty")
    @Size(min = 1, message = "name longer than 1 character")
    private String name;
    private String description;
    private Long executorId;
    private List<Long> labelIds;
    private Long taskStatusId;
    private Long authorId;


}
