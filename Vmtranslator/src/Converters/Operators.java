package Converters;

// Class containing operators

public class Operators {

    private String base_str = """
        @SP
        AM=M-1
        D=M
        @SP
        AM=M-1
        """;

    private String inc_str = """
        @SP
        M=M+1         
        """;

    public String add = base_str+"M=M+D\n"+inc_str;

    public String sub = base_str+"M=M-D\n"+inc_str;

    public String and = base_str+"M=D&M\n"+inc_str;

    public String or = base_str+"M=D|M\n"+inc_str;

    public String neg = "@SP\nAM=M-1\nM=-M\n"+inc_str;
}
