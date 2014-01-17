package no.iprog.ml.objectrecognition.api;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public class FileStatusImpl implements FileStatus {
    private boolean dir;
    private String name;

    public boolean isDir() {
        return dir;
    }

    public String getName() {
        return name;
    }

    public FileStatusImpl(boolean isDir, String name) {
        this.dir = isDir;
        this.name = name;
    }
    
}
