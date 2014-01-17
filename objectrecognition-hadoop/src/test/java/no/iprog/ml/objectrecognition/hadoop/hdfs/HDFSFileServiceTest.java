package no.iprog.ml.objectrecognition.hadoop.hdfs;

import java.io.BufferedReader;
import junit.framework.TestCase;
import no.iprog.ml.objectrecognition.hadoop.Configuration;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public class HDFSFileServiceTest extends TestCase {
    
    public HDFSFileServiceTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of init method, of class HDFSFileService.
     */
    public void testInit() throws Exception {
        HDFSFileService instance = new HDFSFileService();
        
        Configuration conf = new Configuration();
        conf.setFsDefaultName("hdfs://localhost:9000");
        instance.setConf(conf);
        
        //instance.init();
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class HDFSFileService.
     */
    public void testWrite() throws Exception {
        HDFSFileService instance = new HDFSFileService();
        
        Configuration conf = new Configuration();
        conf.setFsDefaultName("hdfs://192.168.33.10:9000");
        instance.setConf(conf);
        
        instance.init();
        
        
        instance.write("/objectrecognition/images/test.jpg", getClass().getResourceAsStream("/0.jpeg"));
        
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
