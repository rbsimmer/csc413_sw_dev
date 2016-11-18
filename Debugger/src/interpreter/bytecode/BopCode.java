package interpreter.bytecode;

import interpreter.VirtualMachine;
import java.util.Vector;

/**
 * bop <binary op> - pop top 2 levels of the stack and perform indicated 
 * operation - operations are + - / * == != <= > >= < | &
 * | and & are logical operators, not bit operators lower level is the first
 * operand: e.g. <second-level> + <top-level>
 */
public class BopCode extends ByteCode{
    private String binaryOp;
    
    public void init(Vector<String> args){
        binaryOp = args.get(0);
    }
    
    public void execute(VirtualMachine vm){
        int top = vm.popRunStack();
        int second = vm.popRunStack();
        
        if(binaryOp.equals("+")){
            vm.pushRunStack(second + top);
        }else if(binaryOp.equals("-")){
            vm.pushRunStack(second - top);
        }else if(binaryOp.equals("/")){
            vm.pushRunStack(second / top);
        }else if(binaryOp.equals("*")){
            vm.pushRunStack(second * top);
        }else if(binaryOp.equals("==")){
            if(second == top){
                vm.pushRunStack(1); 
            }else{
                vm.pushRunStack(0);
            }            
        }else if(binaryOp.equals("!=")){
            if(second != top){
                vm.pushRunStack(1); 
            }else{
                vm.pushRunStack(0);
            }      
        }else if(binaryOp.equals("<=")){
            if(second <= top){
                vm.pushRunStack(1); 
            }else{
                vm.pushRunStack(0);
            }      
        }else if(binaryOp.equals(">")){
            if(second > top){
                vm.pushRunStack(1); 
            }else{
                vm.pushRunStack(0);
            }      
        }else if(binaryOp.equals(">=")){
            if(second >= top){
                vm.pushRunStack(1); 
            }else{
                vm.pushRunStack(0);
            }      
        }else if(binaryOp.equals("<")){
            if(second < top){
                vm.pushRunStack(1); 
            }else{
                vm.pushRunStack(0);
            }      
        }else if(binaryOp.equals("|")){
            if(second==0 && top==0){
                vm.pushRunStack(0); 
            }else{
                vm.pushRunStack(1);
            }      
        }else if(binaryOp.equals("&")){
             if(second==1 && top==1){
                vm.pushRunStack(1); 
            }else{
                vm.pushRunStack(0);
            }      
        }          
    }
    
    public String toString(){
        return "BOP " + binaryOp;
    }
}
