import java.util.HashMap;

public class Akshaj_21009_Tokenizer {
    private String[] keywords = new String[]{
            "class", "constructor", "function", "method", "field", "static", "var",
            "int", "char", "boolean", "void", "true", "false", "null", "this", "let",
            "do", "if", "else", "while", "return"
    };

    private String symbol[] = {
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
                TYPE.INTEGER_CONSTANT,"intConst",
                TYPE.STRING_CONSTANT,"stringConst",
                TYPE.IDENTIFIER,"identifier"
        ));
        return  "<"+Map.get(type)+">"+input+"<"+Map.get(type)+">";
    }

    public String Tokenize(String input){
        String regex = "";
        for (int i=0; i< symbol.length;i++){
            regex+=symbol[i];
        }
        String[] inp = input.split(regex);
        for (int i=0; i < inp.length; i++) {
            inp[i] = inp[i].trim();
            if(inp[i] != ""){
                if (checkArr(inp[i], TYPE.KEYWORD)) {
                    return genrateToken(inp[i], TYPE.KEYWORD);
                } else if (checkArr(inp[i], TYPE.SYMBOL)) {
                    return genrateToken(inp[i], TYPE.SYMBOL);
                } else if (checkIntegerConstant(input)) {
                    return genrateToken(inp[i], TYPE.INTEGER_CONSTANT);
                } else if (checkStringConstant(input)) {
                    return genrateToken(inp[i], TYPE.STRING_CONSTANT);
                } else if (checkIdentifier(input)){
                    return genrateToken(inp[i], TYPE.IDENTIFIER);
                }
            }
        }
        return null;
    }
}
