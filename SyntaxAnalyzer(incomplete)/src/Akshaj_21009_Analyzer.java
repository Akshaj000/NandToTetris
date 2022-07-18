import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

public class Akshaj_21009_Analyzer {
    Akshaj_21009_Tokenizer tokenizer = new Akshaj_21009_Tokenizer();
    ArrayList<Token> tokens = new ArrayList<>();

    private String analyzeOperator(Token token) throws Exception {
        String[] operators = new String[]{"+", "-", "=", "&gt;", "&lt;","==","&gt;=","&lt;=","!="};
        for (String operator : operators) {
            if (operator.trim().equals(token.value)) {
                return tokenizer.genrateToken(token.toString(), TYPE.OPERATOR).toString();
            }
        }
        throw new RuntimeException("Invalid Operator");
    }
    private String analyzeConstant(Token token){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if(pattern.matcher(token.value).matches()){
            return tokenizer.genrateToken(token.toString(), TYPE.CONSTANT).toString();
        }
        throw new RuntimeException("Invalid Constant "+token.value);
    }
    private String analyzeVarName(Token token){
        if(tokenizer.checkIdentifier(token.value)){
            return tokenizer.genrateToken(token.toString(), TYPE.VARIABLENAME).toString();
        }
        throw new RuntimeException("Invalid Variable Name "+token.value);
    }
    private String analyzeTerm(Token token){
        if(token.type.equals("identifier")){
            analyzeVarName(token);
            return tokenizer.genrateToken(analyzeVarName(token), TYPE.TERM).toString();
        } else if (token.type.equals("integerConstant")){
            analyzeConstant(token);
            return tokenizer.genrateToken(analyzeConstant(token), TYPE.TERM).toString();
        } else if (token.value.equals("true")||token.value.equals("false")){
            return tokenizer.genrateToken(token.toString(), TYPE.TERM).toString();
        }
        throw new RuntimeException("Invalid Term "+token.value);
    }
    private String analyzeExpression(ArrayList<Token> tokens) throws Exception {
        String expression;
        if(tokens.size() == 1){
            if(analyzeTerm(tokens.get(0))!=null){
                expression = analyzeTerm(tokens.get(0));
                return tokenizer.genrateToken(expression, TYPE.EXPRESSION).toString();
            }
        } else if (tokens.size() == 3){
            boolean analyzeterm = analyzeTerm(tokens.get(0)) !=null && analyzeTerm(tokens.get(2)) !=null;
            boolean analyzeOperator = analyzeOperator(tokens.get(1)) != null;
            if(analyzeterm && analyzeOperator){
                expression = analyzeTerm(tokens.get(0)) + analyzeOperator(tokens.get(1)) + analyzeTerm(tokens.get(2));
                return tokenizer.genrateToken(expression, TYPE.EXPRESSION).toString();
            }
        }
        throw new RuntimeException("Invalid Expression");
    }
    private String analyzeLetStatement(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        boolean checklet = tokens.get(0).value.equals("let");
        if(checklet){
            expression += tokens.get(0).toString();
        } else {
            throw new RuntimeException("Invalid Let Statement, Missing Let");
        }
        boolean checkvar = analyzeVarName(tokens.get(1)) != null;
        if(checkvar){
            expression += analyzeVarName(tokens.get(1));
        } else {
            throw new RuntimeException("Invalid Let Statement, Missing Variable Name");
        }
        boolean checkoperator = analyzeOperator(tokens.get(2)) != null;
        if(checkoperator){
            expression += analyzeOperator(tokens.get(2));
        } else {
            throw new RuntimeException("Invalid Let Statement, Missing Operator");
        }
        ArrayList<Token> sublist = new ArrayList<>(tokens.subList(3, tokens.size()-1));
        boolean checkexp = analyzeExpression(sublist) != null;
        if(checkexp){
            expression += analyzeExpression(sublist);
        } else {
            throw new RuntimeException("Invalid Let Statement, Missing Expression");
        }
        boolean checkcloseingtag = tokens.get(tokens.size()-1).value.equals(";");
        if(checkcloseingtag){
            expression += tokens.get(tokens.size()-1).toString();
        } else {
            throw new RuntimeException("Invalid Let Statement, Missing Closing Tag");
        }
        return tokenizer.genrateToken(expression, TYPE.LETSTATEMENT).toString();
    }
    public String analyzeWhileStatement(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        boolean checkwhile = tokens.get(0).value.equals("while");
        if(checkwhile){
            expression += tokens.get(0).toString();
        } else {
            throw new RuntimeException("Invalid While Statement, Missing While");
        }
        boolean openbracket = tokens.get(1).value.equals("(");
        if(openbracket){
            expression += tokens.get(1).toString();
        } else {
            throw new RuntimeException("Invalid While Statement, Missing Opening Bracket");
        }
        int index = getIndexOf(tokens,2,")");
        ArrayList<Token> sublist = new ArrayList<>(tokens.subList(2, index));
        boolean checkexp = analyzeExpression(sublist) != null;
        if(checkexp){
            expression += analyzeExpression(sublist);
        } else {
            throw new RuntimeException("Invalid While Statement, Missing Expression");
        }
        boolean closebracket = tokens.get(getIndexOf(tokens,2,")")).value.equals(")");
        if(closebracket){
            expression += tokens.get(getIndexOf(tokens,2,")")).toString();
        } else {
            throw new RuntimeException("Invalid While Statement, Missing Closing Bracket");
        }
        boolean openbracket2 = tokens.get(getIndexOf(tokens,2,"{")).value.equals("{");
        if(openbracket2){
            expression += tokens.get(getIndexOf(tokens,2,"{")).toString();
        } else {
            throw new RuntimeException("Invalid While Statement, Missing Opening Bracket");
        }
        index = getIndexOf(tokens,2,"{");
        sublist = new ArrayList<>(tokens.subList(index+1, tokens.size()-1));
        boolean checkstatements = analyzeStatements(sublist) != null;
        if(checkstatements){
            expression += analyzeStatements(sublist);
        } else {
            throw new RuntimeException("Invalid While Statement, Missing Statements");
        }
        boolean closebracket2 = tokens.get(tokens.size()-1).value.equals("}");
        if(closebracket2){
            expression += tokens.get(tokens.size()-1).toString();
        } else {
            throw new RuntimeException("Invalid While Statement, Missing Closing Bracket");
        }
        return tokenizer.genrateToken(expression, TYPE.WHILESTATEMENT).toString();
    }

    public String analyzeIfStatement(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        boolean checkif = tokens.get(0).value.equals("if");
        if(checkif){
            expression += tokens.get(0).toString();
        } else {
            throw new RuntimeException("Invalid If Statement, Missing If");
        }
        boolean openbracket = tokens.get(1).value.equals("(");
        if(openbracket){
            expression += tokens.get(1).toString();
        } else {
            throw new RuntimeException("Invalid If Statement, Missing Opening Bracket");
        }
        int index = getIndexOf(tokens,2,")");
        ArrayList<Token> sublist = new ArrayList<>(tokens.subList(2, index));
        boolean checkexp = analyzeExpression(sublist) != null;
        if(checkexp){
            expression += analyzeExpression(sublist);
        } else {
            throw new RuntimeException("Invalid If Statement, Missing Expression");
        }
        boolean closebracket = tokens.get(getIndexOf(tokens,2,")")).value.equals(")");
        if(closebracket){
            expression += tokens.get(getIndexOf(tokens,2,")")).toString();
        } else {
            throw new RuntimeException("Invalid If Statement, Missing Closing Bracket");
        }
        boolean openbracket2 = tokens.get(getIndexOf(tokens,2,"{")).value.equals("{");
        if(openbracket2){
            expression += tokens.get(getIndexOf(tokens,2,"{")).toString();
        } else {
            throw new RuntimeException("Invalid If Statement, Missing Opening Bracket");
        }
        index = getIndexOf(tokens,2,"{");
        sublist = new ArrayList<>(tokens.subList(index+1, tokens.size()-1));
        boolean checkstatements = analyzeStatements(sublist) != null;
        if(checkstatements){
            expression += analyzeStatements(sublist);
        } else {
            throw new RuntimeException("Invalid If Statement, Missing Statements");
        }
        boolean closebracket2 = tokens.get(tokens.size()-1).value.equals("}");
        if(closebracket2){
            expression += tokens.get(tokens.size()-1).toString();
        } else {
            throw new RuntimeException("Invalid If Statement, Missing Closing Bracket");
        }
        return tokenizer.genrateToken(expression, TYPE.IFSTATEMENT).toString();
    }
    public String analyzeStatement(ArrayList<Token> tokens, TYPE statementtype) throws Exception {

        String expression;
        if(statementtype==TYPE.LETSTATEMENT) {
            expression = analyzeLetStatement(tokens);
        } else if(statementtype==TYPE.WHILESTATEMENT) {
            expression = analyzeWhileStatement(tokens);
        } else if(statementtype==TYPE.IFSTATEMENT) {
            System.out.println("Analyzing If Statement");
            System.out.println(">");
            expression = analyzeIfStatement(tokens);
            System.out.println("<");
            System.out.println("Analyzed If Statement");
        } else {
            throw new RuntimeException("Invalid Statement Type");
        }
        return expression;
    }
    public String analyzeStatements(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).value.equals("if")||tokens.get(i).value.equals("while")||tokens.get(i).value.equals("let")){
                TYPE statement_type;
                if(tokens.get(i).value.equals("while")){
                    statement_type = TYPE.WHILESTATEMENT;
                } else if(tokens.get(i).value.equals("if")){
                    statement_type = TYPE.IFSTATEMENT;
                } else {
                    statement_type = TYPE.LETSTATEMENT;
                }
                ArrayList<Token> sublist;
                int index2;
                if(statement_type == TYPE.LETSTATEMENT){
                    index2 = getIndexOf(tokens, i, ";")+1;
                } else {
                    index2 = getIndexOf(tokens,i,")")+1;
                    if(tokens.get(i).value.equals("if")){
                        System.out.println(tokens.get(i));
                        System.out.println(i);
                    }
                    sublist = new ArrayList<>(tokens.subList(index2, tokens.size()));
                    index2 = returnParanthesisIndex(sublist) + 7;
                    if(tokens.get(i).value.equals("if")){
                        System.out.println(tokens.get(index2));
                        System.out.println(index2);
                    }
                }
                sublist = new ArrayList<>(tokens.subList(i, index2));
                if(tokens.get(i).value.equals("if")){
                    System.out.println(i);
                    System.out.println(index2);
                    System.out.println("here");
                    for (int j = 0; j < sublist.size(); j++) {
                        System.out.println(sublist.get(j));
                    }
                }
                expression += analyzeStatement(sublist, statement_type);
            }
        }
        return expression;
    }

    private int getIndexOf(ArrayList<Token> tokens,int startindex, String value){
        for (int i = startindex; i < tokens.size(); i++) {
            if(tokens.get(i).value.equals(value)){
                return i;
            }
        }
        return -1;
    }

    private int returnParanthesisIndex(ArrayList<Token> tokens) {
        ArrayList<Token> tokenList = tokens;
        Stack<String> stack = new Stack<>();
        int counter = 0;
        for (Token t : tokenList) {
            if (t.value.equals("(")) {
                stack.push(t.value);
            } else if (t.value.equals(")") && stack.peek().equals("(")) {
                stack.pop();
            } else if (t.value.equals("{")) {
                stack.push(t.value);
            } else if (t.value.equals("}") && stack.peek().equals("{")) {
                stack.pop();
            } else if (t.value.equals("[")) {
                stack.push(t.value);
            } else if (t.value.equals("]") && stack.peek().equals("[")) {
                stack.pop();
            } else if (t.value.equals("<")) {
                stack.push(t.value);
            } else if (t.value.equals(">") && stack.peek().equals("<")) {
                stack.pop();
            }
            if (stack.isEmpty() && counter!=0) {
                return counter;
            }
            counter+=1;
        }
        throw new RuntimeException("Invalid parentheses");
    }
}
