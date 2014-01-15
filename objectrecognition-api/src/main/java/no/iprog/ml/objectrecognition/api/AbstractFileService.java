package no.iprog.ml.objectrecognition.api;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 * @author Øystein Schrøder Elvik
 */
public abstract class AbstractFileService implements FileService {
    public void write(String filePath, CharSequence data, Charset encoding) throws IOException {
        this.write(filePath, data, encoding, false);
    }
    public void write(String filePath, CharSequence data, String encoding) throws IOException {
        this.write(filePath, data, Charset.forName(encoding), false);
    }
    public void write(String filePath, CharSequence data, String encoding, boolean append) throws IOException {
        this.write(filePath, data, Charset.forName(encoding), append);
    }
}
