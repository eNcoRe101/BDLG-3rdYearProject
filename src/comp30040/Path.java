/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;

/**
 *
 * @author rich
 */
public class Path {
    private ArrayList<PathPair> path = null;
            
    public Path(){
        this.path = new ArrayList<>();
    }
    
    public Path(ArrayList<PathPair> p){
        this.path = p;
    }
    
    public void setPathWithCopy(ArrayList<PathPair> p){
        this.path = new ArrayList<>(p);
    }
    
    public Actor getLastActor(){
        if(path.isEmpty())
            return null;
        return path.get(path.size()-1).v.getActor();
    }
    
    public boolean addPath(Path p){
        if(this.path.get(path.size()-1).et == EdgeType.UNDIRECTED
            && p.getPathPairs().get(0).et == EdgeType.UNDIRECTED)
            return false;
        this.path.addAll(p.getPathPairs());
        return true;
    }
    
    public ArrayList<PathPair> getPathPairs(){
        return this.path;
    }
    
    public boolean addNewPathPair(PathPair p){
        if(this.path.isEmpty()){
            if (p.et == null || p.et == EdgeType.UNDIRECTED){
                this.path.add(p);
                return true;
            }
            else
                return false;
        }
        else{
            EdgeType lastEdge = this.path.get(this.path.size()-1).et;
            if(lastEdge != null && lastEdge == EdgeType.UNDIRECTED
               && p.et != null && p.et == EdgeType.UNDIRECTED){
                return false;
            }
            else{
                this.path.add(p);
                return true;
            }
        }
    }
    
}
