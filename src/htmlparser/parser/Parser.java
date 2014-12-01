/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.parser;

import java.io.IOException;
import java.util.HashMap;
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
    private HashMap<String, Team> teams;
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
        
        this.teams = new HashMap<>();
        
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
            
            String name = cells.get(1).text();
            int matches = Integer.parseInt(cells.get(2).text());
            int wins = Integer.parseInt(cells.get(3).text());
            int draws = Integer.parseInt(cells.get(4).text());
            int losses = Integer.parseInt(cells.get(5).text());
            String score = cells.get(6).text();
            int points = Integer.parseInt(cells.get(7).text());
            
            Team t = new Team(matches, wins, draws, losses, score, points);
            
            this.teams.put(name, t);
            
            System.out.println(t);
        }
        
    }
    
    public HashMap<String, Team> getTeams() {
        
        return this.teams;
        
    }
    
    public String getCompetitionName() {
        
        return this.competition_name;
        
    }
}
