import Compressor.Decoder;
import Compressor.Encoder;
import Tests.HuffmanTests;
// to do
// make creating codes from String independent from decoder and encoder ( code duplication )

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "draw.png";
        String outputFilePath = "output.txt";
        String decodedInputFilePath = "decodedInput.txt";

        Encoder encoder = new Encoder(inputFilePath,outputFilePath);
        encoder.encodeContent();
        encoder.saveEncodedContentToFile();

       /* try{
            RandomAccessFile x = new RandomAccessFile(outputFilePath,"rw");
            int tmp;
            try {
                while ((tmp = x.read()) != -1) {
                    System.out.println(tmp);
                }
            }
            catch (IOException e)
            {
                System.err.println("Failed to read from file!");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/

        Decoder decoder = new Decoder(outputFilePath,decodedInputFilePath);
        decoder.decodeContent();
        decoder.saveDecodedContentToFile();

        HuffmanTests huffmanTests = new HuffmanTests();
        System.out.println(huffmanTests.testIfFilesAreEqual(inputFilePath,decodedInputFilePath));
    }
}