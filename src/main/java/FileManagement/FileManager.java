package FileManagement;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManager {
    private RandomAccessFile randomAccessFile;
    private String filePath;

    //CONSTRUCTORS

    public FileManager(String filePath, String mode)
    {
        this.filePath = filePath;
        try
        {
            this.randomAccessFile = new RandomAccessFile(filePath,mode);
        }
        catch(IOException e)
        {
            System.err.println("Error when opening file \"" + filePath + "\"! Make sure the file exists and that you have the rights to access the file." + e.getMessage());
            printHowToGetHelp();
            System.exit(-1);
        }

    }

    //WORKERS

    public void write(int value)
    {
        try
        {
            randomAccessFile.write(value);
        }
        catch(IOException e)
        {
            handleDefaultFileWritingException(e);
        }
    }

    public void write(String text)
    {
        try
        {
            randomAccessFile.writeBytes(text);
        }
        catch(IOException e)
        {
            handleDefaultFileWritingException(e);
        }
    }

    public int read()
    {
        int value = -1;
        try
        {
            value = randomAccessFile.read();
        }
        catch(IOException e)
        {
            System.err.println("Error when reading from file \"" + filePath + "\"! Make sure you have the rights to read from the file." + e.getMessage());
            printHowToGetHelp();
            System.exit(-1);
        }
        return value;
    }
    public void seek(long pos)
    {
        try
        {
            randomAccessFile.seek(pos);
        }
        catch(IOException e)
        {
            handleDefaultFileOperationsException(e);
        }
    }
    public long length()
    {
        long length = - 1;
        try
        {
            length = randomAccessFile.length();
        }
        catch (IOException e)
        {
            handleDefaultFileOperationsException(e);
        }
        return length;

    }

    public long getFilePointerPosition()
    {
        long position = -1;
        try
        {
            position = randomAccessFile.getFilePointer();
        }
        catch (IOException e)
        {
            handleDefaultFileOperationsException(e);
        }
        return position;
    }

    public void close()
    {
        try
        {
            randomAccessFile.close();
        }
        catch (IOException e)
        {
            handleDefaultFileOperationsException(e);
        }
    }

    //OTHER

    private void handleDefaultFileOperationsException(Exception e)
    {
        System.err.println("Error when closing file \"" + filePath + "\"! Make sure you have the rights to perform operations on the file." + e.getMessage());
        printHowToGetHelp();
        System.exit(-1);
    }
    private void handleDefaultFileWritingException(Exception e)
    {
        System.err.println("Error when writing to file \"" + filePath + "\"! Make sure you have the rights to write to the file." + e.getMessage());
        printHowToGetHelp();
        System.exit(-1);
    }

    public void printHowToGetHelp()
    {
        System.err.println("Launch the program with -h as the only argument to receive help");
    }
    public RandomAccessFile getFileHandle(){return this.randomAccessFile;}
    public String getFilePath(){return this.filePath;}
}
