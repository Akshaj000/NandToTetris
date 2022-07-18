import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;

enum TYPE {
    KEYWORD,
    SYMBOL,
    INTEGER_CONSTANT,
    STRING_CONSTANT,
    IDENTIFIER,
    OPERATOR,
    CONSTANT,
    VARIABLENAME,
    TERM,
    EXPRESSION,
    STATEMENT,
    LETSTATEMENT,
    WHILESTATEMENT,
    IFSTATEMENT,

}
class Token {
    public String type;
    public String value;

    @Override
    public String toString() {
        return "<"+this.type+"> "+this.value+" <"+this.type+">\n";
    }
}
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

    public Boolean checkIntegerConstant(String input){
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

    public Boolean checkStringConstant(String inp){
        if(((inp.startsWith("'")&&(inp.endsWith("'"))||(inp.startsWith("\""))&&inp.endsWith("\"")))){
            return true;
        }
        return false;
    }

    public Boolean checkIdentifier(String inp){
        if(Character.isDigit(inp.charAt(0))){
            return false;
        }
        if(inp.contains("'")||inp.contains("\"") ){
            return false;
        }
        return true;
    }

    public Token genrateToken(String input, TYPE type){
        final HashMap<TYPE,String> Map = new HashMap<>();
        Map.put(TYPE.KEYWORD,"keyword");
        Map.put(TYPE.SYMBOL,"symbol");
        Map.put(TYPE.INTEGER_CONSTANT,"integerConstant");
        Map.put(TYPE.STRING_CONSTANT,"stringConstant");
        Map.put(TYPE.IDENTIFIER,"identifier");
        Map.put(TYPE.OPERATOR,"operator");
        Map.put(TYPE.CONSTANT,"constant");
        Map.put(TYPE.VARIABLENAME,"variableName");
        Map.put(TYPE.TERM,"term");
        Map.put(TYPE.EXPRESSION,"expression");
        Map.put(TYPE.STATEMENT,"statement");
        Map.put(TYPE.LETSTATEMENT,"letStatement");
        Map.put(TYPE.WHILESTATEMENT,"whileStatement");
        Map.put(TYPE.IFSTATEMENT,"ifStatement");
        Token token = new Token();
        token.type = Map.get(type).trim();
        token.value = input.trim();
        return  token;
    }

    public Token Tokenize(String input){
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
                } else if (input.equals(">=")){
                    input="&ge;";
                } else if (input.equals("<=")){
                    input="&le;";
                } else if (input.equals("==")){
                    input="&eq;";
                } else if (input.equals("!=")){
                    input="&ne;";
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
