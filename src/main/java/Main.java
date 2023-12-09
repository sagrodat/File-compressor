import Compressor.Compressor;
import Compressor.Decoder;
import Compressor.Encoder;
import FileManagement.FileManager;
import Utility.Constants;

import java.sql.SQLOutput;

// to do
//fix compressing files that have . in their name
public class Main {
    public static void main(String[] args) {
        String inputFilePath = "TestFiles\\x.cpp";
        String outputFilePath = "TestFiles\\xout";
        String decodedInputFilePath = "TestFiles\\xin.cpp";

        Compressor compressor = new Compressor();
        compressor.performAction(inputFilePath,outputFilePath);
        compressor.performAction(outputFilePath + "."+ Constants.customExtension,decodedInputFilePath);



        System.out.println("Codes before : ");
        compressor.getEncoder().printCodes();
        System.out.println("Codes after : ");
        compressor.getDecoder().printCodes();
        System.out.println();

        FileManager before = new FileManager(inputFilePath,"r");
        FileManager after = new FileManager(decodedInputFilePath ,"r");
        FileManager compressed = new FileManager(outputFilePath + "."+ Constants.customExtension, "r");
        compressed.seek(compressed.length() - 2);

        before.seek(before.length() - 2);
        after.seek(after.length() - 2);

        System.out.print("Before : ");
        int tmp;
        while((tmp = before.read()) != -1)
        {
            System.out.print(tmp + " ");
        }
        System.out.println();
        System.out.print("after : ");
        while((tmp = after.read()) != -1)
        {
            System.out.print(tmp + " ");
        }
        System.out.println();
        System.out.print("compressed : ");
        while((tmp = compressed.read()) != -1)
        {
            System.out.print(tmp + " ");
        }
    }
}