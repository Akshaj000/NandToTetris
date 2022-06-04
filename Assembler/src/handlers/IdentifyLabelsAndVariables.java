package handlers;

import java.util.ArrayList;
import java.util.Iterator;

public class IdentifyLabelsAndVariables {
    public static ArrayList<String> identifyLabelsAndVariables(ArrayList<String> data){
        ArrayList<String> variables_and_labels = new ArrayList<>();
        Iterator<String> itr = data.iterator();
        while (itr.hasNext()) {
            String line = itr.next();
            if (line.startsWith("(") || line.startsWith("@")){
                variables_and_labels.add(line);
            }
        }
        return variables_and_labels;
    }
}
