/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.xls;

import htmlparser.parser.Parser;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
    
    public XLSFile(Parser parser) {
        
        this.parser = parser;
        this.excelfile = new XSSFWorkbook();
        
        this.scoresheet = null;
        this.matchsheet = null;
        
       
    }
    
    public void createScoreTable() {
 
    }
    
    public void createMatchTable() {
        
    }
    
    public void saveXLSFile(String filepath) {
        
        FileOutputStream output = null;
        
        try {
            output = new FileOutputStream(filepath);
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
