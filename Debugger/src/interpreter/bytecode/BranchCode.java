package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * abstract class for all branching bytecodes
 * subclass of ByteCode 
 * superclass of FalseBranchCode, GoToCode, CallCode
 */
public abstract class BranchCode extends ByteCode{
    public abstract void init(Vector<String> args);
    public abstract void execute(VirtualMachine vm);
    public abstract int getTargetAddress();
    public abstract void setTargetAddress(int n);
    public abstract String getLabel();
}
