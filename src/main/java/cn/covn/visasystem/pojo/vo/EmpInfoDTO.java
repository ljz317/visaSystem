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
public class EmpInfoDTO {
    String phone;
    String address;
    String email;
    String name;
    String visaType;
    String visaImg;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date visaEnd;
    String opeStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date createTime;
    String username;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date lastLogin;
    int empId;
    int remaining;
}
