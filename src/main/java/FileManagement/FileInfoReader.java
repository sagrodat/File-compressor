package FileManagement;

import Utility.Constants;

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

    public String getName(File file)
    {
        return cutOutExtension(file.getName());
    }
    public String getName(String fullFileName) {return cutOutExtension(fullFileName);}
    private String cutOutExtension(String fullFileName)
    {
        if( fullFileName.contains(".") )
        {
            String output = new String();
            for(int i = 0; i < fullFileName.lastIndexOf(".");  i++)
            {
                output+=fullFileName.charAt(i);
            }
            return output;
        }
        else return fullFileName;
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

}
