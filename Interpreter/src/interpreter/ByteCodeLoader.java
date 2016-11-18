package interpreter;

import interpreter.bytecodes.ByteCode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Robert Simmer
 * CSC 413, Dr. Levine
 * 4/9/2013
 * 
 * This is the class that will be in charge of loading the code from the 
 * source file. It also has a method that loads bytecode objects into an 
 * instance of the program class. This is done with the help of a 
 * Hashtable that matches the bytecode to their equivalent classname.
 */
public class ByteCodeLoader{
    private BufferedReader codeFile;
        
    public ByteCodeLoader(String programFile) throws IOException{
        codeFile = new BufferedReader(new FileReader(programFile));
    }
        
    public Program loadCodes() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{ 
       Program program = new Program();       
       //The Vector args holds the arguments associated with each bytecode (i.e. LIT 0, 0 is args[0])
       Vector<String> args = new Vector<String>();
       //read first line of the program
       String code = codeFile.readLine();
        
       while (code != null) {
            StringTokenizer tok = new StringTokenizer(code);
            args.clear(); //clear argument list on each iteration
            
            String codeClass = CodeTable.get(tok.nextToken());
            while(tok.hasMoreTokens()) {
                args.add(tok.nextToken());
            }
            
            ByteCode byteCode = (ByteCode)(Class.forName("interpreter.bytecodes."+codeClass).newInstance());
            //intitialize the bytecode instance with the arguments in args
            byteCode.init(args);            
            //add the bytecode to the ArrayList in Program.java that keeps track of each bytecode
            program.addByteCode(byteCode);
            //get the next line in the program
            code = codeFile.readLine();
        }
       //resolve the target addresses for branching bytecodes
       program.resolveAddress();  
       return program;
    }     
    
}
