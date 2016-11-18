package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * FALSEBRANCH <label> - pop the top of the stack; if it's false (i.e. 0) then 
 * branch to <label> else execute the next bytecode
 */
public class FalseBranchCode extends BranchCode{
    private String label;
    private int targetAddress;
    
    public void init(Vector<String> args){
        label = args.get(0);
    }
    
    public void execute(VirtualMachine vm){
        int top = vm.popRunStack();
        if(top == 0){
            vm.setPC(targetAddress);
        }
    }
    
    public String toString(){
        return "FALSEBRANCH " + label;
    }
    
    public int getTargetAddress(){
        return targetAddress;
    }
    
    public void setTargetAddress(int n){
        targetAddress = n;
    }
    
    public String getLabel(){
        return label;
    }
}