/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htlmparser.control;

import htmlparser.parser.Parser;
import htmlparser.parser.Team;
import htmlparser.view.View;
import htmlparser.xls.XLSFile;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author Tom
 */
public class Controller {
    
    private View view;
    private Parser parser;
    private ArrayList<String> teams;
    
    public void setView(View v) {
        
        this.view = v;
        
    }
    
    public void getURL(String url) {
        
        System.out.println(url);
        
        this.parser = new Parser(url);
        
        if (this.parser.getTeams() != null) {
            
            this.parser.createTeams();
            
            this.getTeamNames();
            
            this.view.fillTeams(this.teams);
            this.view.urlSuccess();
            
        }
        
    }
    
    public void saveFile(String highlight) {
        
        XLSFile f = new XLSFile(this.parser, null);
        
        int teamno = -1;
        
        if (highlight != null) {
            for (String s: this.teams) {
                if (highlight.equals(s)) {
                    teamno = this.teams.indexOf(s);
                    break;
                }
            }
        }
        
        f.createScoreTable(IndexedColors.ORANGE.getIndex(), IndexedColors.RED.getIndex(), IndexedColors.WHITE.getIndex(), teamno);
        
        f.saveXLSFile();
        
    }
    
    private void getTeamNames() {
        
        this.teams = new ArrayList<>();
        
        for (Team t: this.parser.getTeams()) {
            
            this.teams.add(t.getData().get(0));
            
        }
        
    }
    
}
