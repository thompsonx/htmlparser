/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Tom
 */
public class Parser {
    
    private Elements data;
    private ArrayList<Team> teams;
    private String competition_name;
    
    public Parser(String url) {
        
        Document doc = null;
        
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (doc == null)
            return;
        
        this.data = this.cleanPage(doc);
        
        this.teams = new ArrayList<>();
        
        this.createTeams();
        
    }
    
    private Elements cleanPage(Document doc) {
        
        Element content = doc.select("td[width=500][valign=top]").get(0);
        content = content.getElementsByTag("h4").get(0);
        
        this.competition_name = content.text();
        
        content = doc.select("table[border=0][cellspacing=0][cellpadding=1][width=470]").get(1);

        Elements data = content.getElementsByTag("tr");
        for (int i = 0; i < 3; i++)
            data.remove(0);
        
        Elements emptylines = data.select("tr[bgcolor=#aaaaaa]");
        
        for (Element o: emptylines) {
            data.remove(o);
        }
        
        return data;
    }
    
    private void createTeams() {
        
        for(Element e: this.data) {
            Elements cells = e.getElementsByTag("td");
            
            cells.remove(0);
            cells.remove(cells.last());
            cells.remove(cells.last());
            
            ArrayList<String> team = new ArrayList<>();
            
            for (Element ce: cells) {
                
                team.add(ce.text());
                
            }
            
            Team t = new Team(team);
            
            this.teams.add(t);
            
            System.out.println(t);
        }
        
    }
    
    public ArrayList<Team> getTeams() {
        
        return this.teams;
        
    }
    
    public String getCompetitionName() {
        
        return this.competition_name;
        
    }
}
