package cn.covn.visasystem.pojo.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Emp {
    int id;
    String phone;
    String address;
    String email;
    String name;
    int visa_type;
    String visa_img;
    Date visa_end;
    int opeStatus;
    Date updateTime;
    Date createTime;
    int delete_flag;
}
