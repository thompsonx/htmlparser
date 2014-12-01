/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlparser.parser;

/**
 *
 * @author Tom
 */
public class Team {
    private String score;
    private int matches, wins, draws, losses, points;
    
    
    public Team(int matches, int wins, int draws, int losses, String score, int points) {
        this.matches = matches;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.score = score;
        this.points = points;
    }
    
    @Override
    public String toString() {
        
        String info = Integer.toString(matches) + ","
                + Integer.toString(wins) + "," + Integer.toString(draws)
                + "," + Integer.toString(losses) + "," + score + ","
                + Integer.toString(points);
        
        return info;
        
    }
}
