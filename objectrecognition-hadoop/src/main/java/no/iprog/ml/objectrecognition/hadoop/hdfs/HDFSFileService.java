package no.iprog.ml.objectrecognition.hadoop.hdfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import no.iprog.ml.objectrecognition.api.AbstractFileService;
import no.iprog.ml.objectrecognition.api.FileStatus;
import no.iprog.ml.objectrecognition.hadoop.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public class HDFSFileService extends AbstractFileService {
    protected Configuration conf;
    protected FileSystem fileSystem;

    public void setConf(Configuration conf) {
        this.conf = conf;
    }
    
    public void init() throws IOException{
        fileSystem = FileSystem.get(conf);
    }

    public Collection<FileStatus> listFiles(String dirPath) throws IOException {
        return this.listFiles(new Path(dirPath));
    }
    
    public Collection<FileStatus> listFiles(Path dirPath) throws IOException {
        List<FileStatus> list = new ArrayList<FileStatus>();
        
        org.apache.hadoop.fs.FileStatus[] stats = fileSystem.listStatus(dirPath);
        for (int i = 0; i < stats.length; i++) {
            list.add(new HDFSFileStatus(stats[i]));
        }
        
        return list;
    }

    public void write(String filePath, InputStream data, boolean append) throws IOException {
        Path path = new Path(filePath);
        
        FSDataOutputStream out;
        if (append) out = fileSystem.append(path);
        else out = fileSystem.create(path, false);
        
        IOUtils.copy(data, out);
        
        out.close();
    }
    
    public InputStream openInputStream(String filePath) throws IOException {
        Path path = new Path(filePath);
        
        return fileSystem.open(path);
    }

    public OutputStream openOutputStream(String filePath, boolean overwrite) throws IOException {
        Path path = new Path(filePath);
        
        return fileSystem.create(path, overwrite);
    }
    
    public void destroy() throws IOException{
        fileSystem.close();
    }
}
