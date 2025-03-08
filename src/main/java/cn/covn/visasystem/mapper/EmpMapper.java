package cn.covn.visasystem.mapper;

import cn.covn.visasystem.pojo.vo.EmpInfoDTO;
import cn.covn.visasystem.pojo.vo.EmpListDTO;
import cn.covn.visasystem.pojo.vo.EmpSearchNameDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface EmpMapper {

    //select e.id,e.name,e.visa_end visaEnd,e.name from emp e inner join ope_status o on o.id=e.ope_status;
    ArrayList<EmpListDTO> selectEmpList();

    //select id,name value from emp where DATEDIFF(visa_end, #{date}) > 60 and visa_end is not null
    ArrayList<EmpSearchNameDTO> selectSearchNameList(Date date);

    //select e.id,e.name,e.visa_end visaEnd,e.name from emp e inner join ope_status o on o.id=e.ope_status where e.name=#{name}
    EmpListDTO selectEmpByName(String name);

    //select e.id,e.name,e.visa_end visaEnd,o.name opeStatus from emp e inner join ope_status o on o.id=e.ope_status where DATEDIFF(e.visa_end, #{date}) between
    EmpListDTO selectEmpByDay(@Param("day") int day,@Param("date") String date);

    // select e.phone,e.address,e.email,e.name,t.name,e.visa_img visaImg,e.visa_end visaEnd,o.name,e.create_time createTime,u.username,u.update_time updateTime,u.last_login lastLogin,e.id empId
    // from emp e inner join user u on u.emp_id=e.id inner join ope_status o on o.id=e.ope_status inner join visa_type t on t.id=e.visa_type
    EmpInfoDTO selectEmpInfoById(int id);
}
