package Handlers;

import Converters.Operators;
import Converters.Pop;
import Converters.Push;

// Converts the line to Assembly Language

public class ConvertToVM {
    public static String ConvertToVM(
            String operation,
            String segment,
            String index,
            String filename,
            Pop pop,
            Push push,
            Operators operator
    ){
        String codesnip = "";

        if(operation != null){
            if(segment != null && index != null){
                if (segment.equals("local")){
                    if(operation.equals("push")){
                        codesnip = push.push_local(index);
                    } else if (operation.equals("pop")){
                        codesnip = pop.pop_local(index);
                    }
                } else if(segment.equals("argument")){
                    if(operation.equals("push")){
                        codesnip = push.push_argument(index);
                    } else if (operation.equals("pop")){
                        codesnip = pop.pop_argument(index);
                    }
                } else if(segment.equals("this")){
                    if(operation.equals("push")){
                        codesnip = push.push_this(index);
                    } else if (operation.equals("pop")){
                        codesnip = pop.pop_this(index);
                    }
                } else if(segment.equals("that")){
                    if(operation.equals("push")){
                        codesnip = push.push_that(index);
                    } else if (operation.equals("pop")){
                        codesnip = pop.pop_that(index);
                    }
                } else if(segment.equals("temp")){
                    if(operation.equals("push")){
                        codesnip = push.push_temp(index);
                    } else if (operation.equals("pop")){
                        codesnip = pop.pop_temp(index);
                    }
                } else if(segment.equals("constant")){
                    if(operation.equals("push")){
                        codesnip = push.push_const(index);
                    }
                } else if(segment.equals("pointer")){
                    if(operation.equals("push")){
                        codesnip = push.push_pointer(index);
                    } else if (operation.equals("pop")){
                        codesnip = pop.pop_pointer(index);
                    }
                } else if(segment.equals("static")) {
                    if (operation.equals("push")) {
                        codesnip = push.push_static(filename,index);
                    } else if (operation.equals("pop")) {
                        codesnip = pop.pop_static(filename,index);
                    }
                }
            } else {
                if (operation.equals("add")){
                    codesnip = operator.add;
                } else if (operation.equals("sub")){
                    codesnip = operator.sub;
                } else if (operation.equals("and")){
                    codesnip = operator.and;
                } else if (operation.equals("or")){
                    codesnip = operator.or;
                } else if (operation.equals("neg")){
                    codesnip = operator.neg;
                } else if (operation.equals("not")){
                    codesnip = operator.not;
                } else if (operation.equals("eq")){
                    codesnip = operator.eq();
                } else if (operation.equals("gt")){
                    codesnip = operator.gt();
                } else if (operation.equals("lt")){
                    codesnip = operator.lt();
                }
            }
        }
        return codesnip;
    }
}
