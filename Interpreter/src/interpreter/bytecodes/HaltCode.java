package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * halt execution 
 */
public class HaltCode extends ByteCode{
    
    @Override
    public void init(Vector<String> args){
        
    }
    
    @Override
    public void execute(VirtualMachine vm){
        vm.haltProgram();
    }
    
    @Override
    public String toString(){
        return "HALT";
    }
}
