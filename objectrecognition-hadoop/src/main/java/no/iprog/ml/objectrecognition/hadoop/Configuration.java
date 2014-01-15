package no.iprog.ml.objectrecognition.hadoop;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public class Configuration extends org.apache.hadoop.conf.Configuration {
    public void setFsDefaultName(String name){
        super.set("fs.default.name", name);
    }
    
    public Configuration (){
        super();
    }
}
