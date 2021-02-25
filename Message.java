/* 
    * CS 341-1 Parallel Computing and Distributed Systems
    * Prof Ahmed Khaled
    *
    * Submission from team:
    *   Obsmara Ulloa
    *   Sebin Puthenthara Suresh
    *
    *  Message Class
*/
public class Message {

    public Instruction[] instructions;

    // Constructor to create a Message object with an empty instructions array
    public Message(int numInstructions) {
        this.instructions = new Instruction[numInstructions];
    }

    // Constructor: Takes a user input and creates the instructions array
    // The inputted string will be of the format:
    // "Add: 1,2,-3; Remove: 2;" -> createMessage(): returns Instruction[]
    // "Add:1,2,-3;Remove:2"
    public Message(String userInput) {
        userInput = userInput.replace(" ", "");
        String[] commands = userInput.split(";"); // {"Add:1,2,-3", "Add:1,2,-3", "",..}

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
        String str = "start of toString...";

        for (int i = 0; i < instructions.length; i++) {
            str += this.instructions[i] + "";
        }

        return str;
    }
}
