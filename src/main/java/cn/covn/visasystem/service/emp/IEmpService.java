package cn.covn.visasystem.service.emp;

import cn.covn.visasystem.pojo.vo.EmpInfoDTO;
import cn.covn.visasystem.pojo.vo.EmpListDTO;
import cn.covn.visasystem.pojo.vo.EmpSearchNameDTO;

import java.util.ArrayList;

public interface IEmpService {
    ArrayList<EmpListDTO> getEmpList();

    ArrayList<EmpSearchNameDTO> getSearchNameList();

    EmpListDTO SearchName(String name);

    EmpListDTO SearchTime(int day);

    EmpInfoDTO getEmpInfo(int id);
}
