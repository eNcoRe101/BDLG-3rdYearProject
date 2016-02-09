package comp30040;

import java.awt.Color;
import java.util.Random;

/**
 * Created by rich on 27/10/2015.
 */
public class Actor {
    private final int id;
    private final String label;
    private final Color colour;

    public Actor(int id, String label){
        this.id = id;
        this.label = label;
        Random randGen = new Random();
        this.colour = new Color(randGen.nextInt(256),
                                randGen.nextInt(256),
                                randGen.nextInt(256));
    }

    public int getId(){
        return this.id;
    }

    public String getLabel(){
        return this.label;
    }
    
    public boolean equals (Actor obj){
        return (this.id == obj.getId()) && (this.label.equals(obj.getLabel()));
    }
    
    public Color getColor(){
        return this.colour;
    }

    @Override
    public String toString(){
        return this.label;
    }
}
