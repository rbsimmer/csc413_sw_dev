package interpreter.debugger;

import interpreter.Program;
import interpreter.RunTimeStack;
import interpreter.VirtualMachine;
import interpreter.bytecodes.*;
import interpreter.bytecodes.debuggerByteCodes.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * @author Robert Simmer CSC 413, Dr. Levine 5/8/2013
 *
 */
public class DebugVirtualMachine extends VirtualMachine {

    private ArrayList<SourceLine> sourceProgram;    //arraylist of sourcelines and breakpoints
    private Stack<FunctionEnvironmentRecord> environmentStack;  //stack of function environment records
    private int envSize;
    private Boolean stepOut, stepOver, stepIn, trace, done;
    private String sourceFile, traceString;

    /**
     *
     * @param program
     * @param sourceFile
     * 
     * Several things are initialized in the DVM constructor, an ArrayList of
     * source file's code, the data members of the DVM itself, and the 
     * validity of breakpoints is established here as well
     */
    public DebugVirtualMachine(Program program, String sourceFile) throws IOException {
        super(program);
        sourceProgram = new ArrayList<SourceLine>();
        this.sourceFile = sourceFile;
        //initialize the list of sourcefile lines
        BufferedReader in = new BufferedReader(new FileReader(sourceFile));
        String line = in.readLine();
        while (line != null) {
            sourceProgram.add(new SourceLine(line));
            line = in.readLine();
        }
        //initialize data members
        environmentStack = new Stack<FunctionEnvironmentRecord>();
        environmentStack.push(new FunctionEnvironmentRecord());
        pc = 0;
        envSize = 0;
        runStack = new RunTimeStack();
        returnAddrs = new Stack<Integer>();
        stepOut = false;
        stepOver = false;
        stepIn = false;
        trace = false;
        done = false;
        //initialize the list of valid breakpoints
        ArrayList<ByteCode> byteCodeList = program.getByteCodeList();
        ByteCode code;
        for (int i = 0; i < byteCodeList.size(); i++) {
            code = byteCodeList.get(i);
            if (code instanceof LineCode) {
                int validLine = ((LineCode) code).getLineNum() - 1;
                if (validLine >= 0) {
                    (sourceProgram.get(validLine)).setIsValidBrkpt();
                }
            }
        }
    }

    /**
     * This is the execution cycle of the debugger virtual machine
     */
    @Override
    public void executeProgram() {
        isRunning = true;
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);
            pc++;
            //make sure to stop Debugger when last bytecode executed
            if (code instanceof HaltCode) {
                done = true;
            }            
        }
    }

    /**
     *
     * @param lineNum
     * 
     * Actual work of the LineCode bytecode
     */
    public void line(int lineNum) {
        //peek ahead to setup function environment record
        ByteCode code = program.getCode(pc + 1);
        if (code instanceof FunctionCode) {
            pc++;
            code.execute(this);            
        }
        //check for breakpoint at current line
        if (lineNum > 0) {
            environmentStack.peek().setCurrentLine(lineNum);
            if (sourceProgram.get(lineNum - 1).getIsBreakptSet()) {
                isRunning = false;
                stepIn = false;
                stepOut = false;
                stepOver = false;
            }
        }
        //check for stepin flag
        if (stepIn && (envSize == environmentStack.size())) {
            isRunning = false;
            stepIn = false;
        }
        //check for stepover flag
        if (stepOver && (envSize == environmentStack.size())) {
            isRunning = false;
            stepOver = false;
        }
    }

    /**
     *
     * @param name
     * @param start
     * @param end
     * 
     * Actual work of the FunctionCode bytecode
     */
    public void function(String name, int start, int end) {
        environmentStack.peek().setFuncName(name);
        environmentStack.peek().setStartLine(start);
        environmentStack.peek().setEndLine(end);
        environmentStack.peek().setCurrentLine(start);         
        //if trace is on, print call hierarchy
        if (trace && !environmentStack.empty() && !runStack.empty()) {
            int size = environmentStack.size();
            for (int i = 1; i < size - 1; i++) {
                traceString += "  ";
            }
            traceString += name + " (";
            //peekahead to get the formals the current function is called with
            int temp = pc + 1;
            ByteCode code = program.getCode(temp);
            while (code instanceof FormalCode) {
                int offset = ((FormalCode)code).getOffset();
                traceString += valueFromRunStack(offset);
                temp++;
                code = program.getCode(temp);
                if (code instanceof FormalCode){
                    traceString += ", ";
                }                
            }
            traceString += ")\n";
        }
        //execute formal codes before stepin returns control back to user
        if(stepIn){
            ByteCode code = program.getCode(pc + 1);
            while (code instanceof FormalCode) {
                code.execute(this);
                pc++;
                code = program.getCode(pc + 1);
            }
        }
        //step into: check if environment stack size has increased
        if (stepIn && (envSize < environmentStack.size())) {
            isRunning = false;
            stepIn = false;
        }
    }

    /**
     *
     * @param retVal
     * 
     * Actual work of the DebugReturnCode bytecode
     */
    public void popFER(int retVal) {
        //if trace is on, print call hierarchy
        if (trace && !environmentStack.empty() && !runStack.empty()) {
            int size = environmentStack.size();
            for (int i = 1; i < size - 1; i++) {
                traceString += "  ";
            }
            traceString += "exit " + environmentStack.peek().getFuncName() + ": " + retVal + "\n";
        }
        //remove last FER from environmentStack
        environmentStack.pop();
        //check for stepout flag
        if (stepOut && (envSize > environmentStack.size())) {
            isRunning = false;
            stepOut = false;
        }
        //check for stepover flag
        if (stepOver && (envSize >= environmentStack.size())) {
            isRunning = false;
            stepOver = false;
        }
    }

    public int valueFromRunStack(int offset) {
        return runStack.getValueAtOffset(offset);
    }

    /**
     *
     * @param variable
     * @param value
     * 
     * Enter a value onto the current Function Environment Record, be it
     * an instance of LitCode or FormalCode
     */
    public void enter(String variable, int value) {
        (environmentStack.peek()).put(variable, value);        
    }

    public String setBreakpt(int num) {
        num -= 1;
        if ((sourceProgram.get(num)).getIsValidBrkpt()) {
            (sourceProgram.get(num)).setIsBreakptSet(true);
            num += 1;
            return "Breakpoint set at line #" + num;
        } else {
            num += 1;
            return "ERROR: Cannot set a breakpoint on line #" + num;
        }
    }

    public String clearBreakpt() {
        for (SourceLine s : sourceProgram) {
            s.setIsBreakptSet(false);
        }
        return "All breakpoints have been cleared";
    }

    public String clearBreakpt(int num) {
        num -= 1;
        if ((sourceProgram.get(num)).getIsBreakptSet()) {
            (sourceProgram.get(num)).setIsBreakptSet(false);
            num += 1;
            return "Breakpoint cleared at line #" + num;
        } else {
            num += 1;
            return "ERROR: No breakpoint on line #" + num;
        }
    }

    public FunctionEnvironmentRecord peekEnvironmentStack() {
        return environmentStack.peek();
    }

    public String continueExec() {
        String str = "Continuing Execution";
        isRunning = true;
        return str;
    }

    public int getCurrentLine() {
        return environmentStack.peek().getCurrentLine();
    }

    public void pushRecord() {
        environmentStack.push(new FunctionEnvironmentRecord());
    }

    public void popRecordVars(int n) {
        environmentStack.peek().pop(n);
    }

    public String displayVars() {
        Table symbolTable = (environmentStack.peek()).getTable();
        Set vars = symbolTable.keys();
        Iterator iter = vars.iterator();
        String str = "";
        str += "Displaying Local Variables:\n\t";
        if (iter.hasNext()) {
            while (iter.hasNext()) {
                String s = (String) iter.next();
                int offset = (Integer) symbolTable.get(s);
                int value = valueFromRunStack(offset);
                str += s + ": " + value;
                if (iter.hasNext()) {
                    str += ", ";
                }
            }
        } else {
            //maybe be in and intrinsic function or FER not initialized yet
            str += "No local variables at this time";  
        }
        return str;
    }

    public String displayFunc() {
        int start, end, current;
        String str = "";
        current = environmentStack.peek().getCurrentLine();
        start = environmentStack.peek().getStartLine();
        end = environmentStack.peek().getEndLine();
        if (start < 0) {
            str += "******" + (environmentStack.peek()).getFuncName() + "******\n";
            return str;
        } else if (start == 0) {  //display main is FER not initialized yet
            start = 1;
            end = sourceProgram.size();
        }
        for (int i = start - 1; i < end; i++) {
            if ((sourceProgram.get(i)).getIsBreakptSet()) {
                str += "*";
            } else {
                str += " ";
            }
            if (i >= 0 && i <= 8) {
                str += " ";
            }
            str += (i + 1) + ". " + (sourceProgram.get(i)).getSourceLine();
            if ((i + 1) == current) {
                str += "\t\t<--------\n";
            } else {
                str += "\n";
            }
        }
        return str;
    }

    public String printSource() {
        int i = 1;
        String str = "**** Debugging " + sourceFile + ".cod ****\n\n";
        for (SourceLine s : sourceProgram) {
            str += " ";
            if (i < 10) {
                str += " ";
            }
            str += i + ". " + s.getSourceLine() + "\n";
            i++;
        }
        str += "\nType ? for help";
        return str;
    }

    public void setIsRunning(boolean bool) {
        isRunning = bool;
    }

    public boolean getIsBreakptSet(int currentLine) {
        if (currentLine >= 0) {
            return (sourceProgram.get(currentLine)).getIsBreakptSet();
        }
        return false;
    }

    public SourceLine getSource(int lineNum) {
        return sourceProgram.get(lineNum);
    }

    public Program getProgram() {
        return program;
    }

    public String listBreaks() {
        int i = 0, count = 0;
        String str = "Breakpoints set at lines: ";
        for (SourceLine s : sourceProgram) {
            i++;
            if (s.getIsBreakptSet()) {
                str = str + i + " ";
                count++;
            }
        }
        if (count > 0) {
            return str;
        } else {
            return "No breakpoints curently set";
        }
    }

    public String displayStack() {
        Stack<FunctionEnvironmentRecord> callStack = new Stack<FunctionEnvironmentRecord>();
        String str = "";
        if ((environmentStack.peek()).getFuncName().equals("")) {
            return "No functions have been called as of yet\n";
        } else {
            while (!environmentStack.empty()) {
                str = str + (environmentStack.peek()).getFuncName() + ": line "
                        + (environmentStack.peek()).getCurrentLine() + "\n";
                callStack.push(environmentStack.pop());
            }
            while (!callStack.empty()) {
                environmentStack.push(callStack.pop());
            }
            return str;
        }
    }

    public String stepOut() {
        stepOut = true;
        envSize = environmentStack.size();
        executeProgram();
        return "Stepping Out of current function...\n";
    }

    public String stepOver() {
        stepOver = true;
        envSize = environmentStack.size();
        executeProgram();
        return "Stepping Over next line...\n";
    }

    public String stepIn() {
        stepIn = true;
        envSize = environmentStack.size();
        executeProgram();
        return "Stepping Into function on current line...\n";
    }

    public RunTimeStack getRunStack() {
        return runStack;
    }

    public Boolean getDone() {
        return done;
    }

    public String trace(String s) {
        String str = "";
        if (s.equals("on")) {
            trace = true;
            str = str + "****Tracing ON";
            traceString = "";
        } else if (s.equals("off")) {
            trace = false;
            str = str + "****Tracing OFF";
            traceString = "";
        }
        return str;
    }
    
    public String printTrace() {
        return traceString;
    }

    public Boolean getTrace() {
        return trace;
    }
}
