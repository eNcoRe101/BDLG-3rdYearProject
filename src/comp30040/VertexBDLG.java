/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

/**
 *
 * @author Richard de Mellow
 */
public class VertexBDLG {
    private Actor a = null;
    private NetworkEvent e = null;
    
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
    
    @Override
    public String toString(){
        return a.getLabel() + e.getLabel();
    }
}
