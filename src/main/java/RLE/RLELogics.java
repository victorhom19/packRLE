package RLE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import static java.lang.Math.abs;

class RLELogics {

    private enum EncodingMode {
        collectingMixedCount,
        collectingRepeatingSymbolsCount,
        encodingRun
    }

    @Option(name = "-z")
    private boolean packingFlag;

    @Option(name = "-u")
    private boolean unpackingFlag;

    @Option(name = "-out")
    private boolean outFlag;

    @Argument
    private List<String> arguments = new ArrayList<>();

    RLELogics(String[] args) throws IOException, CmdLineException {

        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);

        String inputFileName;
        String outputFileName;

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

        int repeatingCounter = 1;
        int mixedCounter = -1;

        Pattern numberPattern = Pattern.compile("\\d");
        Matcher numberMatcher;

        boolean codingNumbers = false;

        StringBuilder buffer = new StringBuilder(Character.toString(file.charAt(0)));

        for (int i = 1; i < file.length(); i++) {
            String nextSymbol = Character.toString(file.charAt(i));
            numberMatcher = numberPattern.matcher(Character.toString(buffer.charAt(0)));

            if (nextSymbol.equals(Character.toString(buffer.charAt(buffer.length() - 1)))) {

                repeatingCounter++;
                if (mixedCounter != -1) {
                    codingNumbers = numberMatcher.matches();

                    if (codingNumbers) {
                        newFile.append(0).append("-");
                        newFile.append(buffer.substring(0, buffer.length() - 1)).append("/");
                    } else {
                        newFile.append(mixedCounter + 1).append(buffer.substring(0, buffer.length() - 1));
                    }

                    repeatingCounter = 2;
                    mixedCounter = -1;
                    buffer = new StringBuilder().append(nextSymbol);
                }
            } else {

                mixedCounter--;
                if (repeatingCounter != 1) {
                    codingNumbers = numberMatcher.matches();

                    if (codingNumbers) {
                        newFile.append(0).append(repeatingCounter);
                        newFile.append(buffer.charAt(buffer.length() - 1)).append("/");
                    } else {
                        newFile.append(repeatingCounter).append(buffer.charAt(buffer.length() - 1));
                    }

                    repeatingCounter = 1;
                    mixedCounter = -1;
                    buffer = new StringBuilder();
                }

                buffer.append(nextSymbol);
            }

        }

        if (mixedCounter != -1) {
            if (codingNumbers) {
                newFile.append(0).append("-");
                newFile.append(buffer).append("/");
            } else {
                newFile.append(mixedCounter).append(buffer);
            }
        }
        if (repeatingCounter != 1) {
            if (codingNumbers) {
                newFile.append(0).append(repeatingCounter);
                newFile.append(buffer.charAt(buffer.length() - 1)).append("/");
            } else {
                newFile.append(repeatingCounter).append(buffer.charAt(buffer.length() - 1));
            }
        }
        return (newFile.toString());
    }


    private String unpackingPhase(String file) {
        StringBuilder newFile = new StringBuilder();

        Pattern numberPattern = Pattern.compile("\\d");
        Matcher numberMatcher;

        StringBuilder counter = new StringBuilder();
        StringBuilder line = new StringBuilder();
        EncodingMode mode = EncodingMode.encodingRun;


        for (int i = 0; i < file.length(); i++) {

            if (Character.toString(file.charAt(i)).equals("/")) {
                if (i + 1 >= file.length()) {
                    break;
                } else {
                    i ++;
                }
            }

            String nextSymbol = Character.toString(file.charAt(i));
            numberMatcher = numberPattern.matcher(nextSymbol);

            if (nextSymbol.equals("-")) {

                if (mode == EncodingMode.encodingRun) {
                    mode = EncodingMode.collectingMixedCount;
                    counter.append(nextSymbol);

                } else {
                    line = new StringBuilder(nextSymbol);
                    mode = EncodingMode.encodingRun;

                }

            } else if (nextSymbol.equals("0")) {

                if (mode == EncodingMode.encodingRun) {
                    if (file.charAt(i + 1) == '-') {
                        int fragmentLength = -2;
                        while (!(nextSymbol.equals("/"))) {
                            i++;
                            nextSymbol = Character.toString(file.charAt(i));
                            fragmentLength ++;
                        }
                        i -= fragmentLength;
                        counter.append(-1 * (fragmentLength));
                        line.append(file.charAt(i));
                    } else {
                        int c = 0;
                        while (!(nextSymbol.equals("/"))) {
                            i++;
                            nextSymbol = Character.toString(file.charAt(i));
                            c ++;
                        }
                        line.append(file.charAt(i - 1));
                        counter.append(Integer.parseInt(file.substring(i - c + 1, i - 1)));
                    }
                } else {
                    counter.append(nextSymbol);
                }

            } else if (numberMatcher.matches()) {
                if (mode == EncodingMode.encodingRun) {
                    mode = EncodingMode.collectingRepeatingSymbolsCount;
                }
                counter.append(nextSymbol);

            } else {
                mode = EncodingMode.encodingRun;
                line.append(nextSymbol);
            }


            if (mode == EncodingMode.encodingRun) {

                if (Integer.parseInt(counter.toString()) > 0) {
                    newFile.append(line.toString().repeat(Integer.parseInt(counter.toString())));
                } else {
                    for (int j = 0; j > Integer.parseInt(counter.toString()) + 1; j--) {
                        line.append(file.charAt(i - j + 1));
                    }
                    newFile.append(line.toString());
                    i += abs(Integer.parseInt(counter.toString())) - 1;
                }
                line = new StringBuilder();
                counter = new StringBuilder();
            }
        }

        return (newFile.toString());
    }


}