package no.iprog.ml.objectrecognition.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public interface FileService {
    public Collection<FileStatus> listFiles(String dirPath) throws IOException;
    public void write(String filePath, CharSequence data) throws IOException;
    public void write(String filePath, CharSequence data, boolean append) throws IOException;
    public void write(String filePath, InputStream data) throws IOException;
    public void write(String filePath, InputStream data, boolean append) throws IOException;
    public InputStream openInputStream(String filePath) throws IOException;
    public OutputStream openOutputStream(String filePath, boolean overwrite) throws IOException;
    
}
