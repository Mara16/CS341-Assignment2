/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 *  Task 1 - instruction class
 * */

public class Instruction {

    public static int cmdCount = 0; // static variable
    public int num;
    public String command;

    // Default constructor used for sending commands
    public Instruction(String command) {
        Instruction.cmdCount++;
        this.num = Instruction.cmdCount;
        this.command = command;
    }

    // Overloaded constructor for sending responses
    public Instruction(String command, int responseNum) {
        this.num = responseNum;
        this.command = command;
    }

}
