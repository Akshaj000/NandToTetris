package converters;

import java.util.*;
import java.util.regex.Pattern;

public class ConvertAInstruction {


    public static String getBinary(String num) {
        num = Integer.toBinaryString(Integer.parseInt(num));
        int len = num.length();
        String binary = "0";
        for (int i = 0; i < 15-len; i++) {
            binary+="0";
        }
        binary+=num;
        return(binary);
    }

    public static String convertAInstructionToBinary(String data, HashMap<String,Integer> labels){
        Pattern check_num = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (data.startsWith("@")) {
            if (data.startsWith("@R")) {
                String line = data.split("@R")[1];
                if(Integer.parseInt(line)<16){
                    boolean isNum = check_num.matcher(line).matches();
                    if (isNum) {
                        String binary = getBinary(line);
                        return binary;
                    }
                }
            }else {
                String line = data.split("@")[1];
                boolean isNum = check_num.matcher(line).matches();
                if (isNum){
                    String binary = getBinary(line);
                    return binary;
                } else {
                    String label = String.valueOf(labels.get(line));
                    if(!Objects.equals(label, "null")){
                        return getBinary(label);
                    }
                    label = String.valueOf(Constants.pre_defined_symbols.get(line));
                    if(!Objects.equals(label, "null")){
                        return getBinary(label);
                    }
                }
            }


        }
        return null;
    }
}
