import Compressor.Decoder;
import Compressor.Encoder;
import FileManagement.FileManager;
import Tests.HuffmanTests;
// to do
// make readbuffer a seperate class
// fix tests

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "TestFiles\\romeo.txt";
        String outputFilePath = "TestFiles\\output.txt";
        String decodedInputFilePath = "TestFiles\\decodedInput.txt";


        /*Encoder encoder = new Encoder();
        encoder.encodeContent("TestFiles\\romeo.txt");
        encoder.saveEncodedContentToFile("TestFiles\\romeoTEST.txt");

        encoder.encodeContent("TestFiles\\test.txt");
        encoder.saveEncodedContentToFile("TestFiles\\testTEST.txt");

        Decoder decoder = new Decoder();
        decoder.decodeContent("TestFiles\\romeoTEST.txt");
        decoder.saveDecodedContentToFile("TestFiles\\romeoDECODED.txt");

        decoder.decodeContent("TestFiles\\testTEST.txt");
        decoder.saveDecodedContentToFile("TestFiles\\testDECODED.txt");*/


        //////////////////////


       /* Encoder encoder = new Encoder();
        encoder.encodeContent(inputFilePath);

        encoder.saveEncodedContentToFile(outputFilePath);
        encoder.printCodes();
        System.out.println("!!!!!!!!!!!!!!!!!");

        Decoder decoder = new Decoder();
        decoder.decodeContent(outputFilePath);
        decoder.saveDecodedContentToFile(decodedInputFilePath);
        decoder.printCodes();

        FileManager input = new FileManager(inputFilePath,"r");
        FileManager output = new FileManager(outputFilePath,"r");
        input.seek(input.length() - 2);
        output.seek(output.length() - 2);

        int tmp;
        while((tmp = input.read()) != -1)
            System.out.print((char)tmp + " ");
        System.out.println();
        while((tmp = output.read()) != -1)
            System.out.print(tmp + " ");
        System.out.println();*/

    }
}