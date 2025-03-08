package cn.covn.visasystem.comment.tools.forself;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Data
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TableTools {
    PdfPTable table;
    Document document=null;
    PathInfo info;
    File htFile;
    ArrayList<Paragraph> titleTexts;
    public TableTools(float[] i,PathInfo info) {
        table = new PdfPTable(i);
        document=new Document(PageSize.A4.rotate());
        document.open();
        titleTexts=new ArrayList<>();
        this.info=info;
    }

    public void addTitle(String titleText) {
        try{
            // 添加大标题
            BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\msgothic.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 24, Font.BOLD);
            Paragraph title = new Paragraph(titleText, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            titleTexts.add(title);
            titleTexts.add(new Paragraph(" "));
            titleTexts.add(new Paragraph(" "));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCenterCell(String text) throws DocumentException, IOException {
        System.out.println("table = " + table);
        BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\msgothic.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12);
        PdfPCell idCell = new PdfPCell(new Phrase(text,font));
        idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        idCell.setFixedHeight(20);
        table.addCell(idCell);
    }
    public void addCell(String text){
        try{
            System.out.println("table = " + table);
            BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\msgothic.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 11);
            PdfPCell idCell = new PdfPCell(new Phrase(text,font));
            idCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            idCell.setVerticalAlignment(Element.ALIGN_LEFT);
            idCell.setFixedHeight(16);
            table.addCell(idCell);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GeneralTable(){

        PdfWriter writer=null;
        try {
            htFile=new File(info.getOutPutPath());
            if(htFile.exists()){
                htFile.delete();
            }
            htFile.createNewFile();
            writer=PdfWriter.getInstance(document,new FileOutputStream(htFile));
            document.open();
            while(titleTexts.size()>0){
                document.add(titleTexts.remove(0));
            }
            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(document!=null){
                document.close();
            }
            if(writer!=null){
                writer.close();
            }
        }
    }



}
