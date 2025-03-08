package cn.covn.visasystem.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginVO {
    @NotBlank(message = "error")
    String username;
    @NotBlank(message = "error")
    String password;
}
