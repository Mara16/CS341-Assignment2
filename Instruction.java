/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 *  Task 1 - instruction thingy
 * */

public class Instruction {
    
    public static int cmdCount = 0;  // static variable
    public int num;
    public String command;
    
    public Instruction(String command){
        Instruction.cmdCount++;
        this.num = Instruction.cmdCount;
        this.command = command;
    }

}
