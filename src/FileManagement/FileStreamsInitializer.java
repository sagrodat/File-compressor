package FileManagement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreamsInitializer {
    private FileInputStream fis;
    private FileOutputStream fos;
    public FileStreamsInitializer( String inputPath, String outputPath)
    {
        try
        {
            this.fis = new FileInputStream(inputPath);
        }
        catch (IOException e)
        {
            System.err.println("Failed to open file : " + inputPath);
        }
        try
        {
            this.fos = new FileOutputStream(outputPath);
        }
        catch (IOException e)
        {
            System.err.println("Failed to create/open output file : " + outputPath);
        }
    }

    public FileInputStream getFileInputStream() {
        return fis;
    }

    public FileOutputStream getFileOutputStream() {
        return fos;
    }

}
