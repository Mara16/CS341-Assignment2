/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 *  Task 1 - Client class
 * */

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {

	public static void main(String args[]) {
		try {
			
			// Create client socket to connect to certain server (Server IP, Port address)
			// we use either "localhost" or "127.0.0.1" if the server runs on the same device as the client
			Socket mySocket = new Socket("127.0.0.1", 6666);


			// to interact (send data / read incoming data) with the server, we need to create the following:
			
			//DataOutputStream object to send data through the socket
			DataOutputStream outStream = new DataOutputStream(mySocket.getOutputStream());

			// BufferReader object to read data coming from the server through the socket
			BufferedReader inStream = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            System.out.print("Your choices of commands (case-sensitive) are:\n" + 
            " - Add: x\n"
            + "   Where x can be any integer value (e.g., \"Add: 74\")\n\n" 
            + " - Remove: x\n"
            + "   Where x can be any integer value (e.g., \"Remove: 2\")\n\n"
            + " - Get_Summation\n\n"
            + " - Exit\n\n"
            + "Enter your command: ");
			
			String statement = "";
			Scanner in = new Scanner(System.in);
			
			while(!statement.equals("exit")) {
			
				statement = in.nextLine();  			// read user input from the terminal data to the server
  
				outStream.writeBytes(statement+"\n");		// send such input data to the server
								
				String str = inStream.readLine();     	// receive response from server

                if(!statement.equals("exit")) {
					System.out.println(str + "\n");                // print this response
					System.out.print("Enter your command: ");
				} else
					System.out.println();

				System.out.println(str);                // print this response
				
			}

			System.out.println("Closing the connection and the sockets");
			
			// close connection.
			outStream.close();
			inStream.close();
            mySocket.close();
            in.close(); // close scanner
		
		} catch (Exception exc) {
			System.out.println("Error is : " + exc.toString());

		}
	}
}