package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.LitCode;
import interpreter.debugger.DebugVirtualMachine;
import java.util.Vector;

/**
 *
 * @author Bryce
 */
public class DebugLitCode extends LitCode{
    
    @Override
    public void init(Vector<String> args){
        super.init(args);
    }
    
    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        DebugVirtualMachine dvm = (DebugVirtualMachine) vm;
        if (!id.equals("")) {
            int offset = dvm.getRunStack().getOffset();
            dvm.enter(id, offset);
        }
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
}
