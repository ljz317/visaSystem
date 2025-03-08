package cn.covn.visasystem.comment.tools.forself;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

public class PdfTextZoneTool {
    public static File setField(PathInfo info, Map<String,String> values){
        File file=null;
        try{

            PdfReader reader = new PdfReader(info.getPdfPath());
            FileOutputStream out = new FileOutputStream(info.getOutPutPath());

            PdfStamper stamper = new PdfStamper(reader, out);
            AcroFields form = stamper.getAcroFields();

            values.keySet().forEach(key -> {
                String value = values.get(key);
                try {
                    form.setField(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            stamper.setFormFlattening(false);
            file=new File(info.getOutPutPath());
            file.createNewFile();

            stamper.close();
            reader.close();
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }
}
