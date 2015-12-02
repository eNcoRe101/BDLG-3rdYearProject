package comp30040;

import java.util.Objects;

/**
 * Created by rich on 27/10/2015.
 */
public class Actor {
    private final int id;
    private final String label;

    public Actor(int id, String label){
        this.id = id;
        this.label = label;
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

    @Override
    public String toString(){
        return this.label;
    }
}
