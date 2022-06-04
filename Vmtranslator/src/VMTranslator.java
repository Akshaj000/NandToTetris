import Handlers.Parser;

import java.util.Scanner;

//Driver class that calls Parser

public class VMTranslator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        Parser.Parse(filename);
    }
}
