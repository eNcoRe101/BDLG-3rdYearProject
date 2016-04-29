/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author rich
 */
public class PathFinder {

    private BiDynamicLineGraph graph = null;
    private PathFinderType currentPathFindingMethod;
    private ArrayList<List<PathPair>> paths = new ArrayList<>();
    private double[][] dist;
    private VertexBDLG[][] next;
    private int maxPathLength = -1; // value that sets the depth of the max length of a path to look for
    // were -1 reprosents that paths can be any length
    private boolean stopPathFinding = false;

    public PathFinder(BiDynamicLineGraph g) {
        this.graph = g;
        this.next = new VertexBDLG[this.graph.getVertexCount()][this.graph.getVertexCount()];
        this.dist = new double[this.graph.getVertexCount()][this.graph.getVertexCount()];
        this.currentPathFindingMethod = PathFinderType.BFS_ALL_PATHS;
    }
    
    public PathFinder(BiDynamicLineGraph g, PathFinderType pfType){
        this.graph = g;
        this.next = new VertexBDLG[this.graph.getVertexCount()][this.graph.getVertexCount()];
        this.dist = new double[this.graph.getVertexCount()][this.graph.getVertexCount()];
        this.currentPathFindingMethod = pfType;
    }

    public void setMaxPathLength(int max) {
        this.maxPathLength = max;
    }
    
    public void setPathFinderType(PathFinderType pfType){
        this.currentPathFindingMethod = pfType;
    }

    public int getMaxPathLength() {
        return this.maxPathLength;
    }

    public void stopPathFinding() {
        this.stopPathFinding = true;
    }

    public String pathToString(List<PathPair> p) {
        String stringAsPaths = "";
        for (PathPair pp : p) {
            stringAsPaths += pp.toString();
        }
        stringAsPaths += '\n';
        return stringAsPaths;
    }

    public void printPath(List<PathPair> p) {
        System.out.println(pathToString(p));
    }

    public void printPaths() {
        System.out.print(this.toString());
    }

    public ArrayList<List<PathPair>> getPaths() {
        Collections.sort(paths, new PathLengthComparator());
        return paths;
    }

    public void clearPaths() {
        paths.clear();
        System.gc();
    }
    
    public PathFinderType getPFType(){
        return this.currentPathFindingMethod;
    }
    
    public void getPathsFrom(VertexBDLG v, Actor a){
        switch(this.currentPathFindingMethod){
            case BFS_ALL_PATHS:
                //this.getPathsFrom(v, a, new ArrayList<PathPair>());
                this.bfsParthsAll(v, a);
                break;
            case SHORTEST_PATHS:
                for(NetworkEvent e : graph.getActorsEvents(a))
                    this.dijkstra(v, (VertexBDLG)graph.getVertex(a, e));
                break;
        }
        
    }
    
    /**
     * Original custom recursive path finding algo by Richard de Mellow
     * @param i
     * @param j
     * @param currentPath 
     */
    public void getPathsFrom(VertexBDLG i, Actor j, ArrayList<PathPair> currentPath) {
        if (i.getActor().equals(j)) {
            return;
        }
        Collection<VertexBDLG> currentVUndirectedEdges;
        if (currentPath.isEmpty()) {
            currentVUndirectedEdges = graph.getSuccessors(i, EdgeType.UNDIRECTED);
        } else {
            currentVUndirectedEdges = graph.getSuccessors(i);
        }

        if (currentVUndirectedEdges.size() < 1) {
            return;
        }

        //1.get all undirected edges
        //2.pick any vertical
        //3.if veriacl goes to actor go to 6
        //4.pick undirected
        //5.go to 2
        //6.end
        for (VertexBDLG v : currentVUndirectedEdges) {
            if (i.equals(v) && currentPath.isEmpty()) {
                continue;
            }
            if (((Edge) graph.findEdge(i, v)).getEdgeType() == EdgeType.UNDIRECTED
                    && j.equals(v.getActor())) {
                ArrayList<PathPair> tmp = new ArrayList<>(currentPath);
                tmp.add(new PathPair(i, EdgeType.UNDIRECTED));
                tmp.add(new PathPair(v, null));
                paths.add(tmp);
            } else if (!i.equals(v)) {
                ArrayList<PathPair> tmp = new ArrayList<>(currentPath);
                if (((Edge) graph.findEdge(i, v)).getEdgeType() == EdgeType.UNDIRECTED) {
                    tmp.add(new PathPair(i, EdgeType.UNDIRECTED));
                    tmp.add(new PathPair(v, EdgeType.DIRECTED));
                    for (Object vv : graph.getSuccessors(v, EdgeType.DIRECTED)) {
                        getPathsFrom((VertexBDLG) vv, j, tmp);
                    }
                } else if (graph.isOutEdge(i, v)) {
                    tmp.add(new PathPair(i, EdgeType.DIRECTED));
                    getPathsFrom((VertexBDLG) v, j, tmp);
                }
            }
        }
    }
    
    /**
     * Dijkstra algrithom shortest path
     * @param source
     * @param target
     */
    public void dijkstra(VertexBDLG source, VertexBDLG target){
        if(source.equals(target) || source.getEvent().getEventId() > target.getEvent().getEventId())
            return;
        int[] distLocal = new int[graph.getVertexCount()];
        VertexBDLG[] prevLocal = new VertexBDLG[graph.getVertexCount()];
        Comparator<VertexBDLG> vertexCompar = new VertexBDLGComparator();
        PriorityQueue<VertexBDLG> Q = new PriorityQueue<>(graph.getVertexCount(), vertexCompar);

        distLocal[source.getId()] = 0;
        ((VertexBDLG)graph.getVertex(source.getActor(), source.getEvent())).setPiority(0);
        
        for(VertexBDLG v : (Collection<VertexBDLG>) graph.getVertices()){
            if(!v.equals(source)){
                distLocal[v.getId()] = Integer.MAX_VALUE-1;
                v.setPiority(Integer.MAX_VALUE-1);
            }
            Q.add(v);
        }
        
        while(!Q.isEmpty()){
            VertexBDLG u = Q.poll();
            Collection<VertexBDLG> neighbors;
            if(source.equals(u))
                neighbors = graph.getSuccessors(u, EdgeType.UNDIRECTED);
            else
                neighbors = graph.getSuccessors(u);
            
            for(VertexBDLG v : neighbors){
                int alt = distLocal[u.getId()] + 1;
                if(alt < distLocal[v.getId()]
                   && graph.isSuccessor(u, v))
                 //  && (prevLocal[u.getId()] == null
                 //      || (graph.getEdgeType(u, v) != EdgeType.UNDIRECTED
                 //      && graph.getEdgeType(prevLocal[u.getId()], u) != EdgeType.UNDIRECTED)))
                        {
                    distLocal[v.getId()] = alt;
                    prevLocal[v.getId()] = u;
                    v.setPiority(alt);
                    Q.add(v);
                }
            }
        }
        VertexBDLG tmpV = target;
        Stack<VertexBDLG> s = new Stack<>();
        while(true){
            
            s.push(tmpV);
            if(prevLocal[tmpV.getId()] == null || prevLocal[tmpV.getId()].equals(tmpV))
                break;
            tmpV = prevLocal[tmpV.getId()];
        }
        //s.push(source);
        Path currentPath = new Path();
        tmpV = s.pop();
        boolean ok = currentPath.addNewPathPair(new PathPair(tmpV, null));
        while(!s.isEmpty()){
            if(!ok)
                System.out.println("Path creation problem");
            tmpV = s.pop();
            currentPath.getLastPath().et = graph.getEdgeType(currentPath.getLastPath().v, tmpV);
            ok = currentPath.addNewPathPair(new PathPair(tmpV, null));
        }
        if(currentPath.getNumberOfVertexs() > 1)
            this.paths.add(currentPath.getPathPairs());
    }
    
    /**
     * FloydWarshall with Path Reconstruction
     */
    public void FloydWarshallWithPathReconstruction() {
        int numberOfVertexs = graph.getVertexCount();
        for (int i = 0; i < numberOfVertexs; i++) {
            for (int j = 0; j < numberOfVertexs; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        for (Object e : this.graph.getEdges()) {
            int u = ((VertexBDLG) graph.getIncidentVertices(e).toArray()[0]).getId();
            int v = ((VertexBDLG) graph.getIncidentVertices(e).toArray()[1]).getId();
            if (u == v) {
                this.dist[u][v] = Integer.MAX_VALUE;
            } else if (((Edge) e).getEdgeType() == EdgeType.DIRECTED) {
                this.dist[u][v] = 1;
            } else {
                this.dist[u][v] = 0.5;
            }

            this.next[u][v] = (VertexBDLG) graph.getIncidentVertices(e).toArray()[1];
        }

        for (int k = 0; k < numberOfVertexs; k++) {
            for (int i = 0; i < numberOfVertexs; i++) {
                for (int j = 0; j < numberOfVertexs; j++) {
                    if ((this.dist[i][k] + this.dist[k][j]) < this.dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
        System.out.println("FloydWarshall finished");
    }
    
    /**
     * Path reconstruction method for FloydWarshall algorithom 
     * @param u
     * @param v 
     */
    public void pathReconstuctor(VertexBDLG u, VertexBDLG v) {
        List<PathPair> tmpPath = new ArrayList<>();
        if (this.next[u.getId()][v.getId()] != null) {
            while (!u.equals(v)) {
                tmpPath.add(new PathPair(u, this.graph.getEdgeType(u, next[u.getId()][v.getId()])));
                u = next[u.getId()][v.getId()];
            }
            tmpPath.add(new PathPair(u, null));
            this.paths.add(tmpPath);
        }
    }
    
    
    /**
     * Custom path finding algo by Richard de Mellow
     */
    public void fastPathFinderDp() {
        HashMap<VertexBDLG, HashMap<Actor, ArrayList<Path>>> pathsFromVectorToActor = new HashMap<>();
        for (int i = graph.getEvents().length; i > 0; i--) {
            for (Actor a : graph.getEvents()[i - 1].getActorsAtEvent()) {
                VertexBDLG v = new VertexBDLG(a, graph.getEvents()[i - 1]);
                HashMap tmpRef = pathsFromVectorToActor.get(v);

                if (tmpRef == null) {
                    tmpRef = new HashMap<>();
                    pathsFromVectorToActor.put(v, tmpRef);
                }
                for (VertexBDLG neighbour : (Collection<VertexBDLG>) graph.getSuccessors(v)) {
                    Path p = new Path();
                    boolean storePath = p.addNewPathPair(new PathPair(v, graph.getEdgeType(v, neighbour)));
                    p.addNewPathPair(new PathPair(neighbour, null));
                    if (pathsFromVectorToActor.get(v).get(neighbour.getActor()) == null) {
                        pathsFromVectorToActor.get(v).put(neighbour.getActor(), new ArrayList<Path>());
                    }
                    if (storePath) {
                        pathsFromVectorToActor.get(v).get(neighbour.getActor()).add(p);
                        if (pathsFromVectorToActor.get(neighbour) != null) {
                            for (Actor aa : pathsFromVectorToActor.get(neighbour).keySet()) {
                                if (aa == null) {
                                    continue;
                                }
                                for (Path pp : pathsFromVectorToActor.get(neighbour).get(aa)) {
                                    if (pp == null) {
                                        continue;
                                    }
                                    Path newPath = new Path();
                                    newPath.addNewPathPair(new PathPair(v, graph.getEdgeType(v, neighbour)));
                                    boolean valid = newPath.addPath(pp);
                                    if (pathsFromVectorToActor.get(v).get(newPath.getLastActor()) == null) {
                                        pathsFromVectorToActor.get(v).put(newPath.getLastActor(), new ArrayList<Path>());
                                    }
                                    if(valid)
                                        pathsFromVectorToActor.get(v).get(newPath.getLastActor()).add(newPath);
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("finished fast path finder dp");
    }
    
    Set<ArrayList<PathPair>> seen;
    /*
    private boolean stuck(VertexBDLG v, Actor a) {
        Stack<VertexBDLG> q = new Stack<>();
        int i = 0;
        q.push(v);
        while (!q.isEmpty()) {
            VertexBDLG vTmp = q.pop();
            if (v.getActor().equals(a)) {
                return false;
            }
            for (VertexBDLG y : (Collection<VertexBDLG>) graph.getSuccessors(v)) {
                ArrayList<PathPair> currentPath = new ArrayList<>();
                currentPath.add(new PathPair(y, null));
                if (!seen.contains(currentPath)) {
                    seen.add(currentPath);
                    q.push(y);
                }
            }
            i++;
        }
        return true;
    }*/
    Queue<ArrayList<PathPair>> q = new LinkedList<>();
    
    /**
     * Working breath first search that uses a FILO queue
     * @param v
     * @param a 
     */
    public void bfsParthsAll(VertexBDLG v, Actor a) {
        if (v.getActor().equals(a)) {
            return;
        }
        int numberOfVertexs = graph.getVertexCount();
        double[] distL = new double[numberOfVertexs];
        for (int i = 0; i < numberOfVertexs; i++) {
            distL[i] = Integer.MAX_VALUE;
        }
        distL[v.getId()] = 0;

        ArrayList startingPath = new ArrayList<>();
        startingPath.add(new PathPair(v, null));
        q.add(startingPath);

        while (!q.isEmpty() && !stopPathFinding) {

            ArrayList<PathPair> currentPath = q.remove();
            PathPair currentVP = currentPath.get(currentPath.size() - 1);
            if (((this.maxPathLength != -1) && (currentPath.size() >= this.maxPathLength))) {
                continue;
            }
            Collection<VertexBDLG> neighbours;
            if (currentVP.v.equals(v)) {
                neighbours = graph.getSuccessors(currentVP.v, EdgeType.UNDIRECTED);
            } else {
                neighbours = graph.getSuccessors(currentVP.v);
            }
            if (neighbours == null) {
                continue;
            }
            for (VertexBDLG dest : neighbours) {
                ArrayList<PathPair> currentPath2 = new ArrayList<>(currentPath);
                EdgeType currentEt = graph.getEdgeType(currentVP.v, dest);
                if (dest.equals(currentVP.v)
                        || (currentPath2.size() > 1)
                        && (currentPath2.get(currentPath2.size() - 2).et == EdgeType.UNDIRECTED)
                        && (currentEt == EdgeType.UNDIRECTED)
                        && distL[dest.getId()] != Integer.MAX_VALUE) {
                    continue;
                }
                if (dest.getActor().equals(a)) {
                    distL[dest.getId()] = distL[currentVP.getVertex().getId()] + 1;

                    PathPair pptmp2 = currentPath2.remove(currentPath2.size() - 1);
                    PathPair pptmp = new PathPair(currentVP.v, currentVP.et);
                    pptmp.setEdgeType(graph.getEdgeType(pptmp2.getVertex(), dest));
                    currentPath2.add(pptmp);
                    currentPath2.add(new PathPair((VertexBDLG) dest, null));
                    if ((this.maxPathLength == -1) || (currentPath2.size() <= this.maxPathLength)) {
                        this.paths.add(new ArrayList<>(currentPath2));
                    }
                } else {
                    distL[dest.getId()] = distL[currentVP.getVertex().getId()] + 1;
                    PathPair pptmp2 = currentPath2.remove(currentPath2.size() - 1);
                    PathPair pptmp = new PathPair(currentVP.v, currentVP.et);
                    pptmp.setEdgeType(graph.getEdgeType(pptmp2.getVertex(), dest));
                    currentPath2.add(pptmp);
                    currentPath2.add(new PathPair(dest, null));
                    if ((this.maxPathLength == -1) || (currentPath2.size() <= this.maxPathLength)) {
                        q.add(new ArrayList<>(currentPath2));
                    }
                }
            }
        }
        this.stopPathFinding = false;
    }
    
    /**
     * bfs algo that doesnt work properly 
     * @param v 
     *//*
    public void bfsParths(VertexBDLG v) {
        int numberOfVertexs = graph.getVertexCount();
        double[] distLocal = new double[numberOfVertexs];
        VertexBDLG[] parent = new VertexBDLG[numberOfVertexs];
        for (int i = 0; i < numberOfVertexs; i++) {
            distLocal[i] = Integer.MAX_VALUE;
        }
        distLocal[v.getId()] = 0;
        Queue<VertexBDLG> qLocal = new LinkedList<>();
        qLocal.add(v);
        while (!qLocal.isEmpty()) {
            VertexBDLG currentV = qLocal.remove();
            for (Object dest : graph.getSuccessors(currentV)) {
                if (distLocal[((VertexBDLG) dest).getId()] == Integer.MAX_VALUE) {
                    distLocal[((VertexBDLG) dest).getId()] = distLocal[currentV.getId()] + 1;
                    parent[((VertexBDLG) dest).getId()] = currentV;
                    qLocal.add(((VertexBDLG) dest));
                }
            }
        }
        System.out.print("");
    }*/
    
    /**
     * Method that is probably redundent
     * @param v
     * @param p 
     *//*
    private void PrintPath(VertexBDLG v, ArrayList<Queue<VertexBDLG>> p) {
        VertexBDLG currentV = v;
        Stack<PathPair> s = new Stack<>();
        while (true) {
            //s.push(new PathPair(currentV, graph.getEdgeType(currentV, p.getcurrentV.getId()))));
            if (p.get(currentV.getId()) != null) {

                //    currentV = p[currentV.getId()];
            } else {
                break;
            }
        }
        List<PathPair> aPath = new ArrayList<>();
        while (!s.isEmpty()) {
            aPath.add(s.pop());
        }
        this.paths.add(aPath);
    }*/

    @Override
    public String toString() {
        Collections.sort(paths, new PathLengthComparator());
        String stringAsPaths = "";
        for (List<PathPair> path : paths) {
            stringAsPaths += pathToString(path);
        }
        return stringAsPaths;
    }
}
