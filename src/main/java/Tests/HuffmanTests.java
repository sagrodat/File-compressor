package Tests;

import Compressor.Encoder;
import Compressor.Decoder;

import FileManagement.FileManager;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;


public class HuffmanTests {
    private String [] testFiles = {"TestFiles\\draw.png","TestFiles\\test.txt","TestFiles\\romeo.txt"};
    private String outputFileName = "TestFiles\\output.bin";
    private String decompressedFileName = "TestFiles\\actual.txt";

    Encoder encoder;
    Decoder decoder;
    public HuffmanTests()
    {
        this.encoder = new Encoder();
        this.decoder = new Decoder();
    }

    @Test
    public void shouldCorrectlyCompressAndDecompressFiles() {

        File previousCompressedFile = new File(outputFileName);
        previousCompressedFile.delete();
        File previousDecompressedFile = new File(decompressedFileName);
        previousDecompressedFile.delete();

        encoder.encodeContent(testFiles[1]);
        encoder.saveEncodedContentToFile(outputFileName);

        decoder.decodeContent(outputFileName);
        decoder.saveDecodedContentToFile(decompressedFileName);

        Assert.assertEquals(true,areFilesIdentical(testFiles[1], decompressedFileName) );
    }

    private boolean areFilesIdentical(String expectedFIlePath, String actualFilePath)
    {
        FileManager expectedFile = new FileManager(expectedFIlePath,"r");
        FileManager actualFile = new FileManager(actualFilePath,"rw");
        if(expectedFile.length() != actualFile.length())
        {
            System.out.println(expectedFile.length() + " " +  actualFile.length());
            return false;
        }

        int actual;
        while((actual = actualFile.read()) != -1)
        {
            int expected = expectedFile.read();
            if(actual != expected)
            {
                System.out.println(actual + " " + expected);
                return false;
            }

        }

        return true;
    }
}
