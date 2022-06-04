package converters;

import java.util.Map;

import static java.util.Map.entry;

public class Constants {
    public static Map<String, Integer> pre_defined_symbols = Map.ofEntries(
            entry("SCREEN",16384),
            entry("KBD",24576),
            entry("SP",0),
            entry("LCL",1),
            entry("ARG",2),
            entry("THIS",3),
            entry("THAT",4)
    );

    static Map<String, String> comp = Map.ofEntries(
            entry("0","101010"),
            entry("1","111111"),
            entry("-1","111010"),
            entry("D","001100"),
            entry("A","110000"),
            entry("!D","001101"),
            entry("!A","110001"),
            entry("-D","001111"),
            entry("-A","110011"),
            entry("D+1","011111"),
            entry("A+1","110111"),
            entry("D-1","001110"),
            entry("A-1","110010"),
            entry("D+A","000010"),
            entry("D-A","010011"),
            entry("A-D","000111"),
            entry("D&A","000000"),
            entry("D|A","010101"),
            entry("M","110000"),
            entry("!M","110001"),
            entry("-M","110011"),
            entry("M+1","110111"),
            entry("M-1","110010"),
            entry("D+M","000010"),
            entry("D-M","010011"),
            entry("M-D","000111"),
            entry("D&M","000000"),
            entry("D|M","010101")
    );

    static Map<String, String> dest = Map.ofEntries(
            entry("null","000"),
            entry("M","001"),
            entry("D","010"),
            entry("MD","011"),
            entry("A","100"),
            entry("AM","101"),
            entry("AD","110"),
            entry("AMD","111")
    );

    static Map<String, String> jump = Map.ofEntries(
            entry("null","000"),
            entry("JGT","001"),
            entry("JEQ","010"),
            entry("JGE","011"),
            entry("JLT","100"),
            entry("JNE","101"),
            entry("JLE","110"),
            entry("JMP","111")
    );
}
