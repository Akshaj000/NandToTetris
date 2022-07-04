import java.io.*;
import java.util.ArrayList;

public class Akshaj_21009_Utils {
    public static void Read(String filename){
        Akshaj_21009_Tokenizer tokenizer = new Akshaj_21009_Tokenizer();
        String[] symbol = tokenizer.symbol;
        String sreg ="";
        for (int i=0; i<symbol.length; i++){
            sreg+="\\"+symbol[i];
            if(i!= symbol.length-1){
                sreg+="|";
            }
        }
        String reg = "((?=["+sreg+"])|(?<=["+sreg+"]))";
        File file = new File(filename);
        BufferedReader bis = null;
        FileReader fis= null;
        String line;
        try {
            fis = new FileReader(file);
            bis = new BufferedReader(fis);
            Write(filename,"<tokens>\n");
            while( (line = bis.readLine()) != null ){
                line = Cleanup(line);
                if (line!=null && line.length()>0){
                    String[] _line = line.split(reg);
                    for (int i=0 ; i< _line.length; i++){
                        if(!_line[i].contains("\"")){
                            String[] __l = _line[i].split(" ");
                            for(int j=0; j<__l.length; j++){
                                String _l = tokenizer.Tokenize(__l[j]);
                                if(_l!=null) {
                                    Append(filename, _l);
                                }
                            }
                        } else {
                            String _l = tokenizer.Tokenize(_line[i]);
                            if (_l != null) {
                                Append(filename, _l);
                            }
                        }
                    }
                }
            }
            Append(filename,"</tokens>");
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

    public static void Append(String filename, String line) {
        try {
            FileWriter fw = new FileWriter(filename.substring(0,filename.indexOf("."))+"T.xml",true);
            fw.write(line);
            fw.close();
        } catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public static void Write(String filename, String line) {
        try{
            FileWriter fw = new FileWriter(filename.substring(0,filename.indexOf("."))+"T.xml");
            fw.write(line);
            fw.close();
        } catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public static String Cleanup(String line){
        line = line.trim();
        if (line.equals("")  || line.startsWith("//") || line.startsWith("/**") || line.startsWith("*")) {
            return null;
        } else  if (line.contains("//")){
            line = line.split("//")[0].trim();
            return line;
        }
        return line;
    }
}
