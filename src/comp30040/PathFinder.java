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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

    public PathFinder(BiDynamicLineGraph g) {
        this.graph = g;
        this.next = new VertexBDLG[this.graph.getVertexCount()][this.graph.getVertexCount()];
        this.dist = new double[this.graph.getVertexCount()][this.graph.getVertexCount()];
    }

    public String printPath(List<PathPair> p) {
        String stringAsPaths = "";
        for (PathPair pp : p) {
            stringAsPaths += pp.toString();
        }
        stringAsPaths += '\n';
        return stringAsPaths;
    }

    public String printPaths() {
        Collections.sort(paths, new PathLengthComparator());
        String stringAsPaths = "";
        for (List<PathPair> path : paths) {
            stringAsPaths += printPath(path);
        }
        System.out.print(stringAsPaths);
        return stringAsPaths;
    }

    public ArrayList<List<PathPair>> getPaths() {
        Collections.sort(paths, new PathLengthComparator());
        return paths;
    }

    public void clearPaths() {
        paths.clear();
    }

    public void getPathsFrom(VertexBDLG i, Actor j, ArrayList<PathPair> currentPath) {
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
                    if ((this.dist[i][k] + this.dist[k][j]) < this.dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
        System.out.print("");
    }

    public void Path(VertexBDLG u, VertexBDLG v) {
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

    public void bfsParthsAll(VertexBDLG v, Actor a) {
        int numberOfVertexs = graph.getVertexCount();
        double[] dist = new double[numberOfVertexs];
        Queue<ArrayList<PathPair>> q = new LinkedList<>();
        for (int i = 0; i < numberOfVertexs; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[v.getId()] = 0;
        
        ArrayList startingPath = new ArrayList<PathPair>();
        startingPath.add(new PathPair(v, null));
        q.add(startingPath);

        while (!q.isEmpty()) {
            ArrayList<PathPair> currentPath = q.remove();
            PathPair currentVP = currentPath.get(currentPath.size() - 1);
            for (Object dest : graph.getSuccessors(currentVP.getVertex())) {
                
                ArrayList<PathPair> currentPath2 = new ArrayList<>(currentPath);
                if (((VertexBDLG) dest).equals(currentVP.v)
                    || ((currentVP.et == EdgeType.UNDIRECTED) 
                     && (graph.getEdgeType(currentVP.getVertex(), (VertexBDLG)dest)) == EdgeType.UNDIRECTED)) {
                    //System.out.println(graph.getEdgeType(currentVP.getVertex(), (VertexBDLG)dest));
                
                    continue;
                }
                if (((VertexBDLG) dest).getActor().equals(a)
                        && (!((VertexBDLG) dest).equals(currentVP.v))) {
                    currentPath2.remove(currentPath2.size() - 1);
                    PathPair pptmp = new PathPair(currentVP.v, currentVP.et);
                    pptmp.setEdgeType(graph.getEdgeType(currentVP.getVertex(), (VertexBDLG) dest));
                    currentPath2.add(pptmp);
                    currentPath2.add(new PathPair((VertexBDLG) dest, null));
                    this.paths.add(new ArrayList<>(currentPath2));
                    continue;
                    //PrintPath((VertexBDLG)dest, parent);
                }
                if (dist[((VertexBDLG) dest).getId()] == Integer.MAX_VALUE
                        && (!((VertexBDLG) dest).equals(currentVP.v))) {
                    dist[((VertexBDLG) dest).getId()] = dist[currentVP.getVertex().getId()] + 1;
                    currentPath2.remove(currentPath.size() - 1);
                    PathPair pptmp = new PathPair(currentVP.v, currentVP.et);
                    pptmp.setEdgeType(graph.getEdgeType(currentVP.getVertex(), (VertexBDLG) dest));
                    currentPath2.add(pptmp);
                    currentPath2.add(new PathPair((VertexBDLG) dest, null));
                    q.add(new ArrayList<>(currentPath2));
                    //this.paths.add(new ArrayList<PathPair>(currentPath));
                }
            }
        }
        System.out.print("");
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
}
