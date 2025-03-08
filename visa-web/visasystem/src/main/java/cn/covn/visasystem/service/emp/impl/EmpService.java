package cn.covn.visasystem.service.emp.impl;

import cn.covn.visasystem.mapper.EmpMapper;
import cn.covn.visasystem.pojo.vo.EmpInfoDTO;
import cn.covn.visasystem.pojo.vo.EmpListDTO;
import cn.covn.visasystem.pojo.vo.EmpSearchNameDTO;
import cn.covn.visasystem.service.emp.IEmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EmpService implements IEmpService {
    @Autowired
    EmpMapper empMapper;


    /**
     * 获取员工列表
     * @return
     */
    @Override
    public ArrayList<EmpListDTO> getEmpList() {
        ArrayList<EmpListDTO> empList = empMapper.selectEmpList();

        //计算剩余时间
        for (EmpListDTO emp : empList) {
            Date visaEnd = emp.getVisaEnd();

            if(visaEnd == null) {
                emp.setRemaining(0);
                continue;
            }else{
                Date now = new Date();
                long remainingMillis = visaEnd.getTime() - now.getTime();
                long remainingDays = TimeUnit.MILLISECONDS.toDays(remainingMillis);
                emp.setRemaining(Integer.parseInt(remainingDays + ""));
            }
        }
        return empList;
    }

    /**
     * 获取搜索名字列表
     * @return
     */
    @Override
    public ArrayList<EmpSearchNameDTO> getSearchNameList() {
        Date today=new Date();
        //select id,name value from emp where DATEDIFF(visa_end, #{date}) > 60 and visa_end is not null
        return empMapper.selectSearchNameList(today);
    }

    @Override
    public EmpListDTO SearchName(String name) {
        EmpListDTO emp = empMapper.selectEmpByName(name);

        if(emp == null) {
            return null;
        }


        Date visaEnd = emp.getVisaEnd();
        if(visaEnd == null) {
            emp.setRemaining(0);
        }else{
            Date now = new Date();
            long remainingMillis = visaEnd.getTime() - now.getTime();
            long remainingDays = TimeUnit.MILLISECONDS.toDays(remainingMillis);
            log.debug("EmpController::32:::remainingDays:{}", remainingDays);
            emp.setRemaining(Integer.parseInt(remainingDays + ""));
        }
        return emp;
    }

    @Override
    public EmpListDTO SearchTime(int day) {
        Date date = new Date();
        // 创建 SimpleDateFormat 对象并指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 将 Date 对象格式化为字符串
        String formattedDate = sdf.format(date);


        EmpListDTO emp = empMapper.selectEmpByDay(day,formattedDate);
        if(emp == null) {
            return null;
        }

        Date visaEnd = emp.getVisaEnd();
        if(visaEnd == null) {
            emp.setRemaining(0);
        }else{
            emp.setRemaining(GetDay(visaEnd));
        }
        return emp;
    }

    @Override
    public EmpInfoDTO getEmpInfo(int id) {
        EmpInfoDTO empInfo = empMapper.selectEmpInfoById(id);

        Date date = new Date();
        // 创建 SimpleDateFormat 对象并指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 将 Date 对象格式化为字符串
        String formattedDate = sdf.format(date);

        if(empInfo == null) {
            return null;
        }

        Date visaEnd = empInfo.getVisaEnd();
        if(visaEnd == null) {
            empInfo.setRemaining(0);
        }else{
            empInfo.setRemaining(GetDay(visaEnd));
        }
        return empInfo;
    }


    /**
     * 获取剩余天数
     * @param endDate 结束时间
     * @return
     */
    public int GetDay(Date endDate){
        Date now = new Date();
        long remainingMillis = endDate.getTime() - now.getTime();
        long remainingDays = TimeUnit.MILLISECONDS.toDays(remainingMillis);
        log.debug("EmpController::32:::remainingDays:{}", remainingDays);
        return Integer.parseInt(remainingDays + "");
    }
}
