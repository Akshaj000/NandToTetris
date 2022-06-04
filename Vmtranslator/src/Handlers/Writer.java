package Handlers;

import java.io.FileWriter;
import java.io.IOException;

// Writes to the new file

public class Writer {
    public static void CodeWriter(String filename, String line) {
        try
        {
            FileWriter fw = new FileWriter(filename.substring(0,filename.indexOf("."))+".asm",true);
            fw.write(line);
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
