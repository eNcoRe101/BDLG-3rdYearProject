/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import comp30040.Actor;
import comp30040.BiDynamicLineGraph;
import comp30040.GraphImporter;
import comp30040.NetworkEvent;
import comp30040.PathFinder;
import static comp30040.PathFinderType.SHORTEST_PATHS;
import comp30040.PathPair;
import comp30040.VertexBDLG;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author rich
 */
public class PathFinderTest {

    private GraphImporter imp;
    private final String relativePathToTestData = "./data/sample-2mode.csv";
    private BiDynamicLineGraph graph;

    public PathFinderTest() throws FileNotFoundException {
        this.imp = new GraphImporter(Paths.get(this.relativePathToTestData).toString());
        this.graph = new BiDynamicLineGraph(imp);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getPathForVertexToActor() {
        for (Actor a : graph.getActors()) {
            PathFinder p = new PathFinder(graph);
            for (Object v : graph.getVertices()) {

                if (((VertexBDLG) v).getActor().equals(imp.getActors()[1])
                        && ((VertexBDLG) v).getEvent().getEventId() > 1 && !a.equals(imp.getActors()[1])) {
                    System.out.println("\n" + ((VertexBDLG) v).toString() + " to " + a.getLabel());
                    p.getPathsFrom((VertexBDLG) v, a, new ArrayList<PathPair>());
                    p.printPaths();
                    assertEquals("Checking paths are correct",
                            "N2E2-N3E2->N3E3-N1E3\n"
                            + "N2E2-N4E2->N4E3-N1E3\n"
                            + "N2E2-N4E2->N4E3->N4E4-N1E4\n"
                            + "N2E2-N3E2->N3E3-N4E3->N4E4-N1E4\n"
                            + "", p.toString());
                    p.clearPaths();
                    return;
                }
            }
            p.printPaths();
        }
    }

    /*@Test
     public void getPathForVertexToactorFast(){
     ArrayList<PathPair> pairs = new ArrayList<>(1000);
     PathFinder p = new PathFinder(graph);
     p.FloydWarshallWithPathReconstruction();
     for (Object u : graph.getVertices()) {
     for (Object v : graph.getVertices()) {
     p.pathReconstuctor((VertexBDLG)u,(VertexBDLG)v);
     p.pathsToString();
     p.clearPaths();
                
     }
            
     }
     }*/
    @Test
    public void getPathFloydWarshall() {
        ArrayList<PathPair> pairs = new ArrayList<>(1000);
        PathFinder p = new PathFinder(graph);
        p.FloydWarshallWithPathReconstruction();
        for (Object u : graph.getVertices()) {
            for (Object v : graph.getVertices()) {
                //if (((VertexBDLG) u).getActor().equals(imp.getActors()[1])
                //        && ((VertexBDLG) u).getEvent().getEventId() > 1 && ((VertexBDLG) v).getActor().equals(imp.getActors()[0])) {
                //System.out.println("\n" + u.toString() + " to " + ((VertexBDLG) v).getActor().getLabel());
                p.pathReconstuctor((VertexBDLG) u, (VertexBDLG) v);
                p.printPaths();
                p.clearPaths();
                //}
            }
        }
    }
    
    @Test
    public void getPathDijkstra(){
        PathFinder p = new PathFinder(graph);
        for(VertexBDLG u : (Collection<VertexBDLG>) graph.getVertices()){
            for(VertexBDLG v : (Collection<VertexBDLG>) graph.getVertices()){
                System.out.println(u + " to " + v);
                p.dijkstra(u, v);
                p.printPaths();
                p.clearPaths();
                System.out.println();
            }
        }
    }
    
    /*
     @Test
     public void getPathForVertexToactorFast() {
     long numberOfPaths = 0;
     PathFinder p = new PathFinder(graph);
     PathFinder pp = new PathFinder(graph);
     for (Object v : graph.getVertices()) {
     for (Actor a : graph.getActors()) {
     if (((VertexBDLG) v).getEvent().getEventId() > 1) {
     System.out.println("\n" + ((VertexBDLG) v).toString() + " to " + a.getLabel());
     p.bfsParthsAll((VertexBDLG) v, a);
     numberOfPaths += p.getPaths().size();
     pp.getPathsFrom((VertexBDLG) v, a, new ArrayList<PathPair>());

     p.printPaths();
     System.out.println();
     pp.printPaths();
     assertEquals("Checking Paths are the same", p.toString(), pp.toString());
     p.clearPaths();
     pp.clearPaths();
     }
     }
     System.out.println();
     }

     System.out.println("Vertexs: " + graph.getVertexCount());
     System.out.println("Edges: " + graph.getEdgeCount());
     System.out.println("Paths Num: " + numberOfPaths);
     }*/
    
    @Test
    public void getPathForAllVertexsToAllActorsDijkstra() {
        long numberOfPaths = 0;
        PathFinder p = new PathFinder(graph, SHORTEST_PATHS);
        for (Actor a : graph.getActors()) {
            for (NetworkEvent e : graph.getEvents()) {
                for (Actor aa : graph.getActors()) {
                    VertexBDLG v = new VertexBDLG(a, e);
                    if ((!graph.containsVertex(v)) || (!(v.getActor().getId() == 0) && !(v.getEvent().getEventId() == 0)
                            && !(aa.getId() == 2))) {
                        continue;
                    }
                    System.out.println("\n" + v.toString() + " to " + aa.getLabel());
                    p.getPathsFrom(v, aa);
                    numberOfPaths += p.getPaths().size();
                    p.printPaths();

                    p.clearPaths();
                    System.out.println();
                    return;
                }
                System.out.println();
            }
        }

        System.out.println("Vertexs: " + graph.getVertexCount());
        System.out.println("Edges: " + graph.getEdgeCount());
        System.out.println("Paths Num: " + numberOfPaths);
    }

    @Test
    public void getPathForAllVertexsToAllActors() {
        long numberOfPaths = 0;
        PathFinder p = new PathFinder(graph);
        for (Actor a : graph.getActors()) {
            for (NetworkEvent e : graph.getEvents()) {
                for (Actor aa : graph.getActors()) {
                    VertexBDLG v = new VertexBDLG(a, e);
                    if ((!graph.containsVertex(v)) || (!(v.getActor().getId() == 0) && !(v.getEvent().getEventId() == 0)
                            && !(aa.getId() == 2))) {
                        continue;
                    }
                    System.out.println("\n" + v.toString() + " to " + aa.getLabel());
                    p.bfsParthsAll(v, aa);
                    numberOfPaths += p.getPaths().size();
                    p.printPaths();

                    p.clearPaths();
                    System.out.println();
                    return;
                }
                System.out.println();
            }
        }

        System.out.println("Vertexs: " + graph.getVertexCount());
        System.out.println("Edges: " + graph.getEdgeCount());
        System.out.println("Paths Num: " + numberOfPaths);
    }
    
    @Test
    public void testFastPathFinderDp(){
        PathFinder p = new PathFinder(graph);
        p.fastPathFinderDp();
    }
}
