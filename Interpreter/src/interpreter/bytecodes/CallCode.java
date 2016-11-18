package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * CALL <funcname> - transfer control to the indicated function
 */
public class CallCode extends BranchCode{
    protected String funcName;
    protected int targetAddress;
    protected int value;
    
    public void init(Vector<String> args){
        funcName = args.get(0);        
    }
    
    public void execute(VirtualMachine vm){
        vm.pushReturnAddress(vm.getPC());
        vm.setPC(targetAddress);
        value = vm.peekRunStack();
    }
    
    public String toString(){
        int n = funcName.indexOf("<");
        String temp;
        if(n<0){
            temp = funcName;
        }else{
            temp = funcName.substring(0,n);
        }        
        return "CALL " + funcName + "    " + temp + "(" + value + ")";
    }
    
    public int getTargetAddress(){
        return targetAddress;
    }
    
    public void setTargetAddress(int n){
        targetAddress = n;
    }
    
    public String getLabel(){
        return funcName;
    }
}
