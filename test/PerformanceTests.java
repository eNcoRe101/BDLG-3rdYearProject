
import comp30040.Actor;
import comp30040.BiDynamicLineGraph;
import comp30040.GraphImporter;
import comp30040.KnowledgeDiffusionCalculator;
import static comp30040.PathFinderType.SHORTEST_PATHS;
import static comp30040.PathFinderType.BFS_ALL_PATHS;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rich
 */
public class PerformanceTests {
    private GraphImporter imp;
    private final String relativePathToTestData = "./perfData/sampledata-4.csv";
    private BiDynamicLineGraph graph;
    
    public PerformanceTests() throws FileNotFoundException{
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
    public void getKnowlageDiffusionForUsingBFS(){
        System.out.println("Performance Times BFS");
        long[] times = new long[10];
        long startTime = 0;
        for(int i = 0; i < 10; i++){
            startTime = System.nanoTime();
            KnowledgeDiffusionCalculator k = new KnowledgeDiffusionCalculator(graph, 
                                                                              BFS_ALL_PATHS);
            times[i] = System.nanoTime() - startTime;
        }
        System.out.println(Arrays.toString(times));
    }
    
    @Test
    public void getKnowlageDiffusionForUsingBFSUpto3(){
        System.out.println("Performance Times BFS max length 3");
        long[] times = new long[10];
        long startTime = 0;
        for(int i = 0; i < 10; i++){
            
            KnowledgeDiffusionCalculator k = new KnowledgeDiffusionCalculator(graph, 
                                                                              BFS_ALL_PATHS);
            startTime = System.nanoTime();
            k.updateMaxPathLength(6);
            k.refreshKnowlageDifusionValues();
            times[i] = System.nanoTime() - startTime;
        }
        System.out.println(Arrays.toString(times));
    }
    
    @Test
    public void getKnowlageDiffusionForUsingDij(){
        System.out.println("Performance Times Dijstara");
        long[] times = new long[10];
        long startTime = 0;
        for(int i = 0; i < 10; i++){
            startTime = System.nanoTime();
            KnowledgeDiffusionCalculator k = new KnowledgeDiffusionCalculator(graph, 
                                                                              SHORTEST_PATHS);
            times[i] = System.nanoTime() - startTime;
        }
        System.out.println(Arrays.toString(times));
    }
}
