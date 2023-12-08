package Compressor;

public class Compressor {
    private Encoder encoder;
    private Decoder decoder;

    public void compress(String inputFilePath, String outputFilePath)
    {
        if(this.encoder == null)
            this.encoder = new Encoder();
        encoder.encodeContentAndSaveToFile(inputFilePath, outputFilePath);
    }
    public void decompress(String inputFilePath, String outputFilePath)
    {
        if(this.decoder == null)
            this.decoder = new Decoder();
        decoder.decodeContentAndSaveToFile(inputFilePath,outputFilePath);
    }

    public Encoder getEncoder(){return this.encoder;}
    public Decoder getDecoder(){return this.decoder;}
}
