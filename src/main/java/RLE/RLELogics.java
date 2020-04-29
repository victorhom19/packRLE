package RLE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import static RLE.StringUtilities.isNumber;
import static java.lang.Math.abs;

class RLELogics {

    private enum CodingMode {
        start,
        codingM,
        codingR
    }

    private enum EncodingMode {
        start,
        encodingM,
        encodingR,
        encodingMN,
        encodingRN
    }


    @Option(name = "-z")
    private boolean packingFlag;

    @Option(name = "-u")
    private boolean unpackingFlag;

    @Option(name = "-out")
    private boolean outFlag;

    @Argument
    private List<String> arguments = new ArrayList<>();

    CmdLineParser parser = new CmdLineParser(this);

    String inputFileName;
    String outputFileName;

    RLELogics(String[] args) throws IOException, CmdLineException {

        parser.parseArgument(args);

        if (outFlag) {
            outputFileName = arguments.get(0);
            inputFileName = arguments.get(1);
        } else {
            outputFileName = "output.txt";
            inputFileName = arguments.get(0);
        }

        if (!packingFlag && !unpackingFlag) {
            System.err.println("Invalid flags. You should choose packing flag -z or unpacking flag -u.");
            System.exit(1);
        }

        if (new File(inputFileName).length() < 2) {
            System.err.println("File length is too small. Unable to code/decode");
            System.exit(1);
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
            if (packingFlag) {
                writer.write(packingPhase(FileUtilities.getContent(inputFileName)));
            } else if (unpackingFlag) {
                writer.write(unpackingPhase(FileUtilities.getContent(inputFileName)));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Invalid file name. File not found.");
            System.exit(1);
        }
    }


    private String packingPhase(String file) {

        StringBuilder newFile = new StringBuilder();
        CodingMode mode = CodingMode.start;
        StringBuilder buffer = new StringBuilder(Character.toString(file.charAt(0)));

        int bLastIndex;

        for (int i = 1; i < file.length(); i++) {
            buffer.append(file.charAt(i));
            bLastIndex = buffer.length() - 1;

            if (buffer.charAt(bLastIndex) == (buffer.charAt(bLastIndex - 1))) {
                if (mode == CodingMode.codingM) {
                    newFile.append(codeSymbols(buffer.substring(0, bLastIndex - 1), -(bLastIndex - 1)));
                    buffer.delete(0, bLastIndex - 1);
                }
                mode = CodingMode.codingR;

            } else {
                if (mode == CodingMode.codingR) {
                    newFile.append(codeSymbols(buffer.substring(0, 1), bLastIndex));
                    buffer.delete(0, bLastIndex);
                    mode = CodingMode.start;
                } else {
                    mode = CodingMode.codingM;
                }
            }
        }
        bLastIndex = buffer.length() - 1;
        if (mode == CodingMode.codingM) {
            newFile.append(codeSymbols(buffer.substring(0, bLastIndex + 1), -(bLastIndex + 1)));
        } else {
            newFile.append(codeSymbols(buffer.substring(0, 1), bLastIndex + 1));
        }
        return (newFile.toString());
    }

    String codeSymbols(String receivedBuffer, int counter) {
        StringBuilder result = new StringBuilder();
        boolean codingNumbers = isNumber(Character.toString(receivedBuffer.charAt(0)));
        if (counter < 0) {
            if (codingNumbers) {
                result.append(0).append("-");
                result.append(receivedBuffer).append("/");
            } else {
                result.append(counter).append(receivedBuffer);
            }
        } else {
            if (codingNumbers) {
                result.append(0).append(counter);
                result.append(receivedBuffer).append("/");
            } else {
                result.append(counter).append(receivedBuffer);
            }
        }
        return (result.toString());
    }

    private String unpackingPhase(String file) {
        StringBuilder newFile = new StringBuilder();
        EncodingMode mode = EncodingMode.start;
        StringBuilder buffer = new StringBuilder();
        int counter = 0;

        for (int i = 0; i < file.length(); i++) {
            switch (mode) {
                case start:
                    buffer = new StringBuilder(Character.toString(file.charAt(i)));
                    if (buffer.charAt(buffer.length() - 1) == '0') {
                        i++;
                        buffer.append(file.charAt(i));
                        if (buffer.charAt(buffer.length() - 1) == '-') {
                            mode = EncodingMode.encodingMN;
                            i++;
                            buffer = new StringBuilder(Character.toString(file.charAt(i)));
                        } else {
                            mode = EncodingMode.encodingRN;
                            buffer = new StringBuilder(Character.toString(file.charAt(i)));
                        }
                    } else if (buffer.charAt(buffer.length() - 1) == '-') {
                        mode = EncodingMode.encodingM;
                        i++;
                        buffer = new StringBuilder(Character.toString(file.charAt(i)));
                    } else if (isNumber(buffer.charAt(buffer.length() - 1))) {
                        mode = EncodingMode.encodingR;
                    }
                    counter = 0;
                    break;
                case encodingMN:
                    while (file.charAt(i) != '/') {
                        buffer.append(file.charAt(i));
                        i++;
                    }
                    counter = 1;
                    mode = EncodingMode.start;
                    break;
                case encodingRN:
                    while (file.charAt(i) != '/') {
                        buffer.append(file.charAt(i));
                        i++;
                    }
                    counter = Integer.parseInt(buffer.substring(0, buffer.length() - 1));
                    buffer.delete(0, buffer.length() - 1);
                    mode = EncodingMode.start;
                    break;
                case encodingM:
                    while (isNumber(file.charAt(i))) {
                        buffer.append(file.charAt(i));
                        i++;
                    }
                    counter = Integer.parseInt(buffer.toString());
                    buffer = new StringBuilder(Character.toString(file.charAt(i)));
                    for (int j = 1; j < counter; j++) {
                        buffer.append(file.charAt(i + j));
                    }
                    i += counter - 1;
                    counter = 1;
                    mode = EncodingMode.start;
                    break;
                case encodingR:
                    while (isNumber(file.charAt(i))) {
                        buffer.append(file.charAt(i));
                        i++;
                    }
                    counter = Integer.parseInt(buffer.toString());
                    buffer = new StringBuilder(Character.toString(file.charAt(i)));
                    mode = EncodingMode.start;
                    break;
            }
            newFile.append(buffer.toString().repeat(counter));
        }
        return (newFile.toString());
    }


}