package RLE;

import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import static RLE.FileUtilities.assertFileContent;
import static RLE.FileUtilities.getContent;

class RLE_Test {




    @Test
    void packingTest_1() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/main/resources/PackingTesting/OutputPackingTest.txt",
                                       "src/main/resources/PackingTesting/InputPackingTest_1.txt"};
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/PackingTesting/OutputPackingTest.txt",
                 "5a3b3e");

    }

    @Test
    void packingTest_2() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/main/resources/PackingTesting/OutputPackingTest.txt",
                                       "src/main/resources/PackingTesting/InputPackingTest_2.txt" };
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/PackingTesting/OutputPackingTest.txt",
                  "-1P4A-1C3K-4ING 4T-1E3S-4T."
                          + System.lineSeparator() +
                          "4T2H-1I3S-2 P4R-4OGRA5M-2 I3S-1 3U2S-10ING RLE AL3G-1O2R-4YTHM3.");

    }

    @Test
    void packingTest_3() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/main/resources/PackingTesting/OutputPackingTest.txt",
                                       "src/main/resources/PackingTesting/InputPackingTest_3.txt"};
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/PackingTesting/OutputPackingTest.txt",
                 "8A12B-3DEF8G6H-4IJKL5M-1N5O4P-2.!3?4-");

    }

    @Test
    void packingTest_4() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/main/resources/PackingTesting/OutputPackingTest.txt",
                "src/main/resources/PackingTesting/InputPackingTest_4.txt"};
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/PackingTesting/OutputPackingTest.txt",
                "0132/094/0-391/0104/0-297/");

    }

    @Test
    void packingTest_5() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/main/resources/PackingTesting/OutputPackingTest.txt",
                "src/main/resources/PackingTesting/InputPackingTest_5.txt"};
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/PackingTesting/OutputPackingTest.txt",
                "5A2b0-543/5C065/-10DEF89-34545-0-8/049/");

    }

    @Test
    void unpackingTest_1() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                                       "src/main/resources/UnpackingTesting/InputUnpackingTest_1.txt"};
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "Testing uuuuunnnpackiiiiiing    .!&");

    }

    @Test
    void unpackingTest_2() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "src/main/resources/UnpackingTesting/InputUnpackingTest_2.txt"};
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "SSSSSStttttay hhhhhhhhhhOOO-meeee and wAAAAAAsH yyyyyyOUR hhhands!!!!!"
                        + System.lineSeparator() +
                        "CCCCCCoronAA virUUUUUUsss outsIIIIIIIIde!!");

    }

    @Test
    void unpackingTest_3() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "src/main/resources/UnpackingTesting/InputUnpackingTest_3.txt"};
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "TTTTTTTTTTTTTTTTTTTThis iiiiiiiiis unnnnppppppppppackiiiiing RLEEEEEEEE alggggorythm...."
                        + System.lineSeparator() +
                        "TTTTTryinnnnggggggggg    to eeeeeenncode!!!!");

    }

    @Test
    void unpackingTest_4() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "src/main/resources/UnpackingTesting/InputUnpackingTest_4.txt"};
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "22222222222224444444443914444444444297");

    }

    @Test
    void unpackingTest_5() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "src/main/resources/UnpackingTesting/InputUnpackingTest_5.txt"};
        new Pack_RLE().main(args);
        assertFileContent("src/main/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "AAAAAbb543CCCCC555555DEF89-3454-----89999");

    }

    @Test
    void complexTest_1() throws IOException, CmdLineException {
        String[] packingArgs = {"-z", "-out", "src/main/resources/ComplexTesting/temp.txt",
                "src/main/resources/ComplexTesting/ComplexTestOriginal_1.txt"};
        new Pack_RLE().main(packingArgs);
        String[] unpackingArgs = {"-u", "-out", "src/main/resources/ComplexTesting/ComplexTestModified_1.txt",
                "src/main/resources/ComplexTesting/temp.txt"};
        new Pack_RLE().main(unpackingArgs);
        assertEquals(getContent("src/main/resources/ComplexTesting/ComplexTestOriginal_1.txt"),
                     getContent("src/main/resources/ComplexTesting/ComplexTestModified_1.txt"));
    }

    @Test
    void complexTest_2() throws IOException, CmdLineException {
        String[] packingArgs = {"-z", "-out", "src/main/resources/ComplexTesting/temp.txt",
                "src/main/resources/ComplexTesting/ComplexTestOriginal_2.txt"};
        new Pack_RLE().main(packingArgs);
        String[] unpackingArgs = {"-u", "-out", "src/main/resources/ComplexTesting/ComplexTestModified_2.txt",
                "src/main/resources/ComplexTesting/temp.txt"};
        new Pack_RLE().main(unpackingArgs);
        assertEquals(getContent("src/main/resources/ComplexTesting/ComplexTestOriginal_2.txt"),
                getContent("src/main/resources/ComplexTesting/ComplexTestModified_2.txt"));
    }


    @Test
    void breakingTest_1() {
        String[] args = {"Illegal", "Arguments"};
        assertThrows(IllegalArgumentException.class, () -> new Pack_RLE().main(args));
    }

    @Test
    void breakingTest_2() {
        String[] args = {"-u", "unknownFile.txt"};
        assertThrows(FileNotFoundException.class, () -> new Pack_RLE().main(args));
    }

}