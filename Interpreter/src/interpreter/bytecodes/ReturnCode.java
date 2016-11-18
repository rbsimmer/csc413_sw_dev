package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * RETURN <funcname>; Return from the current function; <funcname> is used as 
 * a comment to indicate the current function
 * RETURN is generated for intrinsic functions
 */
public class ReturnCode extends ByteCode{
    protected String funcName;
    protected int retVal;
    
    public void init(Vector<String> args){
        if(args.size()>0){
            funcName = args.get(0);
        }else{
            funcName = "";
        }
    }
    
    public void execute(VirtualMachine vm){ 
        vm.setPC(vm.popReturnAddress());
        vm.popFrameRunStack();
        retVal = vm.peekRunStack();
    }
    
    public String toString(){
        int n = funcName.indexOf("<");
        String temp;
        if(n<0){
            temp = funcName;
        }else{
            temp = funcName.substring(0,n);
        }       
        return "RETURN " + funcName + "    exit " + temp + ": " + retVal;
    }
}
