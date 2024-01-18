package io.muzoo.ssc.teaching;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class MyFileVisitor implements FileVisitor<Path> {
    private int totalAInHtmlFiles = 0;
    private int totalFiles = 0;
    private int totalDirs = 0;
    private Set<String> uniqueExtensions = new HashSet<>();
    private int totalFilesForExtensions = 0;
    private Map<String, Integer> totalFilesForExtensionsByExtension = new HashMap<>();


    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        totalDirs++; //Count every Directory
        return FileVisitResult.CONTINUE;
    }


    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        totalFiles++; //Count every File
        if (file.toString().toLowerCase().endsWith(".html")) {
            try {
                byte[] bytes = Files.readAllBytes(file);
                String content = new String(bytes, StandardCharsets.UTF_8);
                totalAInHtmlFiles += countOccurrences(content, 'a');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Count file extensions
        String fileExtension = getFileExtension(file);
        uniqueExtensions.add(fileExtension);

        // Update the counter for every extension
        totalFilesForExtensionsByExtension.merge(fileExtension, 1, Integer::sum);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public int getTotalAInHtmlFiles() {
        return totalAInHtmlFiles;
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public int getTotalDirs() {
        return totalDirs;
    }

    public int getTotalUniqueExtensions() {
        return uniqueExtensions.size();
    }

    public String getListOfUniqueExtensions() {
        return String.join(", ", uniqueExtensions);
    }
    public int getTotalFilesForExtension(String ext) {
        return totalFilesForExtensionsByExtension.getOrDefault(ext, 0);
    }

    private int countOccurrences(String content, char target) {
        return (int) content.chars().filter(ch -> ch == target).count();
    }
    private String getFileExtension(Path file) {
        String fileName = file.getFileName().toString();
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex + 1);
    }
}
