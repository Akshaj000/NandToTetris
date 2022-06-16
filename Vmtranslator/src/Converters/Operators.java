package Converters;

// Class containing operators

public class Operators {
    private int count = 0;

    private String base_str = """
            @SP
            AM=M-1
            D=M
            """;

    private String loop__str(String label) {
        return """
                @SP
                A=M
                M=0
                """ + "@" + "End." + count + "\n0;JMP"+"\n("+genrate_labelcount(label)+")\n"+ """
                @SP
                A=M
                M=-1
                """+"("+"End."+count+")\n"+inc_str;
    }

    private String dec_str = """
            @SP
            AM=M-1 
            """;

    private String inc_str = """
            @SP
            M=M+1         
            """;

    private String genrate_labelcount(String label){
        return label+"."+count;
    }

    public String add = base_str+dec_str+"M=M+D\n"+inc_str;

    public String sub = base_str+dec_str+"M=M-D\n"+inc_str;

    public String and = base_str+dec_str+"M=D&M\n"+inc_str;

    public String or = base_str+dec_str+"M=D|M\n"+inc_str;

    public String neg = dec_str+"M=-M\n"+inc_str;

    public String not = dec_str+"M=!M\n"+inc_str;

    public String eq(){
        this.count++;
        return (
                base_str+dec_str+"D=M-D\n@"+genrate_labelcount("EQ")+"\nD;JEQ\n"+loop__str("EQ")
        );
    }

    public String gt(){
        this.count++;
        return (
                base_str+dec_str+"D=M-D\n@"+genrate_labelcount("GT")+"\nD;JGT\n"+loop__str("GT")
        );
    }

    public String lt(){
        this.count++;
        return (
                base_str+dec_str+"D=M-D\n@"+genrate_labelcount("LT")+"\nD;JLT\n"+loop__str("LT")
        );
    }
}
