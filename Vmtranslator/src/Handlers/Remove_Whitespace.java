package Handlers;

//Remove commands from a line and trim from both end

public class Remove_Whitespace {
    public static String removeWhiteSpacesAndCommands(String line){
        if (line.equals("")  || line.startsWith("//")) {
            return null;
        } else  if (line.contains("//")){
            line = line.split("//")[0].trim();
            return line;
        }
        return line;
    }
}
