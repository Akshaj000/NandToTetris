import java.io.*;

public class Akshaj_21009_Utils {
    public static void Read(String filename){
        Akshaj_21009_Tokenizer tokenizer = new Akshaj_21009_Tokenizer();
        Akshaj_21009_Analyzer analyzer = new Akshaj_21009_Analyzer();
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
        filename = filename.substring(0,filename.indexOf("."));
        BufferedReader bis = null;
        FileReader fis= null;
        String line;
        try {
            fis = new FileReader(file);
            bis = new BufferedReader(fis);
            Write(filename+"T","<tokens>\n");
            while( (line = bis.readLine()) != null ){
                line = Cleanup(line);
                if (line!=null && line.length()>0){
                    String[] _line = line.split(reg);
                    for (int i=0 ; i< _line.length; i++){
                        if(!_line[i].contains("\"")){
                            String[] __l = _line[i].split(" ");
                            for(int j=0; j<__l.length; j++){
                                Token _l = tokenizer.Tokenize(__l[j]);
                                if(_l!=null) {
                                    Append(filename+"T", _l.toString());
                                    analyzer.tokens.add(_l);
                                }
                            }
                        } else {
                            Token _l = tokenizer.Tokenize(_line[i]);
                            if (_l != null) {
                                Append(filename+"T", _l.toString());
                                analyzer.tokens.add(_l);
                            }
                        }
                    }
                }
            }
            Append(filename+"T","</tokens>");
            Write(filename,analyzer.analyzeStatements(analyzer.tokens));
        } catch(FileNotFoundException fnfe) {
            System.out.println("The specified file not found" + fnfe);
        } catch(IOException ioe) {
            System.out.println("I/O Exception: " + ioe);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            FileWriter fw = new FileWriter(filename+".xml",true);
            fw.write(line);
            fw.close();
        } catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public static void Write(String filename, String line) {
        try{
            FileWriter fw = new FileWriter(filename+".xml");
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
