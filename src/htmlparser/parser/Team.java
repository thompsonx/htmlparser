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
public class Team {
    private ArrayList<String> data;
    
    
    public Team(ArrayList<String> data) {
        
        this.data = new ArrayList<>();
        
        for (String s: data){
            
            this.data.add(s);
            
        }
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
