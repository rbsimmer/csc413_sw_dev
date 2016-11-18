package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * WRITE; Write the value on top of the stack to output; leave the value on top
 * of the stack
 */
public class WriteCode extends ByteCode{
    
    public void init(Vector<String> args){ 
        
    }
    
    public void execute(VirtualMachine vm){
        System.out.println(vm.peekRunStack());        
    }
    
    public String toString(){
        return "WRITE";
    }
}
