package Tests;

import Compressor.Compressor;

import FileManagement.FileInfoReader;
import FileManagement.FileManager;
import Utility.Constants;
import Utility.Timer;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;


public class HuffmanTests {
    private ArrayList<String> testFilesPaths;
    Compressor compressor;
    public HuffmanTests()
    {
        this.compressor = new Compressor();
        createListOfTestFiles();

    }
    private void createListOfTestFiles()
    {
        this.testFilesPaths = new ArrayList<>();
        String testFilesPath = "TestFiles\\";
        File directory = new File(testFilesPath);
        if(!directory.isDirectory())
            return;
        File[] files = directory.listFiles();
        for(File file : files)
        {
            testFilesPaths.add(file.getAbsolutePath());
        }
    }

    @Test
    public void shouldCorrectlyCompressAndDecompressFiles() {
        boolean testPassed = true;
        Timer timer = new Timer();
        FileInfoReader fileInfoReader = new FileInfoReader();
        for(String testFile : testFilesPaths)
        {
            System.out.println("Testing file " + testFile);
            timer.start();

            String compressedFileName = fileInfoReader.getName(testFile);
            compressor.compress(testFile,compressedFileName);
            String fullCompressedFileName = compressedFileName + fileInfoReader.getExtension(testFile)+ Constants.customExtension;
            System.out.println(fullCompressedFileName);

            String decompressedFileName = fileInfoReader.getName(testFile) + "decompressed";
            compressor.decompress( fullCompressedFileName ,decompressedFileName);
            String fullDecompressedFileName = decompressedFileName + fileInfoReader.getExtension(testFile);

            if(testPassed)
            {
                testPassed = areFilesIdentical(testFile,fullDecompressedFileName);
                if(testPassed)
                {
                    System.out.println("Test passed!");
                    timer.stop();
                    timer.printMeasurement();
                    timer.reset();
                    System.out.println();
                }
                else
                    break;
            }

            File outputFile = new File(fullCompressedFileName);
            outputFile.delete();
            File decompressedFile = new File(fullDecompressedFileName);
           decompressedFile.delete();
        }
        Assert.assertEquals(true,testPassed );
    }

    private boolean areFilesIdentical(String expectedFilePath, String actualFilePath)
    {
        FileManager expectedFile = new FileManager(expectedFilePath,"r");
        FileManager actualFile = new FileManager(actualFilePath,"r");
        if(expectedFile.length() != actualFile.length())
        {
            System.out.println(actualFile.length() + " " + expectedFile.length());
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
