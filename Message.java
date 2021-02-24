/* 
    * CS 341-1 Parallel Computing and Distributed Systems
    * Prof Ahmed Khaled
    *
    * Submission from team:
    *   Obsmara Ulloa
    *   Sebin Puthenthara Suresh
    *
    *  Task 1 - Message Class
*/
public class Message {

    public Instruction[] instructions;

    // Constructor: Takes a user input and creates the instructions array
    // The inputted string will be of the format:
    // "Add: 1,2,-3; Remove: 2;" -> createMessage(): returns Instruction[]
    // "Add:1,2,-3;Remove:2"
    public Message(String userInput){
        userInput = userInput.replace(" ", "");
        String[] commands = userInput.split(";");   // {"Add:1,2,-3", "Add:1,2,-3", "",..}

        // get number of instructions and create array of that size
        int numInstructions = commands.length;
        if(commands[numInstructions - 1].equals(""))
            numInstructions -= 1;
        this.instructions = new Instruction[numInstructions];

        int i = 0;
        for (String command: commands){
            this.instructions[i] = new Instruction(command);
            i++;
        }
    }
}
