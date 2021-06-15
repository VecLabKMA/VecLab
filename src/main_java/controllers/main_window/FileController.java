package main_java.controllers.main_window;

import java.io.File;

public class FileController {
    private static final String defaultFileName = "Untitled.png";
    private static String currentFileName = defaultFileName;
    private static File currentFile;

    public static String getDefaultFileName() {
        return defaultFileName;
    }

    public static String getCurrentFileName() {
        return currentFileName;
    }

    public static void setCurrentFileName(String currentFileName) {
        FileController.currentFileName = currentFileName;
    }

    public static File getCurrentFile() {
        return currentFile;
    }

    public static void setCurrentFile(File currentFile) {
        FileController.currentFile = currentFile;
        FileController.currentFileName = currentFile.getName();
    }
}
