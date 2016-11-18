package interpreter;

import interpreter.bytecodes.ByteCode;
import interpreter.bytecodes.DumpCode;
import java.util.Stack;

/**
 * @author Robert Simmer CSC 413, Dr. Levine 5/8/2013
 * 
 * The virtual machine executes each byte code that is loaded into the program. 
 * It keeps track of the current position in the program; also, it holds a 
 * reference to the runtime stack.
 */
public class VirtualMachine {
    protected RunTimeStack runStack;    
    protected Stack<Integer> returnAddrs;
    protected int pc;
    protected boolean isRunning;
    protected boolean dump;
    protected Program program;

    public VirtualMachine(Program program) {
        this.program = program;
    }
    
    public void executeProgram(){
        pc = 0;
        runStack = new RunTimeStack();
        returnAddrs = new Stack<Integer>();
        isRunning = true;
        dump = false;
            while(isRunning){
                ByteCode code = program.getCode(pc);
                code.execute(this);            
                if(dump && !(code instanceof DumpCode)){    
                    System.out.println(code.toString());
                    runStack.dump();
                }            
                pc++;
            }
    }
    
    public void haltProgram(){
        isRunning = false;
    }
    
    public int popRunStack(){
        return runStack.pop();        
    } 
    
    public int pushRunStack(int n){
        return runStack.push(n);        
    } 
    
    public void setDump(boolean boo){
        dump = boo;
    }
    
    public int getPC(){
        return this.pc;
    }
    
    public void pushReturnAddress(int n){
        returnAddrs.push(n);
    }

    public void setPC(int targetAddress) {
        pc = targetAddress;
    }
    
    public int loadRunStack(int n){
        return runStack.load(n);        
    } 

    public int storeRunStack(int n) {
        return runStack.store(n);   
    }

    public void newFrameRunStack(int n) {
        runStack.newFrameAt(n);
    }
    
     public int peekRunStack(){
        return runStack.peek();        
    } 

    public int popReturnAddress() {
        return returnAddrs.pop().intValue();
    }

    public void popFrameRunStack() {
        runStack.popFrame();
    }
}
