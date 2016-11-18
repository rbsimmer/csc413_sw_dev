package interpreter.debugger.ui;

/**
 *
 * @author Robert Simmer CSC 413, Dr. Levine 5/8/2013
 */
public interface UserInterface{
    //execute the user interface. prompt user for input and execute commands.
    void runUI();
    //carry out user commands
    void executeCommand(String line);
    //display a menu of all commands and their syntaxes
    void helpMenu();
    //set a breakpoint at a specified line
    void setBreaks(int line);    
    //clear all breakpoints
    void clearBreaks();
    //clear a breakpoint at a specified line
    void clearBreaks(int line);
    //display the current function
    void displayFunc();
    //continue execution until next breakpoint
    void continueExec();
    //quit execution of program
    void quit();
    //display all variables in the current scope
    void displayVars();    
    //print source code with associated line numbers
    void printSource();
    //list all active breakpoints
    void listBreaks();
    //display call stack
    void displayStack();
    //toggle trace on/off
    void trace(String str);
    //toggle stepping over current line
    void stepOver();
    //toggle stepping into function on current line
    void stepIn();
    //toggle stepping out of current function
    void stepOut();
    //print the call hierarchy
    void printTrace();
}
