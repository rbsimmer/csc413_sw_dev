package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVirtualMachine;
import java.util.Vector;

/**
 * FORMAL xyz offset - xyz is the formal with associated offset. Generated as
 * a header for each function declaration along with FUNCTION.
 */
public class FormalCode extends ByteCode{
    private String variable;
    private int offset;
    
    @Override
    public void init(Vector<String> args){
        variable = args.get(0);
        offset = Integer.parseInt(args.get(1));
    }
    
    @Override
    public void execute(VirtualMachine vm){
        DebugVirtualMachine dvm = (DebugVirtualMachine)vm;
        dvm.enter(variable, offset);
    }
    
    public String getVariable(){
        return variable;
    }
    
    public int getOffset(){
        return offset;
    }
}
