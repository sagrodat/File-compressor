# Overall
## Description
The project is a file compressor and decompressor. It allows the user to decrease the size of a file by providing the input's file path and a path to the output file that will be created. The compressed file can then be further decompressed to restore the original.
## How to use?
In order to use the program, launch the java jar file in "out/artifacts/JK_jar" without arguments or -h in order to print help information
For basic usage launch with arguments : *[input filepath] [output filepath]*

# Implementation
The compression is achieved using **Huffman's** coding algorithm. The huffman's tree is created with a **priority queue** implemented on a **min-heap**. 
The whole project is written using **object-oriented** programming according to the language's practices.


