package no.iprog.ml.objectrecognition.hadoop.hipi;

import hipi.image.FloatImage;
import hipi.image.ImageHeader;
import hipi.imagebundle.HipiImageBundle;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import no.iprog.ml.objectrecognition.api.FileStatus;
import no.iprog.ml.objectrecognition.api.FileStatusImpl;
import no.iprog.ml.objectrecognition.hadoop.hdfs.HDFSFileService;
import org.apache.hadoop.fs.FSInputStream;
import org.apache.hadoop.fs.Path;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public class HipiFileService extends HDFSFileService {
    
    @Override
    public Collection<FileStatus> listFiles(String dirPath) throws IOException {
        if(!dirPath.endsWith("/")) dirPath = dirPath.concat("/");
        
        List<FileStatus> list = new ArrayList<FileStatus>();
        
        for (FileStatus status : super.listFiles(dirPath)) {
            if(status.isDir() || !status.getName().endsWith("hib")) continue;
            
            HipiImageBundle hib = new HipiImageBundle(new Path(dirPath.concat(status.getName())), this.conf);
            while (hib.hasNext()) {
                ImageHeader imageHeader = hib.next();
                list.add(new FileStatusImpl(false, imageHeader.toString() + " : " + hib.getCurrentImage().hex()));
            }
        }
        
        return list;
    }
    
    public InputStream openInputStream(String filePath) throws IOException {
        Path path = new Path(filePath);
        
        Path dirPath = path.getParent();
        
        FSInputStream inputStream;
        
        hibloop:
        for (FileStatus status : super.listFiles(dirPath)) {
            if(status.isDir() || !status.getName().endsWith("hib")) continue;
            
            HipiImageBundle hib = new HipiImageBundle(new Path(dirPath.toString().concat(status.getName())), this.conf);
            while (hib.hasNext()) {
                ImageHeader imageHeader = hib.next();
                FloatImage image = hib.getCurrentImage();
                //if(path.getName() == image.hex()) inputStream = image.
            }
        }
        
        return null;
    }

    public OutputStream openOutputStream(String filePath, boolean overwrite) throws IOException {
        Path path = new Path(filePath);
        
        return fileSystem.create(path, overwrite);
    }
}
