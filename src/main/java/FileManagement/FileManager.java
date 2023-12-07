package FileManagement;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileManager {
    private RandomAccessFile randomAccessFile;
    public FileManager(String filePath, String mode)
    {
        try
        {
            this.randomAccessFile = new RandomAccessFile(filePath,mode);
        }
        catch(IOException e)
        {
            System.err.println("Error when creating reference to file!");
        }

    }

    public void write(int value)
    {
        try
        {
            randomAccessFile.write(value);
        }
        catch(IOException e)
        {
            System.err.println("Error when writing to file!");
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
            System.err.println("Error when reading from file!");
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
            System.err.println("Error when performing operations on file!");
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
            System.err.println("Error when performing operations on file!");
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
            System.err.println("Error when performing operations on file!");
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
            System.err.println("Error when closing file!");
        }
    }

    public RandomAccessFile getFileHandle(){return this.randomAccessFile;}





}
