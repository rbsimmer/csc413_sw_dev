package interpreter;

/**
 * @author Robert Simmer CSC 413, Dr. Levine 4/9/2013
 *
 * Holds and initializes the Hashtable that ByteCodeLoader uses to create
 * instances of the bytecode classes
 */
public class CodeTable {

    public static java.util.HashMap<String, String> byteCode = new java.util.HashMap<String, String>();

    public static void init() {
        byteCode.put("HALT", "HaltCode");
        byteCode.put("POP", "PopCode");
        byteCode.put("FALSEBRANCH", "FalseBranchCode");
        byteCode.put("GOTO", "GoToCode");
        byteCode.put("STORE", "StoreCode");
        byteCode.put("LOAD", "LoadCode");
        byteCode.put("LIT", "LitCode");
        byteCode.put("ARGS", "ArgsCode");
        byteCode.put("CALL", "CallCode");
        byteCode.put("RETURN", "ReturnCode");
        byteCode.put("BOP", "BopCode");
        byteCode.put("READ", "ReadCode");
        byteCode.put("WRITE", "WriteCode");
        byteCode.put("LABEL", "LabelCode");
        byteCode.put("DUMP", "DumpCode");
    }

    public static void debugInit() {
        byteCode.put("HALT", "HaltCode");
        byteCode.put("POP", "debuggerByteCodes.DebugPopCode");
        byteCode.put("FALSEBRANCH", "FalseBranchCode");
        byteCode.put("GOTO", "GoToCode");
        byteCode.put("STORE", "StoreCode");
        byteCode.put("LOAD", "LoadCode");
        byteCode.put("LIT", "debuggerByteCodes.DebugLitCode");
        byteCode.put("ARGS", "ArgsCode");
        byteCode.put("CALL", "debuggerByteCodes.DebugCallCode");
        byteCode.put("RETURN", "debuggerByteCodes.DebugReturnCode");
        byteCode.put("BOP", "BopCode");
        byteCode.put("READ", "ReadCode");
        byteCode.put("WRITE", "WriteCode");
        byteCode.put("LABEL", "LabelCode");
        byteCode.put("DUMP", "DumpCode");
        byteCode.put("LINE", "debuggerByteCodes.LineCode");
        byteCode.put("FUNCTION", "debuggerByteCodes.FunctionCode");
        byteCode.put("FORMAL", "debuggerByteCodes.FormalCode");
    }

    public static String get(String code) {
        return byteCode.get(code);
    }
}
