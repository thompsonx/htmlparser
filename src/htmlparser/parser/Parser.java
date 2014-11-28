/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.parser;

import java.io.IOException;
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
    
    public Parser(String url) {
        Document doc = null;
        try {
            // TODO code application logic here
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (doc == null)
            return;
        
        this.data = this.cleanPage(doc);
        
        System.out.println(this.data.html());
        
    }
    
    private Elements cleanPage(Document doc) {
        
        Element content = doc.select("table[border=0][cellspacing=0][cellpadding=1][width=470]").get(1);

        Elements data = content.getElementsByTag("tr");
        for (int i = 0; i < 3; i++)
            data.remove(0);
        
        Elements emptylines = data.select("tr[bgcolor=#aaaaaa]");
        
        for (Element o: emptylines) {
            data.remove(o);
        }
        
        return data;
    }
    
}
