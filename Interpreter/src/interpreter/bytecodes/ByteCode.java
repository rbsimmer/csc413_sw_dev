package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * abstract class for each bytecode
 */
public abstract class ByteCode {
    public abstract void init(Vector<String> args);
    public abstract void execute(VirtualMachine vm);
}
