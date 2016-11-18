package interpreter.debugger;

/**
 *
 * @author Robert Simmer CSC 413, Dr. Levine 5/8/2013
 * 
 * Source is a class that contains two instance variables
 *      String sourceLine - contains the source program line i for Vector slot i
 *      Boolean isBreakptSet - is a breakpoint set for this line?
 */
public class SourceLine {
    private String sourceLine;
    private Boolean isBreakptSet = false;
    private Boolean isValidBrkpt = false;
    
    SourceLine(String str){
        sourceLine = str;
    }
    
    public String getSourceLine(){
        return sourceLine;
    }
        
    public void setSourceLine(String str){
        sourceLine = str;
    }
    
    public Boolean getIsBreakptSet(){
        return isBreakptSet;
    }
    
    public void setIsBreakptSet(Boolean bool){
        isBreakptSet = bool;
    }

    public boolean getIsValidBrkpt() {
        return isValidBrkpt;
    }

    public void setIsValidBrkpt() {
        isValidBrkpt = true;
    }
}
