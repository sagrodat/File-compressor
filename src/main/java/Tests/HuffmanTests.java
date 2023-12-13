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
    private Compressor compressor;
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
    public void shouldCorrectlyCompressAndDecompressTestFiles() {
        for(int i = 0 ; i < testFilesPaths.size(); i++)
        {
            System.out.println("Testing file " + "(" + (i+1) + "/" + testFilesPaths.size() + ")" + " : " +  testFilesPaths.get(i)  );
            Assert.assertTrue( compressedAndDecompressedSuccessfully(testFilesPaths.get(i)) );
        }

    }

    private boolean compressedAndDecompressedSuccessfully(String filePath)
    {

        Timer timer = new Timer();
        FileInfoReader fileInfoReader = new FileInfoReader();


        timer.start();

        String compressedFileName = fileInfoReader.getFullPathWithoutExtension(filePath);
        compressor.compress(filePath,compressedFileName);
        String compressedFileFullName = compressedFileName + Constants.customExtension;

        String decompressedFileName = fileInfoReader.getFullPathWithoutExtension(filePath) + "decompressed";
        compressor.decompress( compressedFileFullName ,decompressedFileName);
        String decompressedFileFullName = decompressedFileName + fileInfoReader.getExtension(filePath);

        boolean result = areFilesIdentical(filePath,decompressedFileFullName);

        File outputFile = new File(compressedFileFullName);
        outputFile.delete();
        File decompressedFile = new File(decompressedFileFullName);
        decompressedFile.delete();

        System.out.println("Test passed!");
        timer.stop();
        timer.printMeasurement();
        timer.reset();
        System.out.println();

        return result;
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
