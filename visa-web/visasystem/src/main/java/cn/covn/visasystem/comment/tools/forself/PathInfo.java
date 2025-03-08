package cn.covn.visasystem.comment.tools.forself;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PathInfo {
    String templatePath;
    String pdfPath;
    String outPutPath;

    public PathInfo(String templatePath, String pdfPath, String outPutPath) {
        this.templatePath = templatePath;
        this.pdfPath = pdfPath;
        this.outPutPath = outPutPath;
    }
    public static PathInfo getPath(String fileName,String outPutFileName){
        try{
            String ht;
            File htFile;
            String pdfPath="";
            //定位pdf绝对路径，并定位ht.pdf文件
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("win") || osName.contains("mac")) {
                File space=new File(ResourceUtils.getURL("classpath:").getPath());
                pdfPath=space.getParentFile().getAbsolutePath()+"\\classes";
                space=new File(pdfPath);
                ht = space.getAbsolutePath() + "\\templates\\"+fileName;
                htFile = new File(ht);
            } else { //todo: linux或unbunt
                ht = "/mnt/jar/spring-cloud/"+fileName;
                htFile = new File(ht);
            }
            System.out.println("ht = " + ht);
            if(htFile.exists()){
                System.out.println("文件存在:"+htFile.getName());
            }
            return new PathInfo(pdfPath,ht,pdfPath+"/templates/"+outPutFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
