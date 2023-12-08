package Tests;

import Compressor.Encoder;
import Compressor.Decoder;
import Compressor.Compressor;

import FileManagement.FileInfoReader;
import FileManagement.FileManager;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class HuffmanTests {
    private String [] testFiles = {"TestFiles\\draw.png","TestFiles\\test.txt","TestFiles\\romeo.txt"};
    Compressor compressor;
    public HuffmanTests()
    {
        this.compressor = new Compressor();
    }

    @Test
    public void shouldCorrectlyCompressAndDecompressFiles() {
        boolean testStatus = true;
        FileInfoReader fileInfoReader = new FileInfoReader();

        for(String testFile : testFiles)
        {
            String outputFileName = fileInfoReader.getName(testFile) + "out." + fileInfoReader.getExtension(testFile);
            compressor.compress(testFile,outputFileName);
            String decompressedFileName = fileInfoReader.getName(testFile) + "decompressed." + fileInfoReader.getExtension(testFile);
            compressor.decompress(outputFileName,decompressedFileName);

            if(testStatus)
                testStatus = areFilesIdentical(testFile,decompressedFileName);


            File outputFile = new File(outputFileName);
            outputFile.delete();
            File decompressedFile = new File(decompressedFileName);
            decompressedFile.delete();
        }
        Assert.assertEquals(true,testStatus );
    }

    private boolean areFilesIdentical(String expectedFilePath, String actualFilePath)
    {
        FileManager expectedFile = new FileManager(expectedFilePath,"r");
        FileManager actualFile = new FileManager(actualFilePath,"r");
        if(expectedFile.length() != actualFile.length())
        {
            System.out.println(expectedFile.length() + " " +  actualFile.length());
            closeFiles(actualFile,expectedFile);
            return false;
        }

        int actual;
        while((actual = actualFile.read()) != -1)
        {
            int expected = expectedFile.read();
            if(actual != expected)
            {
                System.out.println(actual + " " + expected);
                closeFiles(actualFile,expectedFile);
                return false;
            }
        }
        closeFiles(actualFile,expectedFile);
        return true;
    }
    private void closeFiles(FileManager f1, FileManager f2)
    {
        f1.close();
        f2.close();
    }
}
