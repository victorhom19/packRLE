package RLE;

import org.junit.jupiter.api.Test;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class RLE_Test {

    private void assertFileContent(String name, String expectedContent) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(name));
        StringBuilder content = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            content.append(line).append(System.lineSeparator());
            line = reader.readLine();
        }
        assertEquals(expectedContent, content.toString());
    }



    @Test
    void packingTest_1() throws IOException {
        String[] args = {"-z", "-out", "src/test/java/RLE/OutputPackingTest.txt",
                                       "src/test/java/RLE/InputPackingTest_1.txt"};
        new pack_rle().main(args);
        assertFileContent("src/test/java/RLE/OutputPackingTest.txt",
                 "5a3b3e"
                         + System.lineSeparator());

    }

    @Test
    void packingTest_2() throws IOException {
        String[] args = {"-z", "-out", "src/test/java/RLE/OutputPackingTest.txt",
                                       "src/test/java/RLE/InputPackingTest_2.txt" };
        new pack_rle().main(args);
        assertFileContent("src/test/java/RLE/OutputPackingTest.txt",
                  "-1P4A-1C3K-4ING 4T-1E3S-4T."
                          + System.lineSeparator() +
                          "4T2H-1I3S-2 P4R-4OGRA5M-2 I3S-1 3U2S-10ING RLE AL3G-1O2R-4YTHM3."
                          + System.lineSeparator());

    }

    @Test
    void packingTest_3() throws IOException {
        String[] args = {"-z", "-out", "src/test/java/RLE/OutputPackingTest.txt",
                                       "src/test/java/RLE/InputPackingTest_3.txt"};
        new pack_rle().main(args);
        assertFileContent("src/test/java/RLE/OutputPackingTest.txt",
                 "8A12B-3DEF8G6H-4IJKL5M-1N5O4P-2.!3?4-"
                         + System.lineSeparator());

    }

    @Test
    void unpackingTest_1() throws IOException {
        String[] args = {"-u", "-out", "src/test/java/RLE/OutputUnpackingTest.txt",
                                       "src/test/java/RLE/InputUnpackingTest_1.txt"};
        new pack_rle().main(args);
        assertFileContent("src/test/java/RLE/OutputUnpackingTest.txt",
                "Testing uuuuunnnpackiiiiiing    .!&"
                        + System.lineSeparator());

    }

    @Test
    void unpackingTest_2() throws IOException {
        String[] args = {"-u", "-out", "src/test/java/RLE/OutputUnpackingTest.txt",
                "src/test/java/RLE/InputUnpackingTest_2.txt"};
        new pack_rle().main(args);
        assertFileContent("src/test/java/RLE/OutputUnpackingTest.txt",
                "SSSSSStttttay hhhhhhhhhhOOO-meeee and wAAAAAAsH yyyyyyOUR hhhands!!!!!"
                        + System.lineSeparator() +
                        "CCCCCCoronAA virUUUUUUsss outsIIIIIIIIde!!"
                        + System.lineSeparator());

    }

    @Test
    void unpackingTest_3() throws IOException {
        String[] args = {"-u", "-out", "src/test/java/RLE/OutputUnpackingTest.txt",
                "src/test/java/RLE/InputUnpackingTest_3.txt"};
        new pack_rle().main(args);
        assertFileContent("src/test/java/RLE/OutputUnpackingTest.txt",
                "TTTTTTTTTTTTTTTTTTTThis iiiiiiiiis unnnnppppppppppackiiiiing RLEEEEEEEE alggggorythm...."
                        + System.lineSeparator() +
                        "TTTTTryinnnnggggggggg    to eeeeeenncode!!!!"
                        + System.lineSeparator());

    }

    @Test
    void breakingTest_1() throws IOException {
        String[] args = {"Illegal", "Arguments"};
        assertThrows(IllegalArgumentException.class, () -> new pack_rle().main(args));
    }

    @Test
    void breakingTest_2() throws IOException {
        String[] args = {"-u", "unknownFile.txt"};
        assertThrows(FileNotFoundException.class, () -> new pack_rle().main(args));
    }
}