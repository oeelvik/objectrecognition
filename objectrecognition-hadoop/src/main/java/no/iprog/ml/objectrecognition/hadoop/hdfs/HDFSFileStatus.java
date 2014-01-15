package no.iprog.ml.objectrecognition.hadoop.hdfs;

import no.iprog.ml.objectrecognition.api.FileStatus;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public class HDFSFileStatus implements FileStatus {
    
    private org.apache.hadoop.fs.FileStatus status;
    
    public HDFSFileStatus(org.apache.hadoop.fs.FileStatus status) {
        this.status = status;
    }

    public boolean isDir() {
        return status.isDir();
    }

    public String getName() {
        return status.getPath().getName();
    }
    
}
