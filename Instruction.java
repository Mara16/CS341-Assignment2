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

    // Static variable that keeps track of 
    // number of commands in session so far.
    public static int cmdCount = 0; 
    
    public int num;         // Instruction number for this Instruction
    public String command;  // The content of this Instruction (command/response to client)

    // Default constructor used for sending commands to server
    public Instruction(String command) {
        Instruction.cmdCount++; // Increase the static variable count
        
        this.num = Instruction.cmdCount;
        this.command = command;
    }

    // Overloaded constructor for sending responses back to Client
    public Instruction(String command, int responseNum) {
        this.num = responseNum;
        this.command = command;
    }

    @Override
    public String toString() {
        return "Command " + num + " " + command;
    }

}
