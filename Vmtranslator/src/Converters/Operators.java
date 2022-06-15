package Converters;

// Class containing operators

public class Operators {

    private String base_str = """
            @SP
            AM=M-1
            D=M
            """;

    private String base_loop_front_str = """
            A=A-1
            D=M-D
            M=-1
            """;

    private String base_loop_rear_str = """
            @SP
            A=M-1
            M=0
            """;

    private String dec_str = """
            @SP
            AM=M-1 
            """;

    private String inc_str = """
            @SP
            M=M+1         
            """;

    private int count = 0;
    private String genrate_labelcount(String label){
        return label+"_"+count;
    }

    public String add = base_str+dec_str+"M=M+D\n"+inc_str;

    public String sub = base_str+dec_str+"M=M-D\n"+inc_str;

    public String and = base_str+dec_str+"M=D&M\n"+inc_str;

    public String or = base_str+dec_str+"M=D|M\n"+inc_str;

    public String neg = base_str+dec_str+"M=-M\n"+inc_str;

    public String not = base_str+dec_str+"M=!M\n"+inc_str;

    public String eq(){
        this.count++;
        return (
                base_str + base_loop_front_str + "@" + genrate_labelcount("EQ") +
                "\nD;JEQ\n" + base_loop_rear_str + "(" + genrate_labelcount("EQ") + ")\n"
        );
    }

    public String gt(){
        this.count++;
        return (
                base_str + base_loop_front_str + "@" + genrate_labelcount("GT") +
                "\nD;JGT\n" + base_loop_rear_str + "(" + genrate_labelcount("GT") + ")\n"
        );
    }

    public String lt(){
        this.count++;
        return (
                base_str + base_loop_front_str + "@" + genrate_labelcount("LT") +
                "\nD;JLT\n" + base_loop_rear_str + "(" + genrate_labelcount("LT") + ")\n"
        );
    }
}
