/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import static org.junit.Assert.*;

/**
 *
 * @author rich
 */
public class PathFinderTest {
    private GraphImporter imp;
    private final String relativePathToTestData = "./data/mafia-2mode.csv";
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
        PathFinder p = new PathFinder(graph);
        for(Object v : graph.getVertices())
        {
            if(((VertexBDLG) v).getActor().equals(imp.getActors()[24])
                && (((VertexBDLG) v).getEvent().equals(imp.getEvents()[28]))){
                p.getPathsFrom((VertexBDLG)v, imp.getActors()[1],"");
                break;
            }
        }
        p.printPaths();
        assertTrue(true);
    }
}
