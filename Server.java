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
 * 	 2. Conver jsonMsg
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
            // Create server Socket that listens/bonds to port/endpoint address 6666 (any
            // port id of your choice, should be >=1024, as other port addresses are
            // reserved for system use)
            // The default maximum number of queued incoming connections is 50 (the maximum
            // number of clients to connect to this server)
            // There is another constructor that can be used to specify the maximum number
            // of connections
            ServerSocket mySocket = new ServerSocket(6666);

            System.out.println("Starting the server side over port 6666 ....");

            // use the created ServerSocket and accept() to start listening for incoming
            // client requests targeting this server and this port
            // accept() blocks the current thread (server application) waiting until a
            // connection is requested by a client.
            // the created connection with a client is represented by the returned Socket
            // object.
            Socket connectedClient = mySocket.accept();

            // reaching this point means that a client established a connection with your
            // server and this particular port.
            System.out.println("Connection established");

            // to interact (read incoming data / send data) with the connected client, we
            // need to create the following:

            // BufferReader object to read data coming from the client
            BufferedReader br = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));

            // PrintStream object to send data to the connected client
            PrintStream ps = new PrintStream(connectedClient.getOutputStream());

            // The data layer at the server side is keeping a dynamic list (ArrayList) of
            // integers (initially empty)
            // and named as inputValues to handle (add/remove) data to/from the user.
            ArrayList<Integer> inputValues = new ArrayList<Integer>();

            // Let's keep reading data from the client, as long as the client does't send
            // "exit".

            // Receive jsnMsg
            String clientCmd = "_INITIAL";
            Gson gson = new Gson();

            boolean exitCommandGiven = false;
            do {

                if (!clientCmd.equals("_INITIAL")) {
                    System.out.println("received a message from client: " + clientCmd); // print the incoming data from client
                }

                // 2. Covert JSON string to Message Object
                // String result = processCommand(inputData, inputValues);
                String clientJson = br.readLine();
                System.out.println("Server recieved from client this: " + clientJson);
				Message message = gson.fromJson(clientJson, Message.class);
				

                Instruction[] clInstructions = message.instructions; // message accesses 'instructions' & return  array of Instruction objects
                
                // Message response = new Message(numInstructionsFromUser);    // Response to send back to client
                // message.instruction - > array of Instruction objects
                // Each Instruction object -> has num property
                for (Instruction ins : clInstructions) {
                    String insResult = processCommand(ins.command, inputValues);    // Process the command within this Instruction, get the return value.
                    ins.command = insResult;

                    if(insResult.equals(BYE_MSG))
                        exitCommandGiven = true;

                    // Instruction responseIns = new Instruction("insResult");
                    // responseIns.num = ins.num;
                    // responseIns.command = insResult;
                }

                String responseJson = gson.toJson(message);
                ps.println(responseJson); // respond back to the client

            } while (!exitCommandGiven);

            System.out.println("Closing the connection and the sockets");

            // close the input/output streams and the created client/server sockets
            ps.close();
            br.close();
            mySocket.close();
            connectedClient.close();

        } catch (Exception exc) {
            System.out.println("Error :" + exc.toString());
        }

    }

    private static String processCommand(String inputData, ArrayList<Integer> inputValues) {
        String toReturn = "";
        // Determine what command needs to get processed and handle
		if (inputData.matches("Add[:][-,0-9]+")) {  
			String numsToAddStr = inputData.trim().replace("Add:", "").replace(" ", "");	// -30,40,50
            String[] numsToAdd = numsToAddStr.split(",");

			// in case user tries to add invalid nums to inputValues
			try {
                for(String numStr: numsToAdd){
					int num = Integer.parseInt(numStr.trim());
                    System.out.println("Adding number: ");
					System.out.println(num);
					inputValues.add(num);
				}
                toReturn = "Added successfully";
			} catch (Exception e) {
				toReturn = "Invalid instruction";
			}

			
		} else if (inputData.matches("Remove[:][-,0-9]+")) { // Remove:67,89,7,-4
			System.out.println("Removing numbers...");
			String onlyNums = inputData.trim().replace("Remove:", "").replace(" ", "");
			String[] numsToRemove = onlyNums.split(",");
			
			// check if values to remove are actually in inputValues
			for (String string : numsToRemove) {
				if (!inputValues.contains(Integer.parseInt(string))) {
					return "Unsupported Command. At least one of the integers does not exist in list.";
                }
			}

			// do the actual removal of inputValues
			for (String string : numsToRemove) {
                System.out.println("Removing number: ");
				inputValues.remove(inputValues.indexOf(Integer.parseInt(string)));
			}
			
			toReturn = "Removed Successfully";
			
		} else if (inputData.equals("Get_Summation")) {
            // Find the summation and append to the returned string
            System.out.println("Processing summation...");
            toReturn = "The summation is ";
			int sum = 0;
			for (int num : inputValues) {
				sum += num;
			}
			toReturn += sum;

		} else if (inputData.equals("Sort_A")) {
            System.out.println("Processing sort...");
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
            
		} else if (inputData.trim().toLowerCase().equals("exit")){
            return BYE_MSG;

        } else {
			toReturn = "Unsupported command";
		}
		return toReturn;
    }
}