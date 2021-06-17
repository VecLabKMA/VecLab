package main_java.controllers.main_window;

import java.io.File;

public class FileController {
    private static final String defaultFileName = "Untitled.png";
    private static String currentFileName = defaultFileName;
    private static File currentProjectFile;

    public static String getDefaultFileName() {
        return defaultFileName;
    }

    public static String getCurrentFileName() {
        return currentFileName;
    }

    public static void setCurrentFileName(String currentFileName) {
        FileController.currentFileName = currentFileName;
    }

    public static File getCurrentProjectFile() {
        return currentProjectFile;
    }

    public static void setCurrentProjectFile(File currentProjectFile) {
        FileController.currentProjectFile = currentProjectFile;
        FileController.currentFileName = currentProjectFile.getName();
    }

    public static void createNewFile() {
        currentFileName = defaultFileName;
        currentProjectFile = null;
    }
}
