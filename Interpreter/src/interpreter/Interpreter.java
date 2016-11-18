/**
 * @author Robert Simmer CSC 413, Dr. Levine 4/9/2013
 */
package interpreter;

import java.io.*;

/**
 * <pre>
 *
 *
 *
 *     Interpreter class runs the interpreter:
 *     1. Perform all initializations
 *     2. Load the bytecodes from file
 *     3. Run the virtual machine
 *
 *
 *
 * </pre>
 */
public class Interpreter {
    private ByteCodeLoader bcl;

    public Interpreter(String codeFile) {
        try {
            CodeTable.init();
            bcl = new ByteCodeLoader(codeFile);
        } catch (IOException e) {
            System.out.println("**** " + e);
        }
    }
    
    public Program getProgram() throws InstantiationException, IllegalAccessException, ClassNotFoundException, ClassNotFoundException, IOException {
        Program program = bcl.loadCodes();
        return program;
    }
    
    public void run() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        Program program = bcl.loadCodes();
        VirtualMachine vm = new VirtualMachine(program);
        vm.executeProgram();
    }

    public static void main(String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, Exception {
        switch (args.length) {
            case 1:
                (new Interpreter(args[0]+".x.cod")).run();
                break;
            case 2:
                if (args[0].equals("-d")) {
                    (new Debugger(args[1]+".x", args[1]+".x.cod")).runDebug();
                }
                break;
            default:
                System.out.println("***Incorrect usage, try: "
                        + "java interpreter.Interpreter <file>");
                System.exit(1);
                break;
        }
    }    
}