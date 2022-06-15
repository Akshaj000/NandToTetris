package Converters;

// Class containing push operations

public class Push{

    private String base= """
        @SP
        A=M
        M=D
        @SP
        M=M+1
        """;

    private String memory_inc(String index){
        return "@"+index+"\nD=A\n";
    }

    public String push_local(String index){
        String inc = memory_inc(index);
        return inc+"@LCL\nD=D+M\nA=D\nD=M\n"+base;
    }

    public String push_argument(String index){
        String inc = memory_inc(index);
        return inc+"@ARG\nD=D+M\nA=D\nD=M\n"+base;
    }

    public String push_this(String index){
        String inc = memory_inc(index);
        return inc+"@THIS\nD=D+M\nA=D\nD=M\n"+base;
    }

    public String push_that(String index){
        String inc = memory_inc(index);
        return inc+"@THAT\nD=D+M\nA=D\nD=M\n"+base;
    }

    public String push_temp(String index){
        String inc = memory_inc(index);
        return inc+"@5\nD=D+A\nA=D\nD=M\n"+base;
    }

    public String push_const(String constant){
        return "@"+constant+"\nD=A\n"+base;
    }

    public String push_static(String filename, String index){    //TODO
        int length = filename.split("/").length;
        filename = filename.split("/")[length-1];
        filename = filename.substring(0,filename.indexOf("."));
        return "@"+filename+"."+ index+"\nD=A\n"+base;
    }

    public String push_pointer(String constant){
        String snip = "";
        if (constant.equals("0")){
            snip = "@THIS\nD=M\n";
        } else if (constant.equals("1")){
            snip = "@THAT\nD=M\n";
        }
        snip+=base;
        return snip;
    }
}
