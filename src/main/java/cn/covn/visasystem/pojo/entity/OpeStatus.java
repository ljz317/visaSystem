package cn.covn.visasystem.pojo.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OpeStatus {
    int id;
    String name;
    String description;
    int weight;
    int level;
}
