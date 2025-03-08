package cn.covn.visasystem.pojo.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    int id;
    String username;
    String password;
    Date last_login;
    Date update_time;
    Date create_time;
    int is_Admin;
    int delete_flag;
    int emp_id;
}
