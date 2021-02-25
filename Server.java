/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 *  Task 1 - Server Class 
 *   1. Establish connection with client 
 * 	 2. Recieve client's commands and take appropriate action
 *   3. Send reply back to client as a JSON String
 * 
 * */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import com.google.gson.*;

class Server {

    public static final String BYE_MSG = "Goodbye!";

    public static void main(String args[]) {
        try {
            
            int port = 6666;
            ServerSocket mySocket = new ServerSocket(port);

            System.out.println("Starting the server side over port " + port + " ....");

            // use the created ServerSocket and accept() to start listening for incoming
            // client requests targeting this server and this port
            Socket connectedClient = mySocket.accept();
            System.out.println("Connection established");

            // BufferReader object to read data coming from the client
            BufferedReader br = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));

            // PrintStream object to send data to the connected client
            PrintStream ps = new PrintStream(connectedClient.getOutputStream());

            // The data layer at the server side is keeping a dynamic list (ArrayList) of
            // integers (initially empty) named as inputValues to handle (add/remove) data
            // to/from the user.
            ArrayList<Integer> inputValues = new ArrayList<Integer>();

            String clientCmd = "_INITIAL";  // initial value for the loop control variable
            Gson gson = new Gson();         // GSON Parser - To convert JSON strings to Java and back

            boolean exitCommandGiven = false;
            do {
                
                // print the incoming data from client
                if (!clientCmd.equals("_INITIAL")) {
                    System.out.println("received a message from client: " + clientCmd); 
                }

                // 2. Covert JSON string to Message Object
                String clientJson = br.readLine();
                System.out.println("Server recieved from client this: " + clientJson);
                Message message = gson.fromJson(clientJson, Message.class);
                
                // The array of Instruction objects that's contained in a Message object
                Instruction[] clInstructions = message.instructions; 
                
                // 1. Iterate over each Instruction object from client
                // 2. Process each command
                // 3. Get the results of each command
                // 4. Re-use the Instruction object to store the response to Client
                //    This ensures that the response has the correct 'num' value.
                // 5. If the result is the BYE_MSG, then prepare for exiting the loop.
                for (Instruction ins : clInstructions) {

                    // Process the command within this Instruction, get the return value.
                    String insResult = processCommand(ins.command, inputValues);    
                    
                    // Set the response message
                    ins.command = insResult;

                    // Prepare for exiting the loop
                    if(insResult.equals(BYE_MSG))
                        exitCommandGiven = true;
                }

                // Convert the response Message object to JSON String
                String responseJson = gson.toJson(message);

                // Send this response back to client
                ps.println(responseJson); 

            } while (!exitCommandGiven);    // Continue the loop until Client sends "Exit"

            System.out.println("Closing the connection and the sockets");

            // Close the input/output streams and the created client/server sockets
            ps.close();
            br.close();
            mySocket.close();
            connectedClient.close();

        } catch (Exception exc) {
            System.out.println("Error :" + exc.toString());
        }

    }

    // Method that processes a given single command string and modifies the ArrayList accordingly
    // or reply with BYE_MSG
    private static String processCommand(String inputCommand, ArrayList<Integer> inputValues) {

        String toReturn = "";
        
        // Determine what command needs to get processed and handle
        if (inputCommand.matches("Add[:][-,0-9]+")) {
            // Remove the non-numeric part of the command to get the numbers to be added  
            String numsToAddStr = inputCommand.trim().replace("Add:", "").replace(" ", "");

            // Get an array of String versions of the numbers to be added (ideally)
            String[] numsToAdd = numsToAddStr.split(",");

            // In case user tries to add invalid nums to inputValues
            try {
                for(String numString: numsToAdd){
                    int num = Integer.parseInt(numString.trim());
                    System.out.println("Adding number: " + numString);
                    inputValues.add(num);
                }
                toReturn = "Added successfully.";
                
            } catch (Exception e) {
                toReturn = "Invalid instruction in Add method";
            }

            
        } else if (inputCommand.matches("Remove[:][-,0-9]+")) {

            System.out.println("Removing numbers...");

            // Remove the non-numeric part of the command to get the numbers to be removed
            String onlyNums = inputCommand.trim().replace("Remove:", "").replace(" ", "");

            // Get an array of String versions of the numbers to be removed
            String[] numsToRemove = onlyNums.split(",");
            
            // Check if values to remove are actually in inputValues list
            for (String string : numsToRemove) {
                if (!inputValues.contains(Integer.parseInt(string))) {
                    return "invalid instruction in Remove method." + 
                        " At least one of the integers do not exist in list.";
                }
            }

            // do the actual removal of inputValues
            for (String numString : numsToRemove) {
                System.out.println("Removing number: " + numString);
                inputValues.remove(inputValues.indexOf(Integer.parseInt(numString)));
            }
            
            toReturn = "Removed Successfully";
            
        } else if (inputCommand.equals("Get_Summation")) {
            System.out.println("Processing summation...");
            
            // Find the summation and append to the returned string
            toReturn = "The summation is ";
            int sum = 0;
            for (int num : inputValues) {
                sum += num;
            }
            toReturn += sum;

        } else if (inputCommand.equals("Sort_A")) {
            System.out.println("Processing sort...");

            // Sort the array and append the values to the returned string
            Collections.sort(inputValues);
            toReturn = "The sorted list is: { ";
            for (int number : inputValues) {
                toReturn += number + ""; 
                if (inputValues.size() > 1)
                    toReturn += ", ";
            }
            if (inputValues.size() > 1)
                toReturn = toReturn.substring(0, toReturn.length() - 2);  // removes the last comma.

            toReturn += " }";
            
        } else if (inputCommand.trim().toLowerCase().equals("exit")){
            return BYE_MSG;

        } else {
            toReturn = "Unsupported command";
        }
        
        return toReturn;
    }
}