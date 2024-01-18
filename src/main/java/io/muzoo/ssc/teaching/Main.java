package io.muzoo.ssc.teaching;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public class Main {

    public static void main(String[] args) {
        CommandLineOptions options = new CommandLineOptions(args);

        // Validate required options
        if (!options.hasOption("f")) {
            System.out.println("Error: -f option is required.");
            options.printHelp();
            System.exit(1);
        }

        // Initialize and run FileVisitor
        MyFileVisitor fileVisitor = new MyFileVisitor();
        try {
            Files.walkFileTree(Paths.get(options.getOptionValue("f")), EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print total number of 'a' in HTML files
        //System.out.println("Total 'a' occurrences in HTML files: " + fileVisitor.getTotalAInHtmlFiles());

        // Print total number of all files
        if (options.hasOption("a")) {
            System.out.println("Total number of all files: " + fileVisitor.getTotalFiles());
        }

        // Print total number of directories
        if (options.hasOption("b")) {
            System.out.println("Total number of directories: " + fileVisitor.getTotalDirs());
        }

        // Print total number of unique file extensions
        if (options.hasOption("c")) {
            System.out.println("Total number of unique file extensions: " + fileVisitor.getTotalUniqueExtensions());
        }

        // Print list of all unique file extensions
        if (options.hasOption("d")) {
            System.out.println("List of all unique file extensions: " + fileVisitor.getListOfUniqueExtensions());
        }

        // Print total number of files for a specified extension
        if (options.hasOption("num-ext")) {
            String ext = options.getOptionValue("num-ext");
            System.out.println("Total number of files for extension " + ext + ": " + fileVisitor.getTotalFilesForExtension(ext));
        }

    }
}
