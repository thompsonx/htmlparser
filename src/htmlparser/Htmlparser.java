/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser;

import htlmparser.control.Controller;
import htmlparser.parser.Parser;
import htmlparser.view.View;
import htmlparser.xls.XLSFile;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author Tom
 */
public class Htmlparser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Controller control = new Controller();
        View gui = new View(control);
        control.setView(gui);
        gui.setVisible(true);
        
//        Parser p = new Parser("http://nv.fotbal.cz/domaci-souteze/kao/moravskoslezsky/opava/souteze.asp?soutez=815A1A");
//        
//        XLSFile f = new XLSFile(p, null);
//        
//        f.createScoreTable(IndexedColors.ORANGE.getIndex(), IndexedColors.RED.getIndex(), IndexedColors.WHITE.getIndex());
//        
//        f.saveXLSFile("file.xlsx");
        
    }
    
}
