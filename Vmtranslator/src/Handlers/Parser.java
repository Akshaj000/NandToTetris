package Handlers;
import Converters.Operators;
import Converters.Pop;
import Converters.Push;

import java.io.*;

// Parse through the files and convert it to operation, segment, index

public class Parser {
    public static void Parse(String filename){
        File file = new File(filename);
        BufferedReader bis = null;
        FileReader fis= null;
        String line;
        Push push = new Push();
        Pop pop = new Pop();
        Operators operator = new Operators();
        try {
            fis = new FileReader(file);
            bis = new BufferedReader(fis);
            while( (line = bis.readLine()) != null ){
                line = Remove_Whitespace.removeWhiteSpacesAndCommands(line);
                if (line!=null && line.length()>0){
                    String[] l = line.split(" ");
                    String operation = l[0];
                    String segment = null;
                    String index = null;
                    if(l.length == 3){
                        segment = l[1];
                        index = l[2];
                    }
                    Writer.CodeWriter(filename,"//"+line+"\n");
                    System.out.println(line);
                    line = ConvertToVM.ConvertToVM(operation, segment, index, filename, pop, push, operator);
                    System.out.println(line);
                    Writer.CodeWriter(filename, line);
                }
            }
        } catch(FileNotFoundException fnfe) {
            System.out.println("The specified file not found" + fnfe);
        } catch(IOException ioe) {
            System.out.println("I/O Exception: " + ioe);
        } finally {
            try{
                if(bis != null) {
                    fis.close();
                    bis.close();
                }
            }catch(IOException ioe) {
                System.out.println("Error in InputStream close(): " + ioe);
            }
        }
    }
}
