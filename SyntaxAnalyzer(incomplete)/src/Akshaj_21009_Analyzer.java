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

    private String analyzeSymbol(Token token) throws Exception {
        String[] symbols = new String[]{"{", "}", "(", ")", "[", "]", ".", ",", ";", "+", "-", "*", "/", "&", "|", "<", ">", "=", "~"};
        for (String symbol : symbols) {
            if (symbol.trim().equals(token.value)) {
                return tokenizer.genrateToken(token.toString(), TYPE.SYMBOL).toString();
            }
        }
        throw new RuntimeException("Invalid Symbol");
    }

    private String analyzeConstant(Token token){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if(pattern.matcher(token.value).matches()){
            return tokenizer.genrateToken(token.toString(), TYPE.CONSTANT).toString();
        }
        throw new RuntimeException("Invalid Constant "+token.value);
    }

    private String analyzeType(Token token){
        String[] types = new String[]{"int", "char", "boolean", "void"};
        for (String type : types) {
            if (type.trim().equals(token.value)) {
                return tokenizer.genrateToken(token.toString(), TYPE.KEYWORD).toString();
            }
        }
        throw new RuntimeException("Invalid Type");
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
        } else if (token.value.equals("true")||token.value.equals("false")||token.value.equals("boolean")||token.value.equals("null")||token.value.equals("this")){
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


    public String analyzeVarDec(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        boolean checkvar = tokens.get(0).value.equals("var");
        if(checkvar){
            expression += tokens.get(0).toString();
        } else {
            throw new RuntimeException("Invalid Var Declaration, Missing Var");
        }
        boolean checktype = analyzeVarName(tokens.get(1)) != null;
        if(checktype){
            expression += analyzeVarName(tokens.get(1));
        } else {
            throw new RuntimeException("Invalid Var Declaration, Missing Type");
        }
        boolean checkvarname = analyzeVarName(tokens.get(2)) != null;
        if(checkvarname){
            expression += analyzeVarName(tokens.get(2));
        } else {
            throw new RuntimeException("Invalid Var Declaration, Missing Variable Name");
        }
        boolean checkclosingtag = tokens.get(tokens.size()-1).value.equals(";");
        if(checkclosingtag){
            expression += tokens.get(tokens.size()-1).toString();
        } else {
            throw new RuntimeException("Invalid Var Declaration, Missing Closing Tag");
        }
        return tokenizer.genrateToken(expression, TYPE.VARIABLEDECLARATION).toString();
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
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i).value);
        }
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
        int index2 = getIndexOf(tokens,2,"else");
        if(index2 != -1){
            sublist = new ArrayList<>(tokens.subList(index+1, index2));
        } else {
            sublist = new ArrayList<>(tokens.subList(index+1, tokens.size()-1));
        }
        boolean checkstatements = analyzeStatements(sublist) != null;
        if(checkstatements){
            expression += analyzeStatements(sublist);
        } else {
            throw new RuntimeException("Invalid If Statement, Missing Statements");
        }
        if(index2 != -1){
            boolean closebracket2 = tokens.get(index2-1).value.equals("}");
            if(closebracket2){
                expression += tokens.get(index2-1).toString();
            } else {
                throw new RuntimeException("Invalid If Statement, Missing Closing Bracket");
            }
            boolean elseif = tokens.get(index2).value.equals("else");
            if(elseif){
                expression += tokens.get(index2).toString();
            } else {
                throw new RuntimeException("Invalid If Statement, Missing Else");
            }
            boolean openbracket3 = tokens.get(index2+1).value.equals("{");
            if(openbracket3){
                expression += tokens.get(index2+1).toString();
            } else {
                throw new RuntimeException("Invalid If Statement, Missing Opening Bracket");
            }
            index = getIndexOf(tokens,index2+1,"{");
            sublist = new ArrayList<>(tokens.subList(index+1, tokens.size()-1));
            boolean checkstatements2 = analyzeStatements(sublist) != null;
            if(checkstatements2){
                expression += analyzeStatements(sublist);
            } else {
                throw new RuntimeException("Invalid If Statement, Missing Statements");
            }
            boolean closebracket3 = tokens.get(tokens.size()-1).value.equals("}");
            if(closebracket3){
                expression += tokens.get(tokens.size()-1).toString();
            } else {
                throw new RuntimeException("Invalid If Statement, Missing Closing Bracket");
            }
            return tokenizer.genrateToken(expression, TYPE.IFSTATEMENT).toString();
        }
        boolean closebracket2 = tokens.get(tokens.size()-1).value.equals("}");
        if(closebracket2){
            expression += tokens.get(tokens.size()-1).toString();
        } else {
            throw new RuntimeException("Invalid If Statement, Missing Closing Bracket");
        }
        return tokenizer.genrateToken(expression, TYPE.IFSTATEMENT).toString();
    }


    public String analyzeExpressionList(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        for (int i = 0; i < tokens.size(); i++) {
            ArrayList<Token> sublist = new ArrayList<>(tokens.subList(i, i+1));
            boolean checkexp = analyzeExpression(sublist) != null;
            if(checkexp){
                expression += analyzeExpression(sublist);
            } else {
                throw new RuntimeException("Invalid Expression List, Missing Expression");
            }
            i+=1;
        }
        return tokenizer.genrateToken(expression, TYPE.EXPRESSION_LIST).toString();
    }

    public String analyzeDoStatement(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        boolean checkdo = tokens.get(0).value.equals("do");
        if(checkdo){
            expression += tokens.get(0).toString();
        } else {
            throw new RuntimeException("Invalid Do Statement, Missing Do");
        }
        boolean checkvarname = analyzeVarName(tokens.get(1)) != null;
        if(checkvarname){
            expression += analyzeVarName(tokens.get(1));
        } else {
            throw new RuntimeException("Invalid Do Statement, Missing Variable Name");
        }
        boolean firstdeclare = analyzeVarName(tokens.get(1)) != null;
        if(firstdeclare){
            expression += analyzeVarName(tokens.get(1));
        } else {
            throw new RuntimeException("Invalid Do Statement, Missing Variable Name");
        }
        int index = 1;
        if(tokens.get(2).value.equals("(")){
            boolean openbracket = tokens.get(2).value.equals("(");
            if(openbracket){
                expression += tokens.get(2).toString();
            } else {
                throw new RuntimeException("Invalid Do Statement, Missing Opening Bracket");
            }
            index = 3;
        } else {
           boolean checkdot = tokens.get(2).value.equals(".");
              if(checkdot){
                expression += tokens.get(2).toString();
              } else {
                throw new RuntimeException("Invalid Do Statement, Missing Dot");
              }
              boolean checkvarname2 = analyzeVarName(tokens.get(3)) != null;
            if(checkvarname2){
                expression += analyzeVarName(tokens.get(3));
            } else {
                throw new RuntimeException("Invalid Do Statement, Missing Variable Name");
            }
            boolean checkopenbracket = tokens.get(4).value.equals("(");
            if(checkopenbracket){
                expression += tokens.get(4).toString();
            } else {
                throw new RuntimeException("Invalid Do Statement, Missing Opening Bracket");
            }
            index = 5;
        }
        if(!tokens.get(index).value.equals(")")){
            int closetagindex = getIndexOf(tokens,index,")");
            ArrayList<Token> sublist = new ArrayList<>(tokens.subList(index, closetagindex));
            index+=sublist.size();
            boolean checkexpressionlist = analyzeExpressionList(sublist) != null;
            if(checkexpressionlist){
                expression += analyzeExpressionList(sublist);
            }else {
                throw new RuntimeException("Invalid Do Statement, Missing Expression List");
            }
        }
        boolean closebracket = tokens.get(index).value.equals(")");
        if(closebracket){
            expression += tokens.get(index).toString();
        } else {
            throw new RuntimeException("Invalid Do Statement, Missing Closing Bracket");
        }
        boolean semicolon = tokens.get(index+1).value.equals(";");
        if(semicolon){
            expression += tokens.get(index+1).toString();
        } else {
            throw new RuntimeException("Invalid Do Statement, Missing Semicolon");
        }
        return tokenizer.genrateToken(expression, TYPE.DOSTATEMENT).toString();
    }
    public String analyzeStatement(ArrayList<Token> tokens, TYPE statementtype) throws Exception {

        String expression;
        if(statementtype==TYPE.LETSTATEMENT) {
            expression = analyzeLetStatement(tokens);
        } else if(statementtype==TYPE.WHILESTATEMENT) {
            expression = analyzeWhileStatement(tokens);
        } else if(statementtype==TYPE.IFSTATEMENT) {
            expression = analyzeIfStatement(tokens);
        } else if (statementtype==TYPE.DOSTATEMENT) {
            expression = analyzeDoStatement(tokens);
        } else {
            throw new RuntimeException("Invalid Statement Type");
        }
        return expression;
    }
    public String analyzeStatements(ArrayList<Token> tokens) throws Exception {
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i).toString());
        }
        String expression = "";
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).value.equals("if")||tokens.get(i).value.equals("while")||tokens.get(i).value.equals("let")||tokens.get(i).value.equals("do")||tokens.get(i).value.equals("var")){
                TYPE statement_type;
                if(tokens.get(i).value.equals("var")){
                    ArrayList<Token> sublist = new ArrayList<>(tokens.subList(i, i+4));
                    expression = analyzeVarDec(sublist);
                }else {
                    if (tokens.get(i).value.equals("while")) {
                        statement_type = TYPE.WHILESTATEMENT;
                    } else if (tokens.get(i).value.equals("if")) {
                        statement_type = TYPE.IFSTATEMENT;
                    } else if (tokens.get(i).value.equals("do")) {
                        statement_type = TYPE.DOSTATEMENT;
                    } else {
                        statement_type = TYPE.LETSTATEMENT;
                    }
                    ArrayList<Token> sublist;
                    int index2;
                    if (statement_type == TYPE.LETSTATEMENT || statement_type == TYPE.DOSTATEMENT) {
                        index2 = getIndexOf(tokens, i, ";") + 1;
                    } else {
                        index2 = getIndexOf(tokens, i, ")") + 1;
                        sublist = new ArrayList<>(tokens.subList(index2, tokens.size()));
                        index2 = returnParanthesisIndex(sublist) + i + 8;
                    }
                    sublist = new ArrayList<>(tokens.subList(i, index2));
                    expression += analyzeStatement(sublist, statement_type);
                    i = index2-2;
                }
            }
        }
        return expression;
    }


    public String analyzeSubroutineDec(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        if(tokens.get(0).value.equals("constructor")){
            expression += tokens.get(0).toString();
            boolean constructorname = analyzeVarName(tokens.get(1)) != null;
            if(constructorname){
                expression += analyzeVarName(tokens.get(1));
            } else {
                throw new RuntimeException("Invalid Constructor Declaration, Missing Constructor Name");
            }
            boolean newkeyword = tokens.get(2).value.equals("new");
            if(newkeyword){
                expression += tokens.get(2).toString();
            } else {
                throw new RuntimeException("Invalid Constructor Declaration, Missing New Keyword");
            }
            boolean openbracket = tokens.get(3).value.equals("(");
            if(openbracket){
                expression += tokens.get(3).toString();
            } else {
                throw new RuntimeException("Invalid Constructor Declaration, Missing Opening Bracket");
            }
            int index = getIndexOf(tokens,4,")");
            ArrayList<Token> sublist = new ArrayList<>(tokens.subList(4, index));
            boolean checkparameterlist = analyzeParameterList(sublist) != null;
            if(checkparameterlist){
                expression += analyzeParameterList(sublist);
            } else {
                throw new RuntimeException("Invalid Constructor Declaration, Missing Parameter List");
            }
            boolean closebracket = tokens.get(index).value.equals(")");
            if(closebracket){
                expression += tokens.get(index).toString();
            } else {
                throw new RuntimeException("Invalid Constructor Declaration, Missing Closing Bracket");
            }
            boolean opencurly = tokens.get(index+1).value.equals("{");
            if(opencurly){
                expression += tokens.get(index+1).toString();
            } else {
                throw new RuntimeException("Invalid Constructor Declaration, Missing Opening Curly Bracket");
            }
            int index2 = getIndexOf(tokens,index+2,"}");
            ArrayList<Token> sublist2 = new ArrayList<>(tokens.subList(index+2, index2));
            boolean checkstatements = analyzeStatements(sublist2) != null;
            if(checkstatements){
                expression += analyzeStatements(sublist2);
            } else {
                throw new RuntimeException("Invalid Constructor Declaration, Missing Statements");
            }
            boolean closecurly = tokens.get(index2).value.equals("}");
            if(closecurly){
                expression += tokens.get(index2).toString();
            } else {
                throw new RuntimeException("Invalid Constructor Declaration, Missing Closing Curly Bracket");
            }

        } else if(tokens.get(0).value.equals("function") || tokens.get(0).value.equals("method")) {
            expression += tokens.get(0).toString();
            boolean checkvoid = tokens.get(1).value.equals("void");
            if (checkvoid) {
                expression += tokens.get(1).toString();
            } else {
                throw new RuntimeException("Invalid Subroutine Declaration, Missing Void");
            }
            boolean checkidentifier = analyzeTerm(tokens.get(2)) != null;
            if (checkidentifier) {
                expression += analyzeTerm(tokens.get(2));
            } else {
                throw new RuntimeException("Invalid Subroutine Declaration, Missing Identifier");
            }
            boolean checkopenbracket = tokens.get(3).value.equals("(");
            if (checkopenbracket) {
                expression += tokens.get(3).toString();
            } else {
                throw new RuntimeException("Invalid Subroutine Declaration, Missing Opening Bracket");
            }
            int index = getIndexOf(tokens,4,")");
            ArrayList<Token> sublist = new ArrayList<>(tokens.subList(4, index));
            boolean checkparameterlist = analyzeParameterList(sublist) != null;
            if (checkparameterlist) {
                expression += analyzeParameterList(sublist);
            } else {
                throw new RuntimeException("Invalid Subroutine Declaration, Missing Parameter List");
            }
            boolean checkclosebracket = tokens.get(index).value.equals(")");
            if (checkclosebracket) {
                expression += tokens.get(index).toString();
            } else {
                throw new RuntimeException("Invalid Subroutine Declaration, Missing Closing Bracket");
            }
            sublist = new ArrayList<>(tokens.subList(index+1, tokens.size()));
            boolean checksubroutinebody = analyzeSubroutineBody(sublist) != null;
            if (checksubroutinebody) {
                expression += analyzeSubroutineBody(sublist);
            } else {
                throw new RuntimeException("Invalid Subroutine Declaration, Missing Subroutine Body");
            }
        } else {
            throw new RuntimeException("Invalid Subroutine Declaration, Missing Subroutine Type");
        }
        return tokenizer.genrateToken(expression, TYPE.CLASSVARDEC).toString();
    }

    public String analyzeParameterList(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        for (int i = 0; i < tokens.size(); i++) {
            if(tokens.get(i).value.equals("int")||tokens.get(i).value.equals("char")||tokens.get(i).value.equals("boolean")){
                boolean checkidentifier = analyzeTerm(tokens.get(i+1)) != null;
                if(checkidentifier){
                    expression += analyzeTerm(tokens.get(i+1));
                } else {
                    throw new RuntimeException("Invalid Parameter List, Missing Identifier");
                }
                if(i+2<tokens.size()){
                    boolean checkcomma = tokens.get(i+2).value.equals(",");
                    if(checkcomma){
                        expression += tokens.get(i+2).toString();
                    } else {
                        throw new RuntimeException("Invalid Parameter List, Missing Comma");
                    }
                }
            }
            i=i+3;
        }
        return tokenizer.genrateToken(expression, TYPE.PARAMETERLIST).toString();
    }

    public String analyzeSubroutineBody(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        boolean checkopenbracket = tokens.get(0).value.equals("{");
        if (checkopenbracket){
            expression += tokens.get(0).toString();
        } else {
            throw new RuntimeException("Invalid Subroutine Body, Missing Opening Bracket");
        }
        ArrayList<Token> sublist = new ArrayList<>(tokens.subList(1, tokens.size()-1));
        boolean checkstatement = analyzeStatements(sublist) != null;
        if(checkstatement){
            expression += analyzeStatements(sublist);
        } else {
            throw new RuntimeException("Invalid Subroutine Body, Missing Statement");
        }
        boolean checkclosebracket = tokens.get(tokens.size()-1).value.equals("}");
        if(checkclosebracket){
            expression += tokens.get(tokens.size()-1).toString();
        } else {
            throw new RuntimeException("Invalid Subroutine Body, Missing Closing Bracket");
        }
        return tokenizer.genrateToken(expression, TYPE.SUBROUTINEBODY).toString();
    }

    public String analyzeClassVarDec(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        boolean checkstatic = tokens.get(0).value.equals("static") || tokens.get(0).value.equals("field");
        if(checkstatic){
            expression += tokens.get(0).toString();
        } else {
            throw new RuntimeException("Invalid Class Variable Declaration, Missing Static or Field");
        }
        int index = getIndexOf(tokens,1,";");
        if(index>3) {
            for (int i = 1; i < index; i++) {
                boolean checkidentifier = analyzeType(tokens.get(i)) != null;
                if (checkidentifier) {
                    expression += analyzeType(tokens.get(i));
                } else {
                    throw new RuntimeException("Invalid Class Variable Declaration, Missing Identifier");
                }
                boolean varname = analyzeVarName(tokens.get(i+1)) != null;
                if (varname) {
                    expression += analyzeTerm(tokens.get(i+1));
                } else {
                    throw new RuntimeException("Invalid Class Variable Declaration, Missing Identifier");
                }
                if (i + 1 < tokens.size()) {
                    boolean checkcomma = tokens.get(i + 2).value.equals(",");
                    if (checkcomma) {
                        expression += tokens.get(i + 2).toString();
                    } else {
                        throw new RuntimeException("Invalid Class Variable Declaration, Missing Comma");
                    }
                }
                i = i + 3;
            }
        }else {
            boolean checkidentifier = analyzeType(tokens.get(1)) != null;
            if (checkidentifier) {
                expression += analyzeType(tokens.get(1));
            } else {
                throw new RuntimeException("Invalid Class Variable Declaration, Missing Identifier");
            }
            boolean varname = analyzeVarName(tokens.get(2)) != null;
            if (varname) {
                expression += analyzeVarName(tokens.get(2));
            } else {
                throw new RuntimeException("Invalid Class Variable Declaration, Missing Variable Name");
            }
        }
        boolean checksemicolon = tokens.get(index).value.equals(";");
        if(checksemicolon){
            expression += tokens.get(index).toString();
        } else {
            throw new RuntimeException("Invalid Class Variable Declaration, Missing Semicolon");
        }
        return tokenizer.genrateToken(expression, TYPE.CLASSVARDEC).toString();

    }

    public String analyzeClass(ArrayList<Token> tokens) throws Exception {
        String expression = "";
        boolean checkclass = tokens.get(0).value.equals("class");
        if(checkclass){
            expression += tokens.get(0).toString();
        } else {
            throw new RuntimeException("Invalid Class, Missing Class");
        }
        boolean checkidentifier = analyzeTerm(tokens.get(1)) != null;
        if(checkidentifier){
            expression += analyzeTerm(tokens.get(1));
        } else {
            throw new RuntimeException("Invalid Class, Missing Identifier");
        }
        boolean checkopenbracket = tokens.get(2).value.equals("{");
        if(checkopenbracket){
            expression += tokens.get(2).toString();
        } else {
            throw new RuntimeException("Invalid Class, Missing Opening Bracket");
        }
        ArrayList<Token> sublist = new ArrayList<>(tokens.subList(2, tokens.size()));
        int closetagindex  = returnParanthesisIndex(sublist)+2;
        for (int i = 3; i < tokens.size(); i++) {
            if(tokens.get(i).value.equals("static")||tokens.get(i).value.equals("field")){
                int index = getIndexOf(tokens,i,";");
                sublist = new ArrayList<>(tokens.subList(i, index+1));
                boolean checkclassvardec = analyzeClassVarDec(sublist) != null;
                if(checkclassvardec){
                    expression += analyzeClassVarDec(sublist);
                } else {
                    throw new RuntimeException("Invalid Class, Missing Class Variable Declaration");
                }
            } else if(tokens.get(i).value.equals("constructor")||tokens.get(i).value.equals("function")||tokens.get(i).value.equals("method")){
                int index = getIndexOf(tokens,i,"{");
                sublist = new ArrayList<>(tokens.subList(index, tokens.size()));
                int index2 = returnParanthesisIndex(sublist)+index+1;
                sublist = new ArrayList<>(tokens.subList(i, index2));
                boolean checksubroutine = analyzeSubroutineDec(sublist) != null;
                if(checksubroutine){
                    expression += analyzeSubroutineDec(sublist);
                } else {
                    throw new RuntimeException("Invalid Class, Missing Subroutine Declaration");
                }
            }
        }
        boolean checkclosebracket = tokens.get(closetagindex).value.equals("}");
        if(checkclosebracket){
            expression += tokens.get(closetagindex).toString();
        } else {
            throw new RuntimeException("Invalid Class, Missing Closing Bracket");
        }
        return tokenizer.genrateToken(expression, TYPE.CLASS).toString();
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
