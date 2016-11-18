package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * POP n; Pop top n levels of runtime stack
 */
public class PopCode extends ByteCode{
    private int n;
    
    public void init(Vector<String> args){
        n = Integer.parseInt(args.get(0));
    }
    
    public void execute(VirtualMachine vm){
        for(int i=0;i<n; i++){
            vm.popRunStack();            
        }        
    }
    
    public String toString(){
        return "POP " + n;
    }
}
