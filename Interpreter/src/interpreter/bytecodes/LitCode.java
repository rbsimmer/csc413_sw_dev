package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * LIT n - load the literal value n
 * LIT 0 i - this form of the Lit was generated to load 0 on the stack in order
 * to initialize the variable i to 0 and reserve space on the runtime stack for i
 */
public class LitCode extends ByteCode{
    protected int n;
    protected String id;
    
    public void init(Vector<String> args){
        n = Integer.parseInt(args.get(0));
        if(args.size()>1){
            id = args.get(1);
        }else{
            id = "";
        }
    }
    
    public void execute(VirtualMachine vm){
        if(id.equals("")){
            vm.pushRunStack(n);
        }else{
            vm.pushRunStack(0);
        }
    }
    
    public String toString(){
        String output = "LIT " + n + " " + id;
        if(!id.equals("")){
            output = output + "    int " + id;
        }
        return output;
    }
}
