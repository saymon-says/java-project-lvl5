package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotEmpty(message = "Task_dto name field must not be empty")
    @Size(min = 1, message = "name longer than 1 character")
    private String name;

    private String description;
    private long executorId;

    private List<Long> labelIds;

    @NotNull(message = "Task_dto status_id field must not be empty")
    private long taskStatusId;

}
