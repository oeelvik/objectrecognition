package no.iprog.ml.objectrecognition.hadoop.hdfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import no.iprog.ml.objectrecognition.api.AbstractFileService;
import no.iprog.ml.objectrecognition.api.FileStatus;
import no.iprog.ml.objectrecognition.hadoop.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public class HDFSFileService extends AbstractFileService {
    private Configuration conf;
    private FileSystem fileSystem;

    public void setConf(Configuration conf) {
        this.conf = conf;
    }
    
    public void init() throws IOException{
        fileSystem = FileSystem.get(conf);
    }

    public Collection<FileStatus> listFiles(String dirPath) throws IOException {
        List<FileStatus> list = new ArrayList<FileStatus>();
        
        org.apache.hadoop.fs.FileStatus[] stats = fileSystem.listStatus(new Path(dirPath));
        for (int i = 0; i < stats.length; i++) {
            list.add(new HDFSFileStatus(stats[i]));
        }
        
        return list;
    }
    
    public void write(String filePath, CharSequence data, Charset encoding, boolean append) throws IOException {
        Path path = new Path(filePath);
        
        FSDataOutputStream out;
        if (append) out = fileSystem.append(path);
        else out = fileSystem.create(path, false);
        
        out.writeChars(data.toString());
        
        out.close();
    }

    public InputStream openInputStream(String path) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public OutputStream openOutputStream(String path) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void destroy() throws IOException{
        fileSystem.close();
    }
}
