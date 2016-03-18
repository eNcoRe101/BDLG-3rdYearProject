/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import java.util.Objects;

/**
 *
 * @author Richard de Mellow
 */
public class VertexBDLG {
    private final Actor a;
    private final NetworkEvent e;
    private double currentKnowlage = 0;
    private int id;
    private int priority;
    
    public VertexBDLG(Actor a, NetworkEvent e, int id){
        this.a = a;
        this.e = e;
        this.id = id;
    }
    
    public VertexBDLG(Actor a, NetworkEvent e){
        this.a = a;
        this.e = e;
    }
    
    public Actor getActor(){
        return a;
    }
    
    public NetworkEvent getEvent(){
        return e;
    }
    
    public void setKnowlage(double newK){
        this.currentKnowlage = newK;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public double getKnowlage(){
        return this.currentKnowlage;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setPiority(int p){
        this.priority = p;
    }
    
    public int getPriority(){
        return this.priority;
    }
    
    @Override
    public String toString(){
        return a.getLabel() + e.getLabel();
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(a, e);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VertexBDLG other = (VertexBDLG) obj;
        return Objects.equals(this.a, other.a)
                && Objects.equals(this.e, other.e);
    }
}
