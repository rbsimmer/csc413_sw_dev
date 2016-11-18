package interpreter;

import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

/**
 * @author Robert Simmer CSC 413, Dr. Levine 4/9/2013
 *
 * Holds the runtime stack as well as the frame pointers. It has many methods to
 * facilitate looking up values, storing them, and so on.
 */
public class RunTimeStack {

    private Vector<Integer> runStack;  //holds actual data we're storing
    private Stack<Integer> framePointers;   //segments the data into frames

    public RunTimeStack() {
        runStack = new Vector<Integer>();
        framePointers = new Stack<Integer>();
        framePointers.push(0);  //initial entry value representing the start of the main function
    }

    public void dump() {
        Iterator iter = framePointers.iterator();
        int nextFrame, currentFrame = (Integer) iter.next();
        //print contents of runtime stack one frame at a time
        for (int i = 0; i < framePointers.size(); i++) {
            if (iter.hasNext()) {
                nextFrame = (Integer) iter.next();
            } else {
                nextFrame = runStack.size();
            }

            System.out.print("[");
            //print contents of current frame
            for (int j = currentFrame; j < nextFrame; j++) {
                System.out.print(runStack.get(j));
                if (j != nextFrame - 1) {
                    System.out.print(",");
                }
            }
            System.out.print("]");
            currentFrame = nextFrame;
        }
        System.out.println();
    }

    public int peek() {
        return runStack.lastElement();
    }

    public int pop() {
        int temp = runStack.lastElement();
        runStack.remove(runStack.size() - 1);
        return temp;
    }

    public int push(int i) {
        runStack.add(i);
        return i;
    }

    /**
     * start a new frame
     *
     * @param offset indicates the number of slot down from the top of runtime
     * stack for starting the new frame
     */
    public void newFrameAt(int offset) {
        framePointers.push(this.runStack.size() - offset);
    }

    /**
     * popFrame pops the top frame when we return from a function; before
     * popping, the function's return value is at the top of the stack so we'll
     * save the value, pop the top frame and then push the return value
     */
    public void popFrame() {
        int temp = this.peek();
        int temp2 = framePointers.pop();
        for (int i = runStack.size() - 1; i >= temp2; i--) {
            this.pop();
        }
        this.push(temp);
    }

    public int store(int offset) {
        int temp = this.pop();
        runStack.set(framePointers.peek() + offset, temp);
        return temp;
    }

    public int load(int offset) {
        int temp = runStack.get(framePointers.peek() + offset);
        runStack.add(temp);
        return temp;
    }

    public Integer push(Integer i) {
        runStack.add(i);
        return i;
    }

    public int getValueAtOffset(int offset) {
        return runStack.elementAt(framePointers.peek() + offset);
    }

    public int getOffset() {
        return runStack.size() - framePointers.peek() - 1;
    }

    public Boolean empty() {
        if (runStack.size() == 0) {
            return true;
        }
        return false;
    }
}
