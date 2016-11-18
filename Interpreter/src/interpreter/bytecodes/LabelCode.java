package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * LABEL <label>; target for branches; (see FALSEBRANCH, GOTO)
 */
public class LabelCode extends ByteCode{
    private String label;
    
    public void init(Vector<String> args){
        label = args.get(0);
    }
    
    public void execute(VirtualMachine vm){
        
    }
    
    public String toString(){
        return "LABEL " + label;
    }

    public String getLabel() {
        return label;
    }
}
