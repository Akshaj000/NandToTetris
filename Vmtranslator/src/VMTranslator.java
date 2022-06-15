import Handlers.Parser;

import java.util.Scanner;

//Driver class that calls Parser

public class VMTranslator {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String filename = sc.nextLine();
//        Parser.Parse(filename);
        System.out.println(mysr_func(255));
    }
    static int mysr_func(int a){
        if(a==256){
            return 3;
        }
        return 1+2*mysr_func(a*4);
    }
}

