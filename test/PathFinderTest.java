/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import comp30040.Actor;
import comp30040.BiDynamicLineGraph;
import comp30040.GraphImporter;
import comp30040.PathFinder;
import comp30040.VertexBDLG;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author rich
 */
public class PathFinderTest {

    private GraphImporter imp;
    private final String relativePathToTestData = "./data/mafia-2mode.csv";
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
    /*
    @Test
    public void getPathForVertexToActor() {
        
        ArrayList<PathPair> pairs = new ArrayList<>(1000);
        for (Actor a : graph.getActors()) {
            System.out.println("\nN2->" + a.getLabel());
            PathFinder p = new PathFinder(graph);
            for (Object v : graph.getVertices()) {

                if (((VertexBDLG) v).getActor().equals(imp.getActors()[1])
                        && ((VertexBDLG) v).getEvent().getEventId() > 1 && !a.equals(imp.getActors()[1])) {

                    p.getPathsFrom((VertexBDLG) v, a,pairs);
                    p.printPaths();
                    p.clearPaths();

                }
            }
            p.printPaths();
        }
        /*
        assertEquals(p.printPaths(), "N2E2-N3E2->N3E3-N1E3\n" +
                                     "N2E2-N4E2->N4E3-N1E3\n" +
                                     "N2E2-N4E2->N4E3->N4E4-N1E4\n" +
                                     "N2E2-N3E2->N3E3-N4E3->N4E4-N1E4\n");*/
    //}
    /*
    @Test
    public void getPathForVertexToactorFast(){
        ArrayList<PathPair> pairs = new ArrayList<>(1000);
        PathFinder p = new PathFinder(graph);
        p.FloydWarshallWithPathReconstruction();
        for (Object u : graph.getVertices()) {
            for (Object v : graph.getVertices()) {
                p.Path((VertexBDLG)u,(VertexBDLG)v);
                p.printPaths();
                p.clearPaths();
                
            }
            
        }
    }
    
    
    @Test 
    public void getPathForVertexToactorFast(){
        ArrayList<PathPair> pairs = new ArrayList<>(1000);
        PathFinder p = new PathFinder(graph);
        p.FloydWarshallWithPathReconstruction();
        for (Object u : graph.getVertices()) {
            p.bfsParths((VertexBDLG)u);
            p.printPaths();
            p.clearPaths();
        }
    }*/
    
    
    @Test
    public void getPathForVertexToactorFast(){
        for(Actor a : graph.getActors()){
            System.out.println("\nN2->" + a.getLabel());
            PathFinder p = new PathFinder(graph);
            for (Object v : graph.getVertices()) {
                //System.out.println(((VertexBDLG)));
                //if (((VertexBDLG) v).getActor().equals(imp.getActors()[1])
                //        && ((VertexBDLG) v).getEvent().getEventId() > 1 && !a.equals(imp.getActors()[1])) {
                    p.bfsParthsAll((VertexBDLG)v, a);
                    p.printPaths();
                    p.clearPaths();
                //    break;
                //}
                
            }
            System.out.println();
        }
    }
}
