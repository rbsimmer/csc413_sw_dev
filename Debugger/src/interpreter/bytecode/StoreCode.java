package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * STORE n <id> - pop the top of the stack; store value into the offset n from
 * the start of the frame;<id> is used as a comment, it's the variable name
 * where the data is stored
 */
public class StoreCode extends ByteCode{
    private int n;
    private int value;
    private String id;
        
    public void init(Vector<String> args){
        n = Integer.parseInt(args.get(0));
        id = args.get(1);
    }
   
    public void execute(VirtualMachine vm){
        vm.storeRunStack(n);
        value = vm.peekRunStack();
    }
  
    public String toString(){
        return "STORE " + n + " " + id + "    " + id + " = " + value;
    }
    
}
