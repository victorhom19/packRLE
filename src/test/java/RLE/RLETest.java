package RLE;

import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import static RLE.FileUtilities.assertFileContent;
import static RLE.FileUtilities.getContent;

class RLETest {


    @Test
    void packingTest_1() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/test/resources/PackingTesting/OutputPackingTest.txt",
                                       "src/test/resources/PackingTesting/InputPackingTest_1.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/PackingTesting/OutputPackingTest.txt",
                 "5a3b3e");

    }

    @Test
    void packingTest_2() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/test/resources/PackingTesting/OutputPackingTest.txt",
                                       "src/test/resources/PackingTesting/InputPackingTest_2.txt" };
        new PackRLE().main(args);
        assertFileContent("src/test/resources/PackingTesting/OutputPackingTest.txt",
                  "-1P4A-1C3K-4ING 4T-1E3S-4T."
                          + System.lineSeparator() +
                          "4T2H-1I3S-2 P4R-4OGRA5M-2 I3S-1 3U2S-10ING RLE AL3G-1O2R-4YTHM3.");

    }

    @Test
    void packingTest_3() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/test/resources/PackingTesting/OutputPackingTest.txt",
                                       "src/test/resources/PackingTesting/InputPackingTest_3.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/PackingTesting/OutputPackingTest.txt",
                 "8A12B-3DEF8G6H-4IJKL5M-1N5O4P-2.!3?4-");

    }

    @Test
    void packingTest_4() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/test/resources/PackingTesting/OutputPackingTest.txt",
                "src/test/resources/PackingTesting/InputPackingTest_4.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/PackingTesting/OutputPackingTest.txt",
                "0132/094/0-391/0104/0-297/");

    }

    @Test
    void packingTest_5() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/test/resources/PackingTesting/OutputPackingTest.txt",
                "src/test/resources/PackingTesting/InputPackingTest_5.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/PackingTesting/OutputPackingTest.txt",
                "5A2b0-543/5C065/-10DEF89-34545-0-8/049/");

    }

    @Test
    void unpackingTest_1() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                                       "src/test/resources/UnpackingTesting/InputUnpackingTest_1.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "Testing uuuuunnnpackiiiiiing    .!&");

    }

    @Test
    void unpackingTest_2() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "src/test/resources/UnpackingTesting/InputUnpackingTest_2.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "SSSSSStttttay hhhhhhhhhhOOO-meeee and wAAAAAAsH yyyyyyOUR hhhands!!!!!"
                        + System.lineSeparator() +
                        "CCCCCCoronAA virUUUUUUsss outsIIIIIIIIde!!");

    }

    @Test
    void unpackingTest_3() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "src/test/resources/UnpackingTesting/InputUnpackingTest_3.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "TTTTTTTTTTTTTTTTTTTThis iiiiiiiiis unnnnppppppppppackiiiiing RLEEEEEEEE alggggorythm...."
                        + System.lineSeparator() +
                        "TTTTTryinnnnggggggggg    to eeeeeenncode!!!!");

    }

    @Test
    void unpackingTest_4() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "src/test/resources/UnpackingTesting/InputUnpackingTest_4.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "22222222222224444444443914444444444297");

    }

    @Test
    void unpackingTest_5() throws IOException, CmdLineException {
        String[] args = {"-u", "-out", "src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "src/test/resources/UnpackingTesting/InputUnpackingTest_5.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/UnpackingTesting/OutputUnpackingTest.txt",
                "AAAAAbb543CCCCC555555DEF89-3454-----89999");

    }

    @Test
    void complexTest_1() throws IOException, CmdLineException {
        String[] packingArgs = {"-z", "-out", "src/test/resources/ComplexTesting/temp.txt",
                "src/test/resources/ComplexTesting/ComplexTestOriginal_1.txt"};
        new PackRLE().main(packingArgs);
        String[] unpackingArgs = {"-u", "-out", "src/test/resources/ComplexTesting/ComplexTestModified_1.txt",
                "src/test/resources/ComplexTesting/temp.txt"};
        new PackRLE().main(unpackingArgs);
        assertEquals(getContent("src/test/resources/ComplexTesting/ComplexTestOriginal_1.txt"),
                     getContent("src/test/resources/ComplexTesting/ComplexTestModified_1.txt"));
    }

    @Test
    void complexTest_2() throws IOException, CmdLineException {
        String[] packingArgs = {"-z", "-out", "src/test/resources/ComplexTesting/temp.txt",
                "src/test/resources/ComplexTesting/ComplexTestOriginal_2.txt"};
        new PackRLE().main(packingArgs);
        String[] unpackingArgs = {"-u", "-out", "src/test/resources/ComplexTesting/ComplexTestModified_2.txt",
                "src/test/resources/ComplexTesting/temp.txt"};
        new PackRLE().main(unpackingArgs);
        assertEquals(getContent("src/test/resources/ComplexTesting/ComplexTestOriginal_2.txt"),
                getContent("src/test/resources/ComplexTesting/ComplexTestModified_2.txt"));
    }

    @Test
    void slashTest_1() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/test/resources/SlashTesting/temp.txt",
                "src/test/resources/SlashTesting/SlashTest_1.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/SlashTesting/temp.txt",
                "3A-10bcde/f/gHI5/-1j3k");

    }

    @Test
    void slashTest_2() throws IOException, CmdLineException {
        String[] args = {"-z", "-out", "src/test/resources/SlashTesting/temp.txt",
                "src/test/resources/SlashTesting/SlashTest_2.txt"};
        new PackRLE().main(args);
        assertFileContent("src/test/resources/SlashTesting/temp.txt",
                "3S-6lash/ 4/-2te3S-2ti3N-1g3/3.-2?!");

    }

}