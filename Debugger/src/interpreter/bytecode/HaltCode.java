package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * halt execution 
 */
public class HaltCode extends ByteCode{
    
    public void init(Vector<String> args){
        
    }
    
    public void execute(VirtualMachine vm){
        vm.haltProgram();
    }
    
    public String toString(){
        return "HALT";
    }
}
