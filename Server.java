/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 *  Task 1 - server class
 * */

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Server {

	public static void main(String args[]) {
		try {
			// Create server Socket that listens/bonds to port/endpoint address 6666 (any port id of your choice, should be >=1024, as other port addresses are reserved for system use)
			// The default maximum number of queued incoming connections is 50 (the maximum number of clients to connect to this server)
			// There is another constructor that can be used to specify the maximum number of connections
			ServerSocket mySocket = new ServerSocket(6666);

			
			System.out.println("Startup the server side over port 6666 ....");

			// use the created ServerSocket and accept() to start listening for incoming client requests targeting this server and this port
			// accept() blocks the current thread (server application) waiting until a connection is requested by a client.
			// the created connection with a client is represented by the returned Socket object.
			Socket connectedClient = mySocket.accept();
			

			// reaching this point means that a client established a connection with your server and this particular port.
			System.out.println("Connection established");

			
			// to interact (read incoming data / send data) with the connected client, we need to create the following:

			// BufferReader object to read data coming from the client
			BufferedReader br = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));

			// PrintStream object to send data to the connected client
			PrintStream ps = new PrintStream(connectedClient.getOutputStream());

            // The data layer at the server side is keeping a dynamic list (ArrayList) of integers (initially empty) 
            // and named as inputValues to handle (add/remove) data to/from the user.
            ArrayList<Integer> inputValues = new ArrayList<Integer>();
			
			// Let's keep reading data from the client, as long as the client does't send "exit".
			String inputData;
			while (!(inputData = br.readLine()).equals("exit")) {    
				
				System.out.println("received a message from client: " + inputData);   //print the incoming data from the client
                

                String result = processCommand(inputData, inputValues); 

				ps.println(result);    //respond back to the client
				
			}
			
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
		
		// All the if and else if cases check if the input string matches one of the commands		
		if(inputData.matches("add[:][ ][-]{0,1}[0-9]+")) {
			int num = Integer.parseInt(inputData.trim().replace("add: ", ""));	//Extract the number from the input string
            inputValues.add(num);
			// Do nothing, return ""
			
		} else if(inputData.matches("remove[:][ ][-]{0,1}[0-9]+")) {
			int num = Integer.parseInt(inputData.trim().replace("remove: ", ""));	//Extract the number from the input string
			if(inputValues.contains(num))
                inputValues.remove(inputValues.indexOf(num));
			else
				toReturn += "unsupported command. The integer does not exist in list.";
			// Do nothing, return ""
			
		} else if(inputData.equals("get_summation")) {
			// Find the summation and append to the returned string
			int sum = 0;
			for (int num : inputValues) {
				sum += num;
			}
			toReturn += sum;
			
		}  else if(inputData.equals("exit")) {
			// Do nothing, return ""
			
		} else {
			// This case is reached if the command does not match any of the other commands.
			toReturn = "unsupported command";
		}
		
		return toReturn;
	}
}