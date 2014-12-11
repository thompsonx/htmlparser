/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import htmlparser.parser.Parser;
import htmlparser.parser.Team;
import java.awt.Color;
import java.io.FileOutputStream;

/**
 *
 * @author Tom
 */
public class PDFFile {
    
    private Parser parser;
    private Document document;
    private String team;
    private boolean opened;
    private final String[] shColNms = {"Pořadí", "Tým", "Zápasů", "+", "0", "-", "Skóre", "Body"};
    private BaseFont fonttype;
    
    public PDFFile(Parser parser, String team) {
        
        this.parser = parser;
        this.document = new Document();
        
        this.team = team;
       
        try {
            
            PdfWriter.getInstance(this.document, new FileOutputStream(this.parser.getCompetitionName() + ".pdf"));
            this.document.open();
            this.fonttype = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            this.opened = true;
            
        } catch (Exception ex) {
            this.opened = false;
        }
    }
    
    public void createScoreTable(Color oddrow_color, Color title_bg_color, Color title_font_color, int highlight) {
        if (this.opened) {
            
            int columnNumber = 0;
            for (String s: this.parser.getTeams().get(0).getData()) {

                columnNumber++;

            }
            
            PdfPTable table = new PdfPTable(columnNumber + 1);
            
            Font f = new Font(this.fonttype, 13);
            
            
            int row = 1;
            
            Font headline = new Font(f);
            headline.setStyle("bold");
            headline.setSize(16);
            headline.setColor(new BaseColor(title_bg_color.getRGB()));
            
            PdfPCell headcell = new PdfPCell(new Paragraph(this.parser.getCompetitionName(), headline));
            headcell.setColspan(columnNumber + 1);
            headcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headcell);
            
            
            Font title = new Font(f);
            title.setStyle("bold");
            title.setColor(new BaseColor(title_font_color.getRGB()));
            
            for (String s: this.shColNms) {
                
                PdfPCell cell = new PdfPCell(new Paragraph(s, title));
                cell.setBackgroundColor(new BaseColor(title_bg_color.getRGB()));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                table.addCell(cell);
            }
            
            row++;
            
            int order = 1;
            
            Font tfont = f;
            Font bold = new Font(f);
            bold.setStyle("bold");
            
            for (Team t: this.parser.getTeams()) {
                
                if (highlight >= 0) {
                    if (order == highlight + 1) {
                        tfont = bold;
                    }
                    else {
                        tfont = f;
                    }
                }
                
                PdfPCell ordercell = new PdfPCell(new Paragraph(Integer.toString(order), tfont));
                ordercell.setHorizontalAlignment(Element.ALIGN_CENTER);
                
                if (row % 2 == 0) {
                        ordercell.setBackgroundColor(new BaseColor(oddrow_color.getRGB()));
                }
                
                table.addCell(ordercell);
                
                for (String s: t.getData()) {
                    
                    PdfPCell cell = new PdfPCell(new Paragraph(s, tfont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    
                    
                    if (row % 2 == 0) {
                        cell.setBackgroundColor(new BaseColor(oddrow_color.getRGB()));
                    }
                    
                    table.addCell(cell);
                    
                }
                
                order++;
                row++;
                
                               
            }
            
            
            try {
                float[] widths = {12f, 40f, 13f, 5f, 5f, 5f, 10f, 10f};
                table.setWidths(widths);
                
                this.document.add(table);
                this.document.newPage();
                
            } catch (DocumentException ex) {
                System.out.println("Content exception");
            }
            
        }
    }
    
    public void closeFile() {
        
        if (this.opened) {
            
            this.document.close();
            
        }
    }
    
}
