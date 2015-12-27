/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author rich
 */

public class PathFinder {
    private BiDynamicLineGraph graph = null;
    
    public PathFinder(BiDynamicLineGraph g){
        this.graph = g;
    }
    public void printPaths(){
        for(ArrayList<PathPair> p : paths){
            for(PathPair pp : p){
                System.out.print(pp.v.toString());
                if(pp.et == null)
                {
                    continue;
                }
                switch (pp.et) {
                    case DIRECTED:
                        System.out.print("->");
                        break;
                    case UNDIRECTED:
                        System.out.print("-");
                        break;
                }
            }
            System.out.println();
        }
    }
    
    private ArrayList< ArrayList<PathPair>> paths = new ArrayList<>();
    public void getPathsFrom(VertexBDLG i, Actor j, ArrayList<PathPair> currentPath){
        Collection<VertexBDLG> currentVUndirectedEdges = graph.getSuccessors(i);
        if(currentVUndirectedEdges.size() < 1) return;
        
        //1.get all undirected edges
        //2.pick any vertical
        //3.if veriacl goes to actor go to 6
        //4.pick undirected
        //5.go to 2
        //6.end
        for(VertexBDLG v: currentVUndirectedEdges){
            if(graph.getEdgeType(graph.findEdge(i, v)) == EdgeType.UNDIRECTED)
            {
                if(j.equals(v.getActor()))
                {
                    ArrayList<PathPair> tmp = new ArrayList<>(currentPath);
                    tmp.add(new PathPair(i, EdgeType.UNDIRECTED));
                    tmp.add(new PathPair(v, null));
                    paths.add(tmp);
                }
                else
                {
                    for(Object vv: graph.getSuccessors(v)){
                        if(graph.getEdgeType(graph.findEdge(v, vv)) == EdgeType.DIRECTED){
                            ArrayList<PathPair> tmp = new ArrayList<>(currentPath);
                            if(!v.equals(i)){
                                tmp.add(new PathPair(i, EdgeType.UNDIRECTED));
                                tmp.add(new PathPair(v, EdgeType.DIRECTED));
                            }
                            else
                            {
                                tmp.add(new PathPair(v, EdgeType.DIRECTED));  
                            }
                            getPathsFrom((VertexBDLG) vv, j, tmp);
                        }
                    }
                }
            }   
        }
    }
}
