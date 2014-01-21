package no.iprog.ml.objectrecognition.api;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public abstract class AbstractFileService implements FileService {
    public void write(String filePath, CharSequence data) throws IOException {
        this.write(filePath, data, false);
    }

    public void write(String filePath, CharSequence data, boolean append) throws IOException {
        this.write(filePath, IOUtils.toInputStream(data), append);
    }

    public void write(String filePath, InputStream data) throws IOException {
        this.write(filePath, filePath, false);
    }

    public void write(String filePath, byte[] bytes) throws IOException {
        this.write(filePath, bytes, false);
    }
    
    public void write(String filePath, byte[] bytes, boolean apply) throws IOException {
        this.write(filePath, new String(bytes), apply);
    }
}
