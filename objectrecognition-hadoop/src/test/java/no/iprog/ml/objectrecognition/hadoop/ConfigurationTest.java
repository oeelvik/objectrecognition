package no.iprog.ml.objectrecognition.hadoop;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import junit.framework.TestCase;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public class ConfigurationTest extends TestCase {
    
    public ConfigurationTest(String testName) {
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
     * Test of setFsDefaultName method, of class Configuration.
     */
    public void testConfig() {
        Map<String, String> expected = new HashMap<String, String>();
        Configuration instance = new Configuration();
        
        expected.put("fs.default.name","hdfs://localhost:9000");
        instance.setFsDefaultName("hdfs://localhost:9000");
        
        for (Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), instance.get(entry.getKey()));
        }
    }
}
