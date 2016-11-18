package functionenvironmentrecord;

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
    private String funcName;
    private int startLine, endLine, currentLine;

    public FunctionEnvironmentRecord() {
        symTab = new Table();
        funcName = "-";
        startLine = 0;
        endLine = 0;
        currentLine = 0;
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

    public void pushVariable(String var, Object val) {
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

    public static void main(String[] args) {
        FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord();

        System.out.print("BS\t\t");
        //start a new scope (set top to null)
        fctEnvRecord.symTab.beginScope();
        dumpFER(fctEnvRecord);
        System.out.print("Function g 1 20\t");
        //update funcName, startLine, endLine
        fctEnvRecord.setFuncName("g");
        fctEnvRecord.setStartLine(1);
        fctEnvRecord.setEndLine(20);
        dumpFER(fctEnvRecord);
        System.out.print("Line 5\t\t");
        //update currentLine 
        fctEnvRecord.setCurrentLine(5);
        dumpFER(fctEnvRecord);
        System.out.print("Enter a 4\t");
        //add a new variable to the table
        fctEnvRecord.pushVariable("a", 4);
        dumpFER(fctEnvRecord);
        System.out.print("Enter b 2\t");
        //add a new variable to the table
        fctEnvRecord.pushVariable("b", 2);
        dumpFER(fctEnvRecord);
        System.out.print("Enter c 7\t");
        //add a new variable to the table
        fctEnvRecord.pushVariable("c", 7);
        dumpFER(fctEnvRecord);
        System.out.print("Enter a 1\t");
        //add a new variable to the table
        fctEnvRecord.pushVariable("a", 1);
        dumpFER(fctEnvRecord);
        System.out.print("Pop 2\t\t");
        //remove the last 2 string/value pairs from the table
        fctEnvRecord.pop(2);
        dumpFER(fctEnvRecord);
        System.out.print("Pop 1\t\t");
        //remove the last string/value pair from the table
        fctEnvRecord.pop(1);
        dumpFER(fctEnvRecord);
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

/**
 * <
 * pre>
 * The Table class is similar to java.util.Dictionary, except that
 * each key must be a Symbol and there is a scope mechanism.
 *
 * Consider the following sequence of events for table t:
 * t.put(Symbol("a"),5)
 * t.beginScope()
 * t.put(Symbol("b"),7)
 * t.put(Symbol("a"),9)
 *
 * symbols will have the key/value pairs for Symbols "a" and "b" as:
 *
 * Symbol("a") ->
 *     Binder(9, Symbol("b") , Binder(5, null, null) )
 * (the second field has a reference to the prior Symbol added in this
 * scope; the third field refers to the Binder for the Symbol("a")
 * included in the prior scope)
 * Binder has 2 linked lists - the second field contains list of symbols
 * added to the current scope; the third field contains the list of
 * Binders for the Symbols with the same string id - in this case, "a"
 *
 * Symbol("b") ->
 *     Binder(7, null, null)
 * (the second field is null since there are no other symbols to link
 * in this scope; the third field is null since there is no Symbol("b")
 * in prior scopes)
 *
 * top has a reference to Symbol("a") which was the last symbol added
 * to current scope
 *
 * Note: What happens if a symbol is defined twice in the same scope??
 * </pre>
 */
class Table {

    private java.util.HashMap<String, Binder> vars = new java.util.HashMap<String, Binder>();
    private String top;    // reference to last symbol added to
    // current scope; this essentially is the
    // start of a linked list of symbols in scope

    /*
     public static void main(String args[]) {
     Symbol s = Symbol.symbol("a", 1),
     s1 = Symbol.symbol("b", 2),
     s2 = Symbol.symbol("c", 3);

     Table t = new Table();
     t.beginScope();
     t.put(s,"top-level a");
     t.put(s1,"top-level b");
     t.beginScope();
     t.put(s2,"second-level c");
     t.put(s,"second-level a");
     t.endScope();
     t.put(s2,"top-level c");
     t.endScope();
     }

     */
    public Table() {
    }

    /**
     * Gets the object associated with the specified symbol in the Table.
     */
    public Object get(String key) {
        Binder e = vars.get(key);
        return e.getValue();
    }

    /**
     * Puts the specified value into the Table, bound to the specified
     * Symbol.<br> Maintain the list of symbols in the current scope (top);<br>
     * Add to list of symbols in prior scope with the same string identifier
     */
    public void put(String key, Object value) {
        vars.put(key, new Binder(value, top, vars.get(key)));
        top = key;
    }

    /**
     * Remembers the current state of the Table; push new mark on mark stack
     */
    public void beginScope() {
        top = null;
    }

    /**
     * Restores the table to what it was at the most recent beginScope that has
     * not already been ended.
     */
    public void popVariables(int n) {
        while (n > 0) {
            if (top != null) {
                Binder e = vars.get(top);
                if (e.getTail() != null) {
                    vars.put(top, e.getTail());
                } else {
                    vars.remove(top);
                }
                top = e.getPrevtop();
                n--;
            }else{
                System.out.println("\n***ERROR: Trying to pop too many symbols");
                System.exit(-1);
            }
        }
    }

    /**
     * @return a set of the Table's strings.
     */
    public java.util.Set<String> keys() {
        return vars.keySet();
    }
}
