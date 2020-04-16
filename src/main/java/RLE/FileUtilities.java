package RLE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class FileUtilities {
    static String getContent(String inputFileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        StringBuilder text = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            if (text.length() > 0) {
                text.append(System.lineSeparator());
            }
            text.append(line);
            line = reader.readLine();
        }
        reader.close();
        return text.toString();
    }

    static void assertFileContent(String fileName, String expectedContent) throws IOException {
        assertEquals(expectedContent, getContent(fileName));
    }

}
