package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.CallCode;
import interpreter.debugger.DebugVirtualMachine;
import java.util.Vector;

/**
 *
 * @author Bryce
 */
public class DebugCallCode extends CallCode{
    
    @Override
    public void init(Vector<String> args){
        super.init(args);        
    }
    
    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        DebugVirtualMachine dvm = (DebugVirtualMachine)vm;
        dvm.pushRecord();
    }
    
    @Override
    public String toString(){    
        return super.toString();
    }
        
    @Override
    public int getTargetAddress(){
        return super.targetAddress;
    }
    
    @Override
    public void setTargetAddress(int n){
        super.targetAddress = n;
    }
    
    @Override
    public String getLabel(){
        return super.funcName;
    }
}
