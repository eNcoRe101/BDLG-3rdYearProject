/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import comp30040.Actor;
import comp30040.BiDynamicLineGraph;
import comp30040.GraphImporter;
import comp30040.KnowledgeDiffusionCalculator;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
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
public class KnowledgeDiffusionCalculatorTest {
    private GraphImporter imp;
    private final String relativePathToTestData = "./data/sample-2mode.csv";
    private BiDynamicLineGraph graph;
    
    public KnowledgeDiffusionCalculatorTest() throws FileNotFoundException{
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
    public void getKnowlageDiffusionForActor1(){
        KnowledgeDiffusionCalculator k = new KnowledgeDiffusionCalculator(graph);
        double[][] tmp3 = new double[imp.getNumberOfActors()][imp.getNumberOfActors()];
        double[][] KnowlageTable = {{0.0, 0.0, 0.5, 1.25, 1.125, 0.0},
                                    {0.630859375, 0.0, 0.625, 0.671875, 0.49755859375, 0.5},
                                    {0.625, 0.0, 0.0, 0.625, 0.4375, 0.0},
                                    {1.25, 0.0, 0.5, 0.0, 1.125, 0.0},
                                    {0.5, 0.0, 0.0, 0.5, 0.0, 0.0},
                                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};
        int ii = 0;
        for(Actor i : imp.getActors())
        {
            int jj = 0;
            for(Actor j : imp.getActors()){
                System.out.println("" + i + " to " + j);
                double[] tmp = k.getKnowlageFromActorsAsArray(i, j);
                double tmp2 = 0;
                for(double d : tmp)
                    tmp2 += d;
                tmp3[ii][jj] = tmp2;
                System.out.println(Arrays.toString(tmp));
                System.out.println(tmp2);
                assertEquals("Checking that actors " + i  + " and " + j + " are correct",
                             KnowlageTable[i.getId()-1][j.getId()-1], 
                             tmp2,
                             0.00001);
                
                jj++;
            }
            ii++;
        }
        System.out.println();
        for(int i = 0; i < tmp3.length; i++){
            System.out.print("[" + (i+1) + "|");
            for(int j = 0; j < tmp3.length; j++){
                System.out.printf("%.2f, ",tmp3[i][j]);
            }
            System.out.println("]");
        }
        assertTrue(true);
    }
    
    @Test
    public void getKnowlageDiffusionForActor2(){
        KnowledgeDiffusionCalculator k = new KnowledgeDiffusionCalculator(graph);
        k.updateMaxPathLength(1);
        double[][] KnowlageTable = {{0.0, 0.0, 0.5, 1.25, 1.125, 0.0},
                                    {0.630859375, 0.0, 0.625, 0.671875, 0.49755859375, 0.5},
                                    {0.625, 0.0, 0.0, 0.625, 0.4375, 0.0},
                                    {1.25, 0.0, 0.5, 0.0, 1.125, 0.0},
                                    {0.5, 0.0, 0.0, 0.5, 0.0, 0.0},
                                    {0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};
        int i = 0;
        for(Actor a : imp.getActors())
        {
            System.out.print("[" + (i+1) + "|");
            for(Actor aa : imp.getActors()){
                assertEquals("Checking that actors " + a  + " and " + aa + " are correct",
                             KnowlageTable[a.getId()-1][aa.getId()-1], 
                             k.getKnowlageFromActors(a, aa),
                             0.00001);
                System.out.printf("%.2f, ", k.getKnowlageFromActors(a, aa));
            }
             System.out.println("]");
            i++;
        }
    }
}
