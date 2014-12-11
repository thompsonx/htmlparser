/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private ArrayList<Match> matches;
    private String competition_name;
    private String team_name;
    private String url;
    
    public Parser(String url) {
        
        Document doc = null;
        
        this.url = url;
        
        try {
            doc = Jsoup.connect(this.url).get();
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (doc == null)
            return;
        
        this.data = this.cleanPage(doc);
        
        this.teams = new ArrayList<>();
        this.matches = new ArrayList<>();
    }
    
    private Elements cleanPage(Document doc) {
        
        Element content = doc.select("td[width=500][valign=top]").get(0);
        content = content.getElementsByTag("h4").get(0);
        
        this.competition_name = content.text();
        
        content = doc.select("table[border=0][cellspacing=0][cellpadding=1][width=470]").get(1);

        Elements datas = content.getElementsByTag("tr");
        for (int i = 0; i < 3; i++)
            datas.remove(0);
        
        Elements emptylines = datas.select("tr[bgcolor=#aaaaaa]");
        
        for (Element o: emptylines) {
            datas.remove(o);
        }
        
        return datas;
    }
    
    public void createTeams() {
        
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
    
    
    public void createMatches(String team) {
        
        this.team_name = team;
        
        this.matches = new ArrayList<>();
        
        Document doc = null;
        
        try {
            doc = Jsoup.connect(this.url + "&show=Los").get();
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (doc != null) {
            
            Elements content = doc.select("table[border=0][cellspacing=0][cellpadding=1][width=470]");
        
            List<Elements> tables = new ArrayList<>();

            for (Element e: content) {

                tables.add(e.getElementsByTag("tr"));

            }

            for (Elements t: tables) {
                t.remove(0);
                t.remove(0);
                
                Elements emptylines = t.select("tr[bgcolor=#aaaaaa]");
                
                for (Element o: emptylines) {
                t.remove(o);
                }

                for (Element e: t) {
                    Elements cells = e.getElementsByTag("td");
                    cells.remove(0);

                    if (cells.get(0).text().equals(team) || cells.get(1).text().equals(team)) {
                        
                        ArrayList<String> matchdata = new ArrayList<>();
                        
                        for (Element cell: cells) {
                            
                            matchdata.add(cell.text());
                            
                        }
                        
                        Match m = new Match(matchdata);
                        this.matches.add(m);
                        System.out.println(m);
                        break;
                    }

                }
                
            }
            
            
                        
        }
        
    }
    
    public ArrayList<Team> getTeams() {
        
        return this.teams;
        
    }
    
    public ArrayList<Match> getMatches() {
        
        return this.matches;
        
    }
    
    public String getTeamName() {
        
        return this.team_name;
        
    }
    
    public String getCompetitionName() {
        
        return this.competition_name;
        
    }
}
