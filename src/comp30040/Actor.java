package comp30040;

/**
 * Created by rich on 27/10/2015.
 */
public class Actor {
    private int id;
    private String label;

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

    public String toString(){
        return this.label;
    }
}
