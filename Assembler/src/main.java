import converters.ConvertAInstruction;
import converters.ConvertCInstruction;
import handlers.NoWhiteSpace;
import handlers.TableGenerator;
import handlers.FileHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter filename : ");
        String filename = sc.next();
        ArrayList<String> data = FileHandler.readFile(filename);
        NoWhiteSpace.removeWhiteSpacesAndCommands(data);
        HashMap<String,Integer> symbolTable = TableGenerator.tableGenerator(data);
        ArrayList<String> instructions =  new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if(!data.get(i).contains("(")) {
                String instruction;
                if (data.get(i).startsWith("@")) {
                    instruction = ConvertAInstruction.convertAInstructionToBinary(data.get(i), symbolTable);
                } else {
                    instruction = ConvertCInstruction.convertCInstructionToBinary(data.get(i));
                }
                if (instruction != null) {
                    System.out.println(data.get(i)+"  :  "+instruction);
                    instructions.add(instruction);
                }
            }
        }
        FileHandler.writeToFile(filename+"_generated_instructions.hack",instructions);

    }
}
