/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 *  Task 1 - Client Class
 * 	1. Initiates a connection with the server 
 *  2. Sends user input from Client to server as JSON
 * */

import java.io.*;
import java.net.*;
import java.util.Scanner;
import com.google.gson.*;


class Client {

	public static void main(String args[]) {
		try {

			// Create client socket to connect to certain server (Server IP, Port address)
			// we use either "localhost" or "127.0.0.1" if the server runs on the same
			// device as the client
			Socket mySocket = new Socket("127.0.0.1", 6666);

			// to interact (send data / read incoming data) with the server, we need to
			// create the following:

			// DataOutputStream object to send data through the socket
			DataOutputStream outStream = new DataOutputStream(mySocket.getOutputStream());

			// BufferReader object to read data coming from the server through the socket
			BufferedReader inStream = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

			System.out.print("Your choices of commands (case-sensitive) are:\n" +
					" - Add: x\n" + "   Where x can be any integer value (e.g., \"Add: 74\")\n\n" + 
					" - Remove: x\n" + "   Where x can be any integer value (e.g., \"Remove: 2\")\n\n" + 
					" - Get_Summation\n\n" + 
					" - Sort_A\n\n" +
					" - Exit\n\n" + 
					" - Enter your command: ");

			String statement = "";
			Scanner in = new Scanner(System.in);

			while (!statement.equals("Exit")) {
				// 1. Create Java Message object
				statement = in.nextLine(); // read user input from the terminal data to the server
				Message msg = new Message(statement);
				
				// 2. Convert Message object to JSON string
				Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
    			String jsonMsg = prettyGson.toJson(msg);
				System.out.println(jsonMsg);
				// 3. Send the command over the socket using outStream.writeBytes()
				outStream.writeBytes(jsonMsg + "\n"); // send such input data to the server

				// receive response from server
				String str = inStream.readLine(); 

				if (!statement.equals("Exit")) {
					System.out.println(str + "\n"); // print this response
					System.out.print("Enter your command: ");
				} else
					System.out.println();

				// System.out.println(str); // print this response
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