package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * DUMP ON/OFF - sets the dump flag in the virtual machine to true/false respectively
 */
public class DumpCode extends ByteCode{
    private String state;
    
    public void init(Vector<String> args){
        state = args.get(0);
    }
    
    public void execute(VirtualMachine vm){
        if (state.equals("ON")){
            vm.setDump(true);
        }else{
            vm.setDump(false);
        }
    }    
}
