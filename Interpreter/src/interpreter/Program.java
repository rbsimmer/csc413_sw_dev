package interpreter;

import interpreter.bytecodes.BranchCode;
import interpreter.bytecodes.ByteCode;
import interpreter.bytecodes.LabelCode;
import java.util.ArrayList;

/**
 * @author Robert Simmer
 * CSC 413, Dr. Levine
 * 4/9/2013
 * 
 * Holds the bytecode objects and has a method to resolve addresses for branch
 * instructions.
 */
public class Program {
    //byteCodeList holds the instance of bytecodes for every line in the program
    private ArrayList<ByteCode> byteCodeList = new ArrayList<ByteCode>();
    //labelTable is the hashmap used to resolve target addresses of branching bytecodes key = label, value = target address 
    private static java.util.HashMap<String, Integer> labelTable = new java.util.HashMap<String, Integer>();
        
    public void addByteCode(ByteCode byteCode) {
        if (byteCode instanceof LabelCode){
            LabelCode label = (LabelCode)byteCode;
            labelTable.put(label.getLabel(), byteCodeList.size());
        }
        byteCodeList.add(byteCode);
    }
    
    //method to get the code at the index in the ArrayList designated by pc
    public ByteCode getCode(int pc) {
        return byteCodeList.get(pc);
    }
    
    public void resolveAddress(){
        Integer jumpAddress;
        for (int i=0; i < byteCodeList.size(); i++) {            
            if (byteCodeList.get(i) instanceof BranchCode){
                BranchCode temp = (BranchCode)byteCodeList.get(i);
                jumpAddress = new Integer(labelTable.get(temp.getLabel()));
                temp.setTargetAddress(jumpAddress.intValue());
            }          
        }
    }

    public ArrayList<ByteCode> getByteCodeList() {
        return byteCodeList;
    }
}