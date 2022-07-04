import java.util.HashMap;

public class Akshaj_21009_Tokenizer {
    public String[] keywords = new String[]{
            "class", "constructor", "function", "method", "field", "static", "var",
            "int", "char", "boolean", "void", "true", "false", "null", "this", "let",
            "do", "if", "else", "while", "return"
    };

    public String symbol[] = {
            "{", "}", "(", ")", "[", "]", ".", ",", ";", "+", "-", "*", "/", "&", "|",
            "<", ">", "=", "~"
    };

    private enum TYPE {
        KEYWORD,
        SYMBOL,
        INTEGER_CONSTANT,
        STRING_CONSTANT,
        IDENTIFIER
    }

    private Boolean checkArr(String input, TYPE type){
        String[] array = new String[0];
        switch (type){
            case KEYWORD -> array=this.keywords;
            case SYMBOL -> array=this.symbol;
        }

        for(int i=0; i< array.length; i++){
            if(array[i].equals(input)){
                return true;
            }
        }
        return false;
    }

    private Boolean checkIntegerConstant(String input){
        if (input == null) {
            return false;
        }
        try {
            int inp = Integer.parseInt(input);
            if (inp>=0 && inp<=32767){
                return true;
            }

        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    private Boolean checkStringConstant(String inp){
        if(((inp.startsWith("'")&&(inp.endsWith("'"))||(inp.startsWith("\""))&&inp.endsWith("\"")))){
            return true;
        }
        return false;
    }

    private Boolean checkIdentifier(String inp){
        if(Character.isDigit(inp.charAt(0))){
            return false;
        }
        if(inp.contains("'")||inp.contains("\"") ){
            return false;
        }
        return true;
    }

    private String genrateToken(String input, TYPE type){
        final HashMap<TYPE,String> Map = new HashMap<>(java.util.Map.of(
                TYPE.KEYWORD , "keyword",
                TYPE.SYMBOL, "symbol",
                TYPE.INTEGER_CONSTANT,"integerConstant",
                TYPE.STRING_CONSTANT,"stringConstant",
                TYPE.IDENTIFIER,"identifier"
        ));
        return  "<"+Map.get(type)+"> "+input+" </"+Map.get(type)+">\n";
    }

    public String Tokenize(String input){
        input = input.trim();
        if(input != ""){
            if (checkArr(input, TYPE.KEYWORD)) {
                return genrateToken(input, TYPE.KEYWORD);
            } else if (checkArr(input, TYPE.SYMBOL)) {
                if(input.equals("<")){
                    input = "&lt;";
                } else if (input.equals(">")) {
                    input="&gt;";
                } else if (input.equals("&")) {
                    input="&amp;";
                }
                return genrateToken(input, TYPE.SYMBOL);
            } else if (checkIntegerConstant(input)) {
                return genrateToken(input, TYPE.INTEGER_CONSTANT);
            } else if (checkStringConstant(input)) {
                input = input.split("(\")|(\')")[1];
                return genrateToken(input, TYPE.STRING_CONSTANT);
            } else if (checkIdentifier(input)){
                return genrateToken(input, TYPE.IDENTIFIER);
            }
        }
        return null;
    }
}
