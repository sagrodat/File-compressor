package GlobalVariables;


public class Constants {
    public static final int MAX_UNIQUE = 256;
    public static final int EndOfExtension = 31;
    public static final String customExtension = ".JK";
    public static final String compressionTag = "JK";

    public static final String helpInfo = """
            Help information can be printed using the following: "-h", "--help", "-?", "h", "help", "?"
                
            USAGE: [input filepath] [output filepath] [options]
            Input filepath has to be a full path of the file, including the name and extension
            Output filepath can be either a directory or have a specified filename
            
            OPTIONS:
            """ + Options.timerOption + """   
             print time elapsed after operation is complete
            
            ADDITIONAL INFORMATION:
            All compressed files are created with a custom .JK extension; changing the extension won't remove the possibility of later decompression
            The compression is done using Huffman's algorithm on 8 bit symbols.
            """;
}
