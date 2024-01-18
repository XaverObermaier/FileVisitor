package io.muzoo.ssc.teaching;
import org.apache.commons.cli.*;

public class CommandLineOptions {
    private CommandLine cmd;

    public CommandLineOptions(String[] args) {
        Options options = createOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            printHelp(options);
            System.exit(1);
        }
    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("a").longOpt("total-num-files").desc("Print the total number of files").build());
        options.addOption(Option.builder("b").longOpt("total-num-dirs").desc("Print the total number of directories").build());
        options.addOption(Option.builder("c").longOpt("total-unique-exts").desc("Print the total number of unique file extensions").build());
        options.addOption(Option.builder("d").longOpt("list-exts").desc("Print the list of all unique file extensions").build());
        options.addOption(Option.builder().longOpt("num-ext").hasArg().desc("Print the total number of files for the specified extension EXT (one extension at a time)").build());
        options.addOption(Option.builder("f").longOpt("path-to-folder").hasArg().desc("Specify the path to the documentation folder. This is a required argument.").required().build());
        return options;
    }

    public boolean hasOption(String option) {
        return cmd.hasOption(option);
    }

    public String getOptionValue(String option) {
        return cmd.getOptionValue(option);
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar YourJarName.jar [options...]", options);
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar YourJarName.jar [options...]", createOptions());
    }
}
