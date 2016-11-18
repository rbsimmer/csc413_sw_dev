package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * READ; Read an integer; prompt the user for input; put the value just read on
 * top of the stack
 */
public class ReadCode extends ByteCode {
    
    public void init(Vector<String> args){   
        
    }
    
    public void execute(VirtualMachine vm){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("> ");
            String line = in.readLine();
            int n = Integer.parseInt(line);
            vm.pushRunStack(n);
        }catch(java.io.IOException ex){}        
    }
    
    public String toString(){
        return "READ";
    }
}
