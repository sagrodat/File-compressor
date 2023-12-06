package Tests;
import java.io.IOException;
import java.io.RandomAccessFile;

public class HuffmanTests {
    public boolean testIfFilesAreEqual(String filePathA, String filePathB)
    {
        try{
            RandomAccessFile A = new RandomAccessFile(filePathA,"r");
            RandomAccessFile B = new RandomAccessFile(filePathA,"r");
            if(A.length() != B.length())
                return false;
            int input1;
            int input2;
            while((input1 = A.read()) != -1)
            {
                input2 = B.read();
                if(input1 != input2)
                    return false;
            }
        }
        catch (IOException e)
        {
            System.err.println("File error while performing tests!");
        }
        return true;
    }

}
