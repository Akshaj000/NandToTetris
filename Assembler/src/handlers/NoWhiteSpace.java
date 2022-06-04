package handlers;

import java.util.ArrayList;
import java.util.Iterator;

public class NoWhiteSpace {
    public static void removeWhiteSpacesAndCommands(ArrayList<String> data){
        Iterator<String> itr = data.iterator();
        int index = 0;
        while (itr.hasNext()) {
            String line = itr.next();
            if (line.equals("")  || line.startsWith("//")) {
                itr.remove();
                index-=1;
            } else  if (line.contains("//")){
                line = line.split("//")[0].trim();
                data.set(index,line);
            }else{
                line = line.replaceAll("\\s+", "");
                data.set(index,line);
            }
            index+=1;
        }
    }
}
