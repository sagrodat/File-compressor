package FileManagement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileStreamsInitializer {
    private RandomAccessFile inputFile;
    private RandomAccessFile outputFile;
    public FileStreamsInitializer( String inputPath, String outputPath)
    {
        try
        {
            this.inputFile = new RandomAccessFile(inputPath,"r");
        }
        catch (IOException e)
        {
            System.err.println("Failed to open file : " + inputPath);
        }
        try
        {
            this.outputFile = new RandomAccessFile(outputPath,"rw");
        }
        catch (IOException e)
        {
            System.err.println("Failed to create/open output file : " + outputPath);
        }
    }

    public RandomAccessFile getFileInputStream() {
        return inputFile;
    }

    public RandomAccessFile getFileOutputStream() {
        return outputFile;
    }

}
