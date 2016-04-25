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
    private int numberOfUndirectedEdges = 0;
            
    public Path(){
        this.path = new ArrayList<>();
    }
    
    public Path(ArrayList<PathPair> p){
        this.path = p;
    }
    
    public int getNumUndirectedEdges(){
        return numberOfUndirectedEdges;
    }
    
    public void setPathWithCopy(ArrayList<PathPair> p){
        this.path = new ArrayList<>(p);
    }
    
    public Actor getLastActor(){
        if(path.isEmpty())
            return null;
        return path.get(path.size()-1).v.getActor();
    }
    
    public int getNumberOfVertexs(){
        return this.path.size();
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
    
    public PathPair getLastPath(){
        if(!this.path.isEmpty())
            return this.path.get(path.size()-1);
        return null;
    }
    
    public boolean addNewPathPair(PathPair p){
        if(this.path.isEmpty()){
            if (p.et == null || p.et == EdgeType.UNDIRECTED){
                this.path.add(p);
                if(p.et == EdgeType.UNDIRECTED)
                    this.numberOfUndirectedEdges++;
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
                if(p.et == EdgeType.UNDIRECTED)
                    this.numberOfUndirectedEdges++;
                this.path.add(p);
                return true;
            }
        }
    }
    
}
