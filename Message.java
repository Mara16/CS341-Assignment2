/* 
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 * Message Class
 *  1. Message class contains one or more instructions in an array
 * 
 *  2. JSON String of the Message class looks like:
 *      {
 *          "instructions":	
 *              [
 *                  {
 *                      "num": 1,
 *                      "command": "Add:1",	
 *                  },
 *                  {
 *                      "num": 2,
 *                      "command": "Add:3,6,-14;Remove:2;",
 *                  },
 *              ]
 *      }
 * 
 *  3. There are two constructors:
 *     a. Constructor to create a Message object given the number of instructions
 *        it would have.
 *     b. Constructor to create a Message object given an input string containing
 *        commands. The instructions[] array is filled in with appropriate 
 *        messages
*/
public class Message {

    public Instruction[] instructions;

    // Constructor to create a Message object with an empty instructions array
    public Message(int numInstructions) {
        this.instructions = new Instruction[numInstructions];
    }

    // Constructor: Takes a user input and creates the instructions array
    // The inputted string will be of the format:
    // "Add: 1,2,-3; Remove: 2;"
    public Message(String userInput) {
        userInput = userInput.replace(" ", ""); // Remove all spaces
        String[] commands = userInput.split(";");

        // calculate number of instructions and Instruction[] instruction array to that
        // size
        int numInstructions = commands.length;
        if (commands[numInstructions - 1].equals(""))
            numInstructions -= 1;
        this.instructions = new Instruction[numInstructions];

        // Add each command in String[] commands array to Instruction[] instruction
        // arrays
        int i = 0;
        for (String command : commands) {
            this.instructions[i] = new Instruction(command);
            i++;
        }
    }

    // toString() method for printing the Java Object
    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < instructions.length; i++) {
            str += this.instructions[i].toString() + " \n";
            //System.out.println("instruction[" + i + "] is: " + this.instructions[i]);
        }

        return str;
    }
}
