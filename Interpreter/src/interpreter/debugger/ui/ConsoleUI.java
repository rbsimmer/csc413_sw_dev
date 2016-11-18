package interpreter.debugger.ui;

import interpreter.debugger.DebugVirtualMachine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author Robert Simmer CSC 413, Dr. Levine 5/8/2013
 * 
 * The ConsoleUI is the interface between the user and the debugger. It owns an 
 * instance of the Debugger Virtual Machine and can therefore call methods in
 * the DVM, but the DVM does not know anything about the the actual 
 * functionality of the UI. The UserInterface is an abstraction layer that 
 * allows for any kind of UI (GUI, Java Applet, etc.) to be used in the place of the
 * ConsoleUI with minimal changes. All of the commands are handled as methods 
 * inside of the DVM, which executes the command calls from the UI and returns
 * a string, which the UI displays in whatever medium it chooses.
 */
public class ConsoleUI implements UserInterface {

    private DebugVirtualMachine dvm;
    private boolean isDebugRunning;

    public ConsoleUI(DebugVirtualMachine dvm) {
        this.dvm = dvm;
    }

    @Override
    public void runUI() {
        isDebugRunning = true;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        printSource();
        while (isDebugRunning) {
            //check if HaltCode reached
            if (dvm.getDone()) {
                isDebugRunning = false;
                System.out.println("Execution complete\n");
            } else { //print prompt, take in command and execute
                System.out.print(">> ");
                try {
                    line = in.readLine();
                    executeCommand(line);
                } catch (Exception e) {
                    System.out.println("***ERROR: " + e + "\n");
                }
            }
        }
    }

    @Override
    public void executeCommand(String line) {
        StringTokenizer tok = new StringTokenizer(line);
        try {
            while (tok.hasMoreTokens()) {
                String str = tok.nextToken();
                if (str.equals("?")) {
                    helpMenu();
                } else if (str.equals("sbr")) {
                    int i;
                    while (tok.hasMoreTokens()) {
                        i = (int) Integer.parseInt(tok.nextToken());
                        setBreaks(i);
                    }
                    System.out.println();
                } else if (str.equals("clr")) {
                    if (!tok.hasMoreTokens()) {
                        clearBreaks();
                    }
                    int i;
                    while (tok.hasMoreTokens()) {
                        i = (int) Integer.parseInt(tok.nextToken());
                        clearBreaks(i);
                    }
                    System.out.println();
                } else if (str.equals("list")) {
                    listBreaks();
                } else if (str.equals("dcf")) {
                    displayFunc();
                } else if (str.equals("dlv")) {
                    displayVars();
                } else if (str.equals("dcs")) {
                    displayStack();
                } else if (str.equals("stpovr")) {
                    stepOver();
                } else if (str.equals("stpin")) {
                    stepIn();
                } else if (str.equals("stpout")) {
                    stepOut();
                } else if (str.equals("trace")) {
                    if (tok.hasMoreTokens()) {
                        trace(tok.nextToken());
                    }
                } else if (str.equals("cont")) {
                    continueExec();
                } else if (str.equals("quit")) {
                    quit();
                }
            }
        } catch (Exception e) {
            System.out.println("***ERROR: " + e + "\n");
        }
    }

    @Override
    public void helpMenu() {
        System.out.println("Displaying Help Menu:\n");
        System.out.printf("%-25s %s \n", "sbr <line number>", "- Set breakpoints at specified line numbers (spaces between multiple lines)");
        System.out.printf("%-25s %s \n", "clr <line number>", "- Clear breakpoints (spaces between multiple lines, no line numbers to clear all)");
        System.out.printf("%-25s %s \n", "list", "- List current breakpoints");
        System.out.printf("%-25s %s \n", "dcf", "- Display current function sourcecode");
        System.out.printf("%-25s %s \n", "dlv", "- Display local variables in current function");
        System.out.printf("%-25s %s \n", "dcs", "- Display call stack");
        System.out.printf("%-25s %s \n", "stpovr", "- To step over current line");
        System.out.printf("%-25s %s \n", "stpin", "- To step into function on current line");
        System.out.printf("%-25s %s \n", "stpout", "- To step out of current function");
        System.out.printf("%-25s %s \n", "trace <on/off>", "- Turn tracing ON/OFF");
        System.out.printf("%-25s %s \n", "cont", "- To continue execution");
        System.out.printf("%-25s %s \n\n", "quit", "- To exit the debugger");
    }

    @Override
    public void setBreaks(int line) {
        System.out.println(dvm.setBreakpt(line));
    }

    @Override
    public void clearBreaks() {
        System.out.println(dvm.clearBreakpt());
    }

    @Override
    public void clearBreaks(int line) {
        System.out.println(dvm.clearBreakpt(line));
    }

    @Override
    public void displayFunc() {
        System.out.println("Displaying Current Function:\n\n" + dvm.displayFunc());
    }

    @Override
    public void continueExec() {
        System.out.println(dvm.continueExec() + "\n");
        dvm.executeProgram();
        //The trace is displayed whenever control is passed back to the user 
        //and trace is ON
        if (dvm.getTrace()) {
            printTrace();
        }
        System.out.println(dvm.displayFunc());
    }

    @Override
    public void quit() {
        System.out.println("Leaving Debugger\n");
        isDebugRunning = false;
    }

    @Override
    public void displayVars() {
        System.out.println(dvm.displayVars() + "\n");
    }

    @Override
    public void printSource() {
        System.out.println(dvm.printSource());
    }

    @Override
    public void listBreaks() {
        System.out.println(dvm.listBreaks() + "\n");
    }

    @Override
    public void displayStack() {
        System.out.println(dvm.displayStack());
    }

    @Override
    public void trace(String str) {
        System.out.println(dvm.trace(str) + "\n");
    }

    @Override
    public void stepOver() {
        System.out.println(dvm.stepOver() + "\n");
        if (dvm.getTrace()) {
            printTrace();
        }
        System.out.println(dvm.displayFunc());
    }

    @Override
    public void stepIn() {
        System.out.println(dvm.stepIn() + "\n");
        if (dvm.getTrace()) {
            printTrace();
        }
        System.out.println(dvm.displayFunc());
    }

    @Override
    public void stepOut() {
        System.out.println(dvm.stepOut());
        if (dvm.getTrace()) {
            printTrace();
        }
        System.out.println(dvm.displayFunc());
    }

    @Override
    public void printTrace() {
        System.out.println(dvm.printTrace());
    }
}
