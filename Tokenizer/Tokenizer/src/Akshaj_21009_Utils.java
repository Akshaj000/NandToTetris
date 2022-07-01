import java.io.*;

public class Akshaj_21009_Utils {
    public static void Read(String filename){
        Akshaj_21009_Tokenizer tokenizer = new Akshaj_21009_Tokenizer();
        File file = new File(filename);
        BufferedReader bis = null;
        FileReader fis= null;
        String line;
        try {
            fis = new FileReader(file);
            bis = new BufferedReader(fis);
            while( (line = bis.readLine()) != null ){
                line = Cleanup(line);
                if (line!=null && line.length()>0){
                    line = tokenizer.Tokenize(line);
                    Write(filename, line);
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

    public static void Write(String filename, String line) {
        try
        {
            FileWriter fw = new FileWriter(filename.substring(0,filename.indexOf("."))+".xml",true);
            fw.write(line);
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public static String Cleanup(String line){
        if (line.equals("")  || line.startsWith("//")) {
            return null;
        } else  if (line.contains("//")){
            line = line.split("//")[0].trim();
            return line;
        }
        return line;
    }
}
