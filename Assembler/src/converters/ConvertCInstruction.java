package converters;

public class ConvertCInstruction {
    public static String convertCInstructionToBinary(String data){
        String comp = "0", dest = "null", jump = "null";
        String c_instruction = "111";
        if(data.contains(";") && data.contains("=")){
            String[] line = data.split("=");
            dest = line[0].trim();
            line = line[1].split(";");
            comp = line[0].trim();
            jump = line[1].trim();
        }
        else if(data.contains("=")){
            String[] line = data.split("=");
            dest = line[0].trim();
            comp = line[1].trim();
        }
        else if(data.contains(";")){
            String[] line = data.split(";");
            comp = line[0].trim();
            jump = line[1].trim();
        }
        if(comp.contains("M")){
            c_instruction+="1";
        }else{
            c_instruction+="0";
        }
        comp = Constants.comp.get(comp);
        dest = Constants.dest.get(dest);
        jump = Constants.jump.get(jump);
        c_instruction+=comp;
        c_instruction+=dest;
        c_instruction+=jump;
        return c_instruction;
    }
}
