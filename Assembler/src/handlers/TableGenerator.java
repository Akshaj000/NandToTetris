package handlers;

import converters.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class TableGenerator {
    public static HashMap<String, Integer> tableGenerator(ArrayList<String> data){
        HashMap<String, Integer> symbolTable = new HashMap<>();
        Pattern check_num = Pattern.compile("-?\\d+(\\.\\d+)?");
        int symbolcount = -1;
        int index = 16;
        for (int ln = 0; ln < data.size(); ln++) {
            String line = data.get(ln);
            if(line.startsWith("(")){
                symbolcount+=1;
                String symbol = line.substring(line.indexOf("(") + 1);
                symbol = symbol.substring(0, symbol.indexOf(")"));
                symbolTable.put(symbol,ln-symbolcount);
            }
        }
        for (int ln = 0; ln < data.size(); ln++) {
            String line = data.get(ln);
            if(line.startsWith("@")){
                if(!line.contains("@R")){
                    line = line.split("@")[1];
                    boolean isNum = check_num.matcher(line).matches();
                    if (!isNum){
                        if(symbolTable.get(line)==null && Constants.pre_defined_symbols.get(line)==null){
                            symbolTable.put(line,index);
                            index+=1;
                        }
                    }
                }
            }
        }

        return symbolTable;
    }
}
