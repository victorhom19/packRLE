package RLE;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class pack_rle {

    public static void main(String[] args) throws IOException {
        new RLELogics(args);

    }

}


class RLELogics {

    private String inputFileName;
    private String outputFileName;

    enum EncodingMode {
        collectingMixedCount,
        collectingRepeatingCount,
        encodingRun
    }


    RLELogics(String[] args) throws IOException {

        boolean outFlag = false;
        boolean packingFlag = false;
        boolean unpackingFlag = false;

        for (String argument : args) {

            if (argument.equals("-u")) {
                unpackingFlag = true;
            } else if (argument.equals("-z")) {
                packingFlag = true;
            } else if (argument.equals("-out")) {
                outFlag = true;
            } else if (outFlag) {
                outputFileName = argument;
                outFlag = false;
            } else {
                inputFileName = argument;
            }

        }

        StringBuilder newFile = new StringBuilder();

        if (inputFileName != null) {
            BufferedReader reader = new BufferedReader(new FileReader(inputFileName));

            if (packingFlag) {

                int repeatingCounter = 1;
                int mixedCounter = -1;
                StringBuilder buffer = new StringBuilder(Character.toString(reader.read()));
                int newChar;
                while ((newChar = reader.read()) != -1) {
                    if (newChar == buffer.charAt(buffer.length() - 1)) {
                        repeatingCounter++;
                        if (mixedCounter != -1) {
                            newFile.append(mixedCounter + 1).append(buffer.substring(0, buffer.length() - 1));
                            repeatingCounter = 2;
                            mixedCounter = -1;
                            buffer = new StringBuilder().append(Character.toString(newChar));
                        }
                    } else {
                        mixedCounter--;
                        if (repeatingCounter != 1) {
                            newFile.append(repeatingCounter).append(buffer.charAt(buffer.length() - 1));
                            repeatingCounter = 1;
                            mixedCounter = -1;
                            buffer = new StringBuilder();
                        }
                        buffer.append(Character.toString(newChar));

                    }
                }
                if (mixedCounter != -1) {
                    newFile.append(mixedCounter).append(buffer.substring(0, buffer.length()));
                }
                if (repeatingCounter != 1) {
                    newFile.append(repeatingCounter).append(buffer.charAt(buffer.length() - 1));
                }


            } else if (unpackingFlag) {
                Pattern numberPattern = Pattern.compile("\\d");
                Matcher numberMatcher;
                StringBuilder counter = new StringBuilder();
                StringBuilder line = new StringBuilder();
                EncodingMode mode = EncodingMode.encodingRun;

                int chr;
                String newChar;

                while ((chr = reader.read()) != -1) {

                    newChar = Character.toString(chr);
                    numberMatcher = numberPattern.matcher(newChar);

                    if (newChar.equals("-")) {

                        if (mode == EncodingMode.encodingRun) {
                            mode = EncodingMode.collectingMixedCount;
                            counter.append(newChar);

                        } else {
                            line = new StringBuilder(newChar);
                            mode = EncodingMode.encodingRun;

                        }

                    } else if (numberMatcher.matches()) {
                        if (mode == EncodingMode.encodingRun) {
                            mode = EncodingMode.collectingRepeatingCount;
                        }
                        counter.append(newChar);

                    } else {
                        mode = EncodingMode.encodingRun;
                        line.append(newChar);
                    }

                    if (mode == EncodingMode.encodingRun) {

                        if (Integer.parseInt(counter.toString()) > 0) {
                            newFile.append(line.toString().repeat(abs(Integer.parseInt(counter.toString()))));
                        } else {
                            for (int i = 0; i > Integer.parseInt(counter.toString()) + 1; i--) {
                                line.append(Character.toString(reader.read()));
                            }
                            newFile.append(line.toString());
                        }
                        line = new StringBuilder();
                        counter = new StringBuilder();
                    }
                }
            }
        }

        if (outputFileName != null) {
            FileWriter writer = new FileWriter(outputFileName);
            writer.write(newFile.toString());
            writer.close();
        } else {
            FileWriter writer = new FileWriter("output.txt");
            writer.write(newFile.toString());
            writer.close();
        }

    }

}