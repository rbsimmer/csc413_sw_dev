package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * LOAD n <id>; push the value in the slot which is offset n from the start
 * of the frame onto the top of the stack; <id> is used as a comment, it's the 
 * variable name from which the data is loaded
 */
public class LoadCode extends ByteCode{
    private int n;
    private String id;
    
    public void init(Vector<String> args){
        n = Integer.parseInt(args.get(0));
        id = args.get(1);
    }
    
    public void execute(VirtualMachine vm){
        vm.loadRunStack(n);
    }
    
    public String toString(){
        return "LOAD " + n + " " + id + "    <load " + id + ">";
    }
}
