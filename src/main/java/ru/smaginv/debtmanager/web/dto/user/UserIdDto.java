package ru.smaginv.debtmanager.web.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
public class UserIdDto implements HasIdDto {

    @NotBlank
    @Accessors(prefix = "user")
    private String userId;

    @Override
    public boolean isNew() {
        return Objects.isNull(userId);
    }
}
