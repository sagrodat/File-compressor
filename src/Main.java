import Compressor.Decoder;
import Compressor.Encoder;
import FileManagement.FileStreamsInitializer;
import Tree.Node;
import Utility.Constants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
// to do
// make creating codes from String independent from decoder and encoder ( code duplication )

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "draw.png";
        String outputFilePath = "output.txt";
        Encoder encoder = new Encoder(inputFilePath,outputFilePath);
        encoder.createCodes();
        encoder.printCodes();
        String [] before = encoder.getCodes();

        ArrayList<Integer> data = encoder.getTree().getBinaryTreeData();
        FileStreamsInitializer fis = new FileStreamsInitializer("",outputFilePath);
        for(Integer el : data)
        {
            try
            {
                fis.getFileOutputStream().write(el);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }


        System.out.println();

        Decoder decoder = new Decoder("output.txt","");
        decoder.createCodes();
        decoder.printCodes();
        String [] after = decoder.getCodes();


        for(int i = 0 ;i  <Constants.MAX_UNIQUE ; i++)
        {
            if(before[i] == null && after[i] == null)
                continue;

            if(before[i].compareTo(after[i]) != 0)
                System.out.println("TEST FAILED!");
        }

    }
}