/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.PopCode;
import interpreter.debugger.DebugVirtualMachine;
import java.util.Vector;

/**
 *
 * @author Bryce
 */
public class DebugPopCode extends PopCode{
    
    @Override
    public void init(Vector<String> args){
        super.init(args);
    }
    
    @Override
    public void execute(VirtualMachine vm){
        super.execute(vm);
        DebugVirtualMachine dvm = (DebugVirtualMachine)vm;
        dvm.popRecordVars(n);
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
}
