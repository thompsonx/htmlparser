/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.xls;

import htmlparser.parser.Match;
import htmlparser.parser.Parser;
import htmlparser.parser.Team;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Tom
 */
public class XLSFile {
    
    private Workbook excelfile;
    private Sheet scoresheet;
    private Sheet matchsheet;
    private Parser parser;
    private String path;
    private final String[] shColNms = {"Pořadí", "Tým", "Zápasů", "+", "0", "-", "Skóre", "Body"};
    private final String[] mhColNms = {"Domácí", "Hosté", "Termín", "Den", "Hřiště"};
    
    public XLSFile(Parser parser, String destination) {
        
        this.parser = parser;
        this.excelfile = new XSSFWorkbook();
        this.path = destination;
        
        this.scoresheet = null;
        this.matchsheet = null;
       
    }
    
    public void createScoreTable(XSSFColor oddrow_color, XSSFColor title_bg_color, XSSFColor title_font_color, int highlight) {
        
        byte[] white = new byte[3];
        white[0] = 0;
        white[1] = 0;
        white[2] = 0;
        
        byte[] black = new byte[3];
        black[0] = black[1] = black[2] = -1;
        
        if (Arrays.equals(oddrow_color.getRgb(), white)) {
            oddrow_color.setRgb(black);
        }
        else if (Arrays.equals(oddrow_color.getRgb(), black)) {
            oddrow_color.setRgb(white);
        }
        if (Arrays.equals(title_bg_color.getRgb(), white)) {
            title_bg_color.setRgb(black);
        }
        else if (Arrays.equals(title_bg_color.getRgb(), black)) {
            title_bg_color.setRgb(white);
        }
        if (Arrays.equals(title_font_color.getRgb(), white)) {
            title_font_color.setRgb(black);
        }
        else if (Arrays.equals(title_font_color.getRgb(), black)) {
            title_font_color.setRgb(white);
        }
        
        System.out.println(Arrays.toString(title_font_color.getRgb()));
        
        String sheetname = WorkbookUtil.createSafeSheetName(this.parser.getCompetitionName());
        this.scoresheet = this.excelfile.createSheet(sheetname);
        
        CreationHelper createHelper = this.excelfile.getCreationHelper();
        
        CellStyle cellStyle = this.excelfile.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        
        int rows = 0;
        
        Row headline = this.scoresheet.createRow(rows);
        Cell cheadline = headline.createCell(0);
        cheadline.setCellValue(createHelper.createRichTextString(this.parser.getCompetitionName()));
        XSSFCellStyle customstyle = (XSSFCellStyle) this.excelfile.createCellStyle();
        customstyle.cloneStyleFrom(cellStyle);
        XSSFFont fh = (XSSFFont) this.excelfile.createFont();
        fh.setFontHeightInPoints((short) 16);
        fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fh.setColor(title_bg_color);
        customstyle.setFont(fh);
        cheadline.setCellStyle(customstyle);
        int length = this.parser.getTeams().get(0).getData().size();
        CellRangeAddress headrow = new CellRangeAddress(rows, rows, 0, length);
        this.scoresheet.addMergedRegion(headrow);
        RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, headrow, this.scoresheet, this.excelfile);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), headrow, this.scoresheet, this.excelfile);
        RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, headrow, this.scoresheet, this.excelfile);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), headrow, this.scoresheet, this.excelfile);
        RegionUtil.setBorderRight(CellStyle.BORDER_THIN, headrow, this.scoresheet, this.excelfile);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), headrow, this.scoresheet, this.excelfile);
        RegionUtil.setBorderTop(CellStyle.BORDER_THIN, headrow, this.scoresheet, this.excelfile);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), headrow, this.scoresheet, this.excelfile);
        rows++;
        
        Row colNms = this.scoresheet.createRow(rows++);
        customstyle = (XSSFCellStyle) this.excelfile.createCellStyle();
        customstyle.cloneStyleFrom(cellStyle);
        customstyle.setFillForegroundColor(title_bg_color);
        customstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        XSSFFont f1 = (XSSFFont) this.excelfile.createFont();
        f1.setColor(title_font_color);
        f1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        customstyle.setFont(f1);
        int cCN = 0;
        for (String s: this.shColNms) {
            
            Cell c = colNms.createCell(cCN);
            c.setCellValue(createHelper.createRichTextString(s));
            c.setCellStyle(customstyle);
            cCN++;
            
        }
        
        double ordNum = 1;
        
        customstyle = (XSSFCellStyle) this.excelfile.createCellStyle();
        customstyle.cloneStyleFrom(cellStyle);
        customstyle.setFillForegroundColor(oddrow_color);
        customstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        
        for (Team t: this.parser.getTeams()) {
            
            Row r = this.scoresheet.createRow(rows++);
            
            int cell = 0;
            
            Cell order = r.createCell(cell++);
            order.setCellValue(ordNum++);
            if (rows % 2 == 0)
                order.setCellStyle(customstyle);
            else
                order.setCellStyle(cellStyle);
            
            for (String s: t.getData()) {
                
                Cell c = r.createCell(cell);
                
                c.setCellValue( createHelper.createRichTextString(s) );
                
                if (rows % 2 == 0)
                    c.setCellStyle(customstyle);
                else
                    c.setCellStyle(cellStyle);
                
                cell++;
               
            }
            
        }
        
        for (int i = 0; i <= length; i++) {
            
            this.scoresheet.autoSizeColumn(i);
            
        }
        
        if (highlight >= 0) {
            
            highlight += 2;
            
            Row r = this.scoresheet.getRow(highlight);
            customstyle = (XSSFCellStyle) this.excelfile.createCellStyle();
            customstyle.cloneStyleFrom(r.getCell(0).getCellStyle());
            Font bold = this.excelfile.createFont();
            bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
            customstyle.setFont(bold);
            
            for (Cell c: r) {
                c.setCellStyle(customstyle);
            }
        }
        
    }
    
    public void createMatchTable(XSSFColor oddrow_color, XSSFColor title_bg_color, XSSFColor title_font_color) {
        
        byte[] white = new byte[3];
        white[0] = 0;
        white[1] = 0;
        white[2] = 0;
        
        byte[] black = new byte[3];
        black[0] = black[1] = black[2] = -1;
        
        if (Arrays.equals(oddrow_color.getRgb(), white)) {
            oddrow_color.setRgb(black);
        }
        else if (Arrays.equals(oddrow_color.getRgb(), black)) {
            oddrow_color.setRgb(white);
        }
        if (Arrays.equals(title_bg_color.getRgb(), white)) {
            title_bg_color.setRgb(black);
        }
        else if (Arrays.equals(title_bg_color.getRgb(), black)) {
            title_bg_color.setRgb(white);
        }
        if (Arrays.equals(title_font_color.getRgb(), white)) {
            title_font_color.setRgb(black);
        }
        else if (Arrays.equals(title_font_color.getRgb(), black)) {
            title_font_color.setRgb(white);
        }
        
        
        String sheetname = WorkbookUtil.createSafeSheetName(this.parser.getTeamName());
        this.matchsheet = this.excelfile.createSheet(sheetname);
        
        CreationHelper createHelper = this.excelfile.getCreationHelper();
        
        CellStyle cellStyle = this.excelfile.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        
        int rows = 0;
        
        Row headline = this.matchsheet.createRow(rows);
        Cell cheadline = headline.createCell(0);
        cheadline.setCellValue(createHelper.createRichTextString(this.parser.getTeamName()));
        XSSFCellStyle customstyle = (XSSFCellStyle) this.excelfile.createCellStyle();
        customstyle.cloneStyleFrom(cellStyle);
        XSSFFont fh = (XSSFFont) this.excelfile.createFont();
        fh.setFontHeightInPoints((short) 16);
        fh.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fh.setColor(title_bg_color);
        customstyle.setFont(fh);
        cheadline.setCellStyle(customstyle);
        int length = this.parser.getMatches().get(0).getData().size();
        CellRangeAddress headrow = new CellRangeAddress(rows, rows, 0, length - 1);
        this.matchsheet.addMergedRegion(headrow);
        RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, headrow, this.matchsheet, this.excelfile);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), headrow, this.matchsheet, this.excelfile);
        RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, headrow, this.matchsheet, this.excelfile);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), headrow, this.matchsheet, this.excelfile);
        RegionUtil.setBorderRight(CellStyle.BORDER_THIN, headrow, this.matchsheet, this.excelfile);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), headrow, this.matchsheet, this.excelfile);
        RegionUtil.setBorderTop(CellStyle.BORDER_THIN, headrow, this.matchsheet, this.excelfile);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), headrow, this.matchsheet, this.excelfile);
        rows++;
        
        Row colNms = this.matchsheet.createRow(rows++);
        customstyle = (XSSFCellStyle) this.excelfile.createCellStyle();
        customstyle.cloneStyleFrom(cellStyle);
        customstyle.setFillForegroundColor(title_bg_color);
        customstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        XSSFFont f1 = (XSSFFont) this.excelfile.createFont();
        f1.setColor(title_font_color);
        f1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        customstyle.setFont(f1);
        int cCN = 0;
        for (String s: this.mhColNms) {
            
            Cell c = colNms.createCell(cCN);
            c.setCellValue(createHelper.createRichTextString(s));
            c.setCellStyle(customstyle);
            cCN++;
            
        }
        
        customstyle = (XSSFCellStyle) this.excelfile.createCellStyle();
        customstyle.cloneStyleFrom(cellStyle);
        customstyle.setFillForegroundColor(oddrow_color);
        customstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        
        for (Match t: this.parser.getMatches()) {
            
            Row r = this.matchsheet.createRow(rows++);
            
            int cell = 0;
            
            for (String s: t.getData()) {
                
                Cell c = r.createCell(cell);
                
                c.setCellValue( createHelper.createRichTextString(s) );
                
                if (rows % 2 == 0)
                    c.setCellStyle(customstyle);
                else
                    c.setCellStyle(cellStyle);
                
                cell++;
               
            }
            
        }
        
        for (int i = 0; i < length; i++) {
            
            this.matchsheet.autoSizeColumn(i);
            
        }
        
    }
    
    public void saveXLSFile() {
        
        FileOutputStream output = null;
        
        try {
            output = new FileOutputStream(this.path + "\\" + this.parser.getCompetitionName() + ".xlsx");
        } catch (FileNotFoundException ex) {
            
        }
        
        if (output != null) {
            try {
                try {
                    this.excelfile.write(output);
                } catch (IOException ex) {
                    
                }
                
                output.close();
            } catch (IOException ex) {
                
            }
        }
        
    }
    
}
