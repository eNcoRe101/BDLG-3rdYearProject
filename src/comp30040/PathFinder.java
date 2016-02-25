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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author rich
 */
public class PathFinder {

    private BiDynamicLineGraph graph = null;
    private ArrayList< List<PathPair>> paths = new ArrayList<>();
    private double[][] dist;
    private VertexBDLG[][] next;
    private int maxPathLength = -1; // value that sets the depth of the max length of a path to look for
    // were -1 reprosents that paths can be any length
    private boolean stopPathFinding = false;

    public PathFinder(BiDynamicLineGraph g) {
        this.graph = g;
        this.next = new VertexBDLG[this.graph.getVertexCount()][this.graph.getVertexCount()];
        this.dist = new double[this.graph.getVertexCount()][this.graph.getVertexCount()];
    }
    
    public void setMaxPathLength(int max){
        this.maxPathLength = max;
    }
    
    public int getMaxPathLength(){
        return this.maxPathLength;
    }
    
    public void stopPathFinding(){
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
    
    public void printPath(List<PathPair> p){
        System.out.println(pathToString(p));
    }

    public void printPaths(){
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

    public void getPathsFrom(VertexBDLG i, Actor j, ArrayList<PathPair> currentPath) {
        if(i.getActor().equals(j))
            return;
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
                this.dist[u][v] = 0;
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
                    //if ((this.dist[i][k] + this.dist[k][j]) < this.dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    //}
                }
            }
        }
        System.out.print("FloydWarshall finished");
    }

    public void pathReconstuctor(VertexBDLG u, VertexBDLG v) {
        List<PathPair> tmpPath = new ArrayList<>();
        if (this.next[u.getId()][v.getId()] != null) {
            while (!u.equals(v)) {
                tmpPath.add(new PathPair(u, this.graph.getEdgeType(u, next[u.getId()][v.getId()])));
                u = next[u.getId()][v.getId()];
            }
            this.paths.add(tmpPath);
        }
    }

    public void bfsParths(VertexBDLG v) {
        int numberOfVertexs = graph.getVertexCount();
        double[] dist = new double[numberOfVertexs];
        VertexBDLG[] parent = new VertexBDLG[numberOfVertexs];
        for (int i = 0; i < numberOfVertexs; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[v.getId()] = 0;
        Queue<VertexBDLG> q = new LinkedList<>();
        q.add(v);
        while (!q.isEmpty()) {
            VertexBDLG currentV = q.remove();
            for (Object dest : graph.getSuccessors(currentV)) {
                if (dist[((VertexBDLG) dest).getId()] == Integer.MAX_VALUE) {
                    dist[((VertexBDLG) dest).getId()] = dist[currentV.getId()] + 1;
                    parent[((VertexBDLG) dest).getId()] = currentV;
                    q.add(((VertexBDLG) dest));
                }
            }
        }
        System.out.print("");
    }
    Set<ArrayList<PathPair>> seen;

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
    }
    Queue<ArrayList<PathPair>> q = new LinkedList<>();

    public void bfsParthsAll(VertexBDLG v, Actor a) {
        if(v.getActor().equals(a))
            return;
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
            if(currentVP.v.equals(v))
                neighbours = graph.getSuccessors(currentVP.v, EdgeType.UNDIRECTED);
            else
                neighbours = graph.getSuccessors(currentVP.v);
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
                    if((this.maxPathLength == -1) || (currentPath2.size() <= this.maxPathLength))
                        this.paths.add(new ArrayList<>(currentPath2));
                } else {
                    distL[dest.getId()] = distL[currentVP.getVertex().getId()] + 1;
                    PathPair pptmp2 = currentPath2.remove(currentPath2.size() - 1);
                    PathPair pptmp = new PathPair(currentVP.v, currentVP.et);
                    pptmp.setEdgeType(graph.getEdgeType(pptmp2.getVertex(), dest));
                    currentPath2.add(pptmp);
                    currentPath2.add(new PathPair(dest, null));
                    if((this.maxPathLength == -1) || (currentPath2.size() <= this.maxPathLength))
                        q.add(new ArrayList<>(currentPath2));
                }
            }
        }
        this.stopPathFinding = false;
    }

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
    }
    
    @Override
    public String toString(){
        Collections.sort(paths, new PathLengthComparator());
        String stringAsPaths = "";
        for (List<PathPair> path : paths) {
            stringAsPaths += pathToString(path);
        }
        return stringAsPaths;
    }
}
