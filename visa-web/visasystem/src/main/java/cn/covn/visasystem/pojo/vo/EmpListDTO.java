package cn.covn.visasystem.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmpListDTO {
    int id;
    String name;
    int remaining;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date visaEnd;
    String opeStatus;
}
