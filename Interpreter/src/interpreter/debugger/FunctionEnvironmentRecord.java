package interpreter.debugger;

import java.util.Iterator;
import java.util.Set;


/**
 * The function environment record consists of the symbol table, function name,
 * starting/ending line number of the function and current line.
 *
 * @author Robert Simmer CSC 413, Dr. Levine 4/18/2013
 */
public class FunctionEnvironmentRecord {

    private Table symTab;
    private String funcName = "";
    private int startLine=0, endLine=0, currentLine=0;

    public FunctionEnvironmentRecord() {
        symTab = new Table();
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String name) {
        funcName = name;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int start) {
        startLine = start;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int end) {
        endLine = end;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(int line) {
        currentLine = line;
    }

    public void put(String var, Object val) {
        symTab.put(var, val);
    }

    public void pop(int n) {
        symTab.popVariables(n);
    }

    public static void dumpFER(FunctionEnvironmentRecord fer) {
        Set vars = fer.symTab.keys();
        Iterator iter = vars.iterator();

        System.out.print("(<");
        if (iter.hasNext()) {
            while (iter.hasNext()) {
                String s = (String) iter.next();
                System.out.print(s + "/" + fer.symTab.get(s));
                if (iter.hasNext()) {
                    System.out.print(",");
                }
            }
        }
        System.out.print(">," + fer.getFuncName() + ",");
        if (fer.getStartLine() == 0) {
            System.out.print("-,");
        } else {
            System.out.print(fer.getStartLine() + ",");
        }
        if (fer.getEndLine() == 0) {
            System.out.print("-,");
        } else {
            System.out.print(fer.getEndLine() + ",");
        }
        if (fer.getCurrentLine() == 0) {
            System.out.println("-)");
        } else {
            System.out.println(fer.getCurrentLine() + ")");
        }
    }

    public Table getTable() {
        return symTab;
    }
}
/**
 * <
 * pre>
 *  Binder objects group 3 fields
 *  1. a value
 *  2. the next link in the chain of symbols in the current scope
 *  3. the next link of a previous Binder for the same identifier
 *     in a previous scope
 * </pre>
 */
class Binder {

    private Object value;
    private String prevtop;   // prior symbol in same scope
    private Binder tail;      // prior binder for same symbol
    // restore this when closing scope

    Binder(Object v, String p, Binder t) {
        value = v;
        prevtop = p;
        tail = t;
    }

    Object getValue() {
        return value;
    }

    String getPrevtop() {
        return prevtop;
    }

    Binder getTail() {
        return tail;
    }
}