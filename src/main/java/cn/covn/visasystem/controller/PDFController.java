package cn.covn.visasystem.controller;

import cn.covn.visasystem.comment.tools.forself.PathInfo;
import cn.covn.visasystem.comment.tools.forself.PdfTextZoneTool;
import cn.covn.visasystem.comment.tools.forself.TableTools;
import cn.covn.visasystem.pojo.vo.EmpInfoDTO;
import cn.covn.visasystem.pojo.vo.EmpListDTO;
import cn.covn.visasystem.service.emp.IEmpService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
@Validated
@Slf4j
public class PDFController {
    @Autowired
    private IEmpService empService;
    @GetMapping("emp/{id}/download")// /pdf/emp/1/download
    public void download(@PathVariable int id, HttpServletResponse response) {


        EmpInfoDTO empInfo = empService.getEmpInfo(id);
        if(empInfo == null) return;
        Map<String,String> values=new HashMap<>();
        values.put("name",empInfo.getName());
        values.put("address",empInfo.getAddress());
        values.put("phone",empInfo.getPhone());
        values.put("email",empInfo.getEmail());
        values.put("visa_type",empInfo.getVisaType());
        values.put("visa_end",empInfo.getVisaEnd().toString());
        values.put("ope_status",empInfo.getOpeStatus());
        values.put("remaining",empInfo.getRemaining()+"");
        PathInfo pathInfo=PathInfo.getPath("EmpInfoTemplate.pdf","output.pdf");

        if(pathInfo==null)return;
        File file = PdfTextZoneTool.setField(pathInfo, values);
        if(file!=null&&!file.exists()){
            log.error("文件不存在");
        }
        response.setContentType("application/pdf");
        sendFile(response, file);
    }

    private static void sendFile(HttpServletResponse response, File file) {
        OutputStream httpOutputStream=null;
        FileInputStream fileInputStream=null;
        byte[] buffer=new byte[1024*2];
        try{
            httpOutputStream= response.getOutputStream();
            fileInputStream=new FileInputStream(file);
            int i=0;
            while((i=fileInputStream.read(buffer))!=-1){

                httpOutputStream.write(buffer,0,i);
            }
            httpOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(httpOutputStream!=null){
                    httpOutputStream.close();
                }
            } catch (Exception e) {
            }finally {
                try {
                    if(fileInputStream!=null){
                        fileInputStream.close();;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @GetMapping("emp/list/download")
    public void listDownload(HttpServletResponse response) {
        try{
            TableTools tableTools=new TableTools(new float[]{2,2,2,2,2},PathInfo.getPath("","EmpListoutput.pdf"));
            tableTools.addTitle("aaaaa社員情報リスト");
            tableTools.addCenterCell("ID");
            tableTools.addCenterCell("名前");
            tableTools.addCenterCell("残り時間");
            tableTools.addCenterCell("期限日");
            tableTools.addCenterCell("進捗");
            ArrayList<EmpListDTO> empList = empService.getEmpList();
            empList.forEach(emp->{
                tableTools.addCell(emp.getId()+"");
                tableTools.addCell(emp.getName());
                tableTools.addCell(emp.getRemaining()+"");
                if(emp.getVisaEnd()!=null){
                    tableTools.addCell(emp.getVisaEnd().toString());
                }else{
                    tableTools.addCell("null");
                }
                tableTools.addCell(emp.getOpeStatus());

            });
            tableTools.GeneralTable();
            sendFile(response,tableTools.getHtFile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
