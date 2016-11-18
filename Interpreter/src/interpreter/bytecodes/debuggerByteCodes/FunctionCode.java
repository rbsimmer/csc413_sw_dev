package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVirtualMachine;
import java.util.Vector;

/**
 * FUNCTION name start end - name of function with source line number
 * boundaries given by start and end. Generated as header for each function
 * declaration along with FORMAL. 
 */
public class FunctionCode extends ByteCode{
    private String name;
    private int start;
    private int end;
    
    /**
     *
     * @param args
     */
    @Override
    public void init(Vector<String> args){
        name = args.get(0);
        start = Integer.parseInt(args.get(1)); 
        end = Integer.parseInt(args.get(2));
    }
    
    @Override
    public void execute(VirtualMachine vm){
        DebugVirtualMachine dvm = (DebugVirtualMachine)vm;
        dvm.function(name, start, end);
    }
    
    public String getName(){
        return name;
    }
}
