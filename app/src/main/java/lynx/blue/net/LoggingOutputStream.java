package lynx.blue.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class LoggingOutputStream extends BufferedReader {
    public LoggingOutputStream(Reader in, int sz) {
        super(in, sz);
    }


    public void write(byte[] bytes) {
    }

    public void flush() {
    }
}
