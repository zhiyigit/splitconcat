/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package splitconcat;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.exit;

public class App {
    public static void main(String[] args) {
        // Define command-line options
        Options options = new Options();
        options.addOption("a", "action", true, "Specify the action: split or concat");
        options.addOption("f", "filename", true, "Specify the filename");
        options.addOption("c", "chunksize", false, "Specify the chunk size");

        // Create the command-line parser
        CommandLineParser parser = new DefaultParser();

        String filename = null;
        int chunkSize = 106502400;
        Action splitOrConcat = Action.NONE;
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("action")) {
                String actionStr = cmd.getOptionValue("action");
                splitOrConcat = Action.valueOf(actionStr);
                System.out.println("Action: " + splitOrConcat);
            }

            // Check if the filename option is present
            if (cmd.hasOption("filename")) {
                filename = cmd.getOptionValue("filename");
                System.out.println("Filename: " + filename);
            }

            // Check if the chunksize option is present
            if (cmd.hasOption("chunksize")) {
                String chunkSizeStr = cmd.getOptionValue("chunksize");
                chunkSize = Integer.parseInt(chunkSizeStr);
                System.out.println("Chunk Size: " + chunkSize);
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command-line arguments: " + e.getMessage());
            exit(1);
        }

        try {
            switch (splitOrConcat) {
                case SPLIT:
                    FileSplitConcat.splitFile(filename, chunkSize);
                    break;
                case CONCAT:
                    FileSplitConcat.concatenateFiles(filename);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error split or concat: " + e.getMessage());
            exit(1);
        }
    }
}
