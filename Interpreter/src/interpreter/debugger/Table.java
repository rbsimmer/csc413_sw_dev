/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger;

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
public class Table {

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

