package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * GOTO <label>
 */
public class GoToCode extends BranchCode{
    private String label;
    private int targetAddress;
    
    public void init(Vector<String> args){
        label = args.get(0);
    }
    
    public void execute(VirtualMachine vm){
        vm.setPC(targetAddress);
    }
    
    public String toString(){
        return "GOTO " + label;
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
