package no.iprog.ml.objectrecognition.hadoop;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public class Configuration extends org.apache.hadoop.conf.Configuration {
    private String basepath = "/";

    public String getBasepath() {
        return basepath;
    }

    public void setBasepath(String basepath) {
        if(!basepath.startsWith("/")) basepath = "/".concat(basepath);
        if(!basepath.endsWith("/")) basepath = basepath.concat("/");
        this.basepath = basepath;
    }
    
    public void setFsDefaultName(String name){
        super.set("fs.default.name", name);
    }
    
    public Configuration (){
        super();
    }
}
