/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.parser;

import java.util.ArrayList;

/**
 *
 * @author Tom
 */
public class Match {
    private ArrayList<String> data;
    
    
    public Match(ArrayList<String> data) {
        
        this.data = data;
    }
    
    public ArrayList<String> getData() {
        
        return this.data;
        
    }
    
    @Override
    public String toString() {
        
        StringBuilder str = new StringBuilder();
        
        for (String s: this.data) {
            
            str.append(s).append(" ");
            
        }
        
        return str.toString();
        
    }
}
