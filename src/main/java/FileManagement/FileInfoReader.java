package FileManagement;

import java.io.File;

public class FileInfoReader {
    public String getExtension(File file)
    {
       String fullFileName = file.getName();
       return getExtension(fullFileName);
    }
    public String getExtension(String fileName)
    {
        int lastDotIndex = -1;
        for(int i = fileName.length() - 1 ; i >= 0 ; i--)
        {
            if(fileName.charAt(i) == '.')
            {
                lastDotIndex = i;
                break;
            }
        }
        String extension = new String();

        for(int i = lastDotIndex + 1; i < fileName.length(); i++)
            extension += fileName.charAt(i);

        return extension;
    }

    public String getName(File file)
    {
        return cutOutExtension(file.getName());
    }
    public String getName(String fileName)
    {
        return cutOutExtension(fileName);
    }
    private String cutOutExtension(String fileName)
    {
        String output = new String();
        if( fileName.contains(".") )
        {
            for(int i = 0; i < fileName.lastIndexOf(".");  i++)
            {
                output+=fileName.charAt(i);
            }
        }
        return output;
    }

}
