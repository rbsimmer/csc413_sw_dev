package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ReturnCode;
import interpreter.debugger.DebugVirtualMachine;
import java.util.Vector;

/**
 *
 * @author Bryce
 */
public class DebugReturnCode extends ReturnCode{
    
    public void init(Vector<String> args){
        super.init(args);
    }
    
    public void execute(VirtualMachine vm){ 
        super.execute(vm);
        DebugVirtualMachine dvm = (DebugVirtualMachine)vm;
        dvm.popFER(retVal);
    }
    
    public String toString(){
        return super.toString();
    }
}
