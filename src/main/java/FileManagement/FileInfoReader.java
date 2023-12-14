package FileManagement;

import GlobalVariables.Constants;

import java.io.File;

public class FileInfoReader {
    public String getExtension(File file)
    {
       String fullFileName = file.getName();
       return getExtension(fullFileName);
    }
    public String getExtension(String fileName)
    {
        int lastDotIndex = fileName.lastIndexOf(".");
        if(lastDotIndex == -1)
            return "";
        String extension = new String();
        for(int i = lastDotIndex + 1; i < fileName.length(); i++)
            extension += fileName.charAt(i);

        return "." + extension;
    }


    public String getFileNameWithoutExtension(String fullFilePath)
    {
        String fullFilePathWithoutExtension = getFullPathWithoutExtension(fullFilePath);
        String result = new String();
        for(int i = fullFilePathWithoutExtension.length() - 1 ; i >= 0 ; i--)
        {
            if(fullFilePathWithoutExtension.charAt(i) == '\\')
                break;

            result += fullFilePathWithoutExtension.charAt(i);
        }
        return new StringBuilder(result).reverse().toString();
    }


    public String getFullPathWithoutExtension(File file)
    {
        return cutOutExtension(file.getName());
    }
    public String getFullPathWithoutExtension(String fullFilePath) {return cutOutExtension(fullFilePath);}
    private String cutOutExtension(String fullFilePath)
    {
        if( fullFilePath.contains(".") )
        {
            String output = new String();
            for(int i = 0; i < fullFilePath.lastIndexOf(".");  i++)
            {
                output+=fullFilePath.charAt(i);
            }
            return output;
        }
        else return fullFilePath;
    }

    public boolean isCompressionTagPresent(String filePath)
    {
        FileManager fileManager = new FileManager(filePath,"r");
        for(int i = 0 ; i < Constants.compressionTag.length(); i++)
        {
            int tmp = fileManager.read();
            if(tmp != Constants.compressionTag.charAt(i))
            {
                fileManager.close();
                return false;
            }
        }
        fileManager.close();
        return true;
    }

    public void printFileInBinary(String filePath)
    {
        FileManager fileManager = new FileManager(filePath,"r");
        int tmp;
        while((tmp = fileManager.read()) != -1)
        {
            for(int i = 0 ; i < 8 ; i++)
            {
                int val = (tmp >> (7-i)) & 1;
                System.out.print(val);
            }
            System.out.print(" ");
        }
        fileManager.close();
    }

}
