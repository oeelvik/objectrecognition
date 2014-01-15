package no.iprog.ml.objectrecognition.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public interface FileService {
    public Collection<FileStatus> listFiles(String dirPath) throws IOException;
    public void write(String filePath, CharSequence data, Charset encoding) throws IOException;
    public void write(String filePath, CharSequence data, Charset encoding, boolean append) throws IOException;
    public void write(String filePath, CharSequence data, String encoding) throws IOException;
    public void write(String filePath, CharSequence data, String encoding, boolean append) throws IOException;
    public InputStream openInputStream(String path) throws IOException;
    public OutputStream openOutputStream(String path) throws IOException;
    
}
