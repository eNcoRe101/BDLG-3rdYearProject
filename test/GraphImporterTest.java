/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import comp30040.GraphImporter;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
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
public class GraphImporterTest {
    private GraphImporter imp;
    private final File cvsFile;
    private final Path pathToCvsFile;
    private final String relativePath = "./data/sample-2mode.csv";
    
    public GraphImporterTest() {
        this.pathToCvsFile = Paths.get(relativePath);
        this.cvsFile = new File(pathToCvsFile.toString());
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.imp = null;
    }
    
    @After
    public void tearDown() {
    
    }
    
    @Test
    public void testImportOfFileFromRelativePath(){
        try{
            this.imp = new GraphImporter(this.relativePath);
            assertNotNull("Check that the GraphImporter Exists from relative path name", this.imp);
        }
        catch(FileNotFoundException e)
        {
            fail(e.toString());
        }
    }
    
    @Test
    public void testImportOfFileFromFullPath(){
        try{
            this.imp = new GraphImporter(this.pathToCvsFile.toString());
            assertNotNull("Check that the GraphImporter Exists from full path name", this.imp);
        }
        catch(FileNotFoundException e)
        {
            fail(e.toString());
        }
    }
    
    @Test
    public void testImportOfFileFromFileObject(){
        this.imp = new GraphImporter(this.cvsFile);
        assertNotNull("Check that the GraphImporter Exists from File", this.imp);
    }
    
    @Test
    public void testNumberOfActorsIsCorrect(){
        this.imp = new GraphImporter(this.cvsFile);
        assertNotNull("Check that the GraphImporter Exists", this.imp);
        assertEquals("Checking number of Actors are correct", 
                      (int)6, imp.getNumberOfActors());
    }
    
    @Test
    public void testNumberOfEventsIsCorrect(){
        this.imp = new GraphImporter(this.cvsFile);
        assertNotNull("Check that the GraphImporter Exists", this.imp);
        assertEquals("Checking number of Events are correct", 
                      (int)4, imp.getNumberOfEvents());
    }
}
