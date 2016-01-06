/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import comp30040.Actor;
import comp30040.BiDynamicLineGraph;
import comp30040.GraphImporter;
import comp30040.PathFinder;
import comp30040.PathPair;
import comp30040.VertexBDLG;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rich
 */
public class PathFinderTest {
    private GraphImporter imp;
    private final String relativePathToTestData = "./data/sample-2mode.csv";
    private BiDynamicLineGraph graph;
    
    public PathFinderTest() throws FileNotFoundException{
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
    public void getPathForVertexToActor(){
        PathFinder p;
        ArrayList<PathPair> pairs = new ArrayList<>(1000);
        for(Actor a : graph.getActors())
            {
                p = new PathFinder(graph);
        System.out.println("2->" + a.getLabel());
        for(Object v : graph.getVertices())
        {
            
            
                if(((VertexBDLG) v).getActor().equals(imp.getActors()[1]) 
                    && ((VertexBDLG) v).getEvent().getEventId() > 1 && !a.equals(imp.getActors()[1])){
                    
                    p.getPathsFrom((VertexBDLG)v, a,pairs );
                   
                }
            }
         p.printPaths();
        }

        /*assertEquals(p.printPaths(), "N2E2-N3E2->N3E3-N1E3\n" +
                                     "N2E2-N4E2->N4E3-N1E3\n" +
                                     "N2E2-N4E2->N4E3->N4E4-N1E4\n" +
                                     "N2E2-N3E2->N3E3-N4E3->N4E4-N1E4\n");*/
    }
}
