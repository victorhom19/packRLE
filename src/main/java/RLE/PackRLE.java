package RLE;

import org.kohsuke.args4j.CmdLineException;
import java.io.*;

public class PackRLE {
    public static void main(String[] args) throws IOException, CmdLineException {
        new RLELogics(args);
    }
}