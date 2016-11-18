package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * ARGS n; Used prior to calling a function:
 *  n = # of args
 * this instruction is immediately followed by the CALL instruction:
 * the function has n args so ARGS n instructs the interpreter to set up a new
 * frame n down from the top, so it will include the arguments
 */
public class ArgsCode extends ByteCode{
    private int n;  //the number of arguments in the new frame
    
    public void init(Vector<String> args){
        n = Integer.parseInt(args.get(0));
    }
    
    public void execute(VirtualMachine vm){
        vm.newFrameRunStack(n);        
    }
    
    public String toString(){
        return "ARGS " + n;
    }
}
