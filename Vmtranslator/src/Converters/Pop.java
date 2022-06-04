package Converters;

// Class containing pop operations

public class Pop {

    private String base_pop = """
        @SP
        M=M-1
        A=M
        D=M
        @R15
        A=M
        M=D
        """;

    private String mem_inc(String index){
        return "@"+index+"\nD=A\n";
    }

    private String get_addr = "@R15\nM=D\n";

    public String pop_local(String index){
        return mem_inc(index)+"@LCL\nD=D+M\n"+get_addr+base_pop;
    }

    public String pop_argument(String index){
        return mem_inc(index)+"@ARG\nD=D+M\n"+get_addr+base_pop;
    }

    public String pop_this(String index){
        return mem_inc(index)+"@THIS\nD=D+M\n"+get_addr+base_pop;
    }

    public String pop_that(String index){
        return mem_inc(index)+"@THAT\nD=D+M\n"+get_addr+base_pop;
    }

    public String pop_static(String filename, String index){           //TODO
        int length = filename.split("/").length;
        filename = filename.split("/")[length-1];
        filename = filename.substring(0,filename.indexOf("."));
        return "@"+filename+"."+index+"\nD=M\n"+base_pop;
    }

    public String pop_temp(String index){
        return mem_inc(index)+"@5\nD=D+A\n"+get_addr+base_pop;
    }

    public String pop_pointer(String index){
        String snip =  """
        @SP
        AM=M-1
        D=M
        """;
        if(index.equals("0")){
            snip+="@THIS\nM=D\n";
        }else if(index.equals("1")){
            snip+="@THAT\nM=D\n";
        }
        return snip;
    }


}
