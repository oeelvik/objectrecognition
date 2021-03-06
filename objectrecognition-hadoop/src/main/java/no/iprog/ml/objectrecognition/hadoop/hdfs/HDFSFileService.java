package no.iprog.ml.objectrecognition.hadoop.hdfs;

import java.io.BufferedOutputStream;
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
        return this.listFiles(getPath(dirPath));
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
        Path path = getPath(filePath);
        
        BufferedOutputStream out;
        if (append) out = new BufferedOutputStream(fileSystem.append(path));
        else out = new BufferedOutputStream(fileSystem.create(path, false));
        
        IOUtils.copy(data, out);
        
        data.close();
        out.close();
    }

    @Override
    public void write(String filePath, byte[] bytes, boolean append) throws IOException {
        Path path = getPath(filePath);
        
        BufferedOutputStream out;
        if (append) out = new BufferedOutputStream(fileSystem.append(path));
        else out = new BufferedOutputStream(fileSystem.create(path, false));
        
        out.write(bytes);
        out.close();
    }
    
    public InputStream openInputStream(String filePath) throws IOException {
        Path path = getPath(filePath);
        
        return fileSystem.open(path);
    }

    public OutputStream openOutputStream(String filePath, boolean overwrite) throws IOException {
        Path path = getPath(filePath);
        
        return fileSystem.create(path, overwrite);
    }
    
    public void destroy() throws IOException{
        fileSystem.close();
    }
    
    private Path getPath(String path) {
        if(path.startsWith("/")) return new Path(path);
        return new Path(this.conf.getBasepath().concat(path));
    }
}
