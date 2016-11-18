package interpreter;

import interpreter.debugger.DebugVirtualMachine;
import interpreter.debugger.ui.ConsoleUI;
import java.io.IOException;

/**
 *
 * @author Bryce
 */
public class Debugger{
    private DebugVirtualMachine dvm;
    private ConsoleUI ui;
    
    public Debugger(String sourcefile, String codefile) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
        Interpreter interpreter = new Interpreter(codefile);        
        CodeTable.debugInit();
        Program program = interpreter.getProgram(); 
        dvm = new DebugVirtualMachine(program, sourcefile);
        ui = new ConsoleUI(dvm);
    }
    
    public void runDebug() throws IOException, Exception{
            ui.runUI();
    }  
}

