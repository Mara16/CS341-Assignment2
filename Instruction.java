/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 * Instruction class
 *  1. Has the JSON form:
 *  {
 *      "num": 1,
 *      "command": "Add 1",	
 *  }
 * 
 *  2. Has two constructors:
 *      a. Constructor that takes a command, increases the
 *      total number of commands in the session (static
 *      variable), and sets the num and command values.
 *      This constructor is intended for use by Client.
 * 
 *      b. Constructor that takes a command and a number
 *      This constructor sets the values of num and
 *      command. This constructor is originally intended 
 *      for the Server to use, to send a response back 
 *      to the user.
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

    @Override
    public String toString() {
        return "Command " + num + " " + command;
    }

}
