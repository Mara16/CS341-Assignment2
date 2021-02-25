/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 *  Client Class
 * 	1. Initiates a connection with the server 
 *  2. Sends user input from Client to server as JSON
 * 	3. receive response from server and display to user
 * */

import java.io.*;
import java.net.*;
import java.util.Scanner;
import com.google.gson.*;

class Client {

    public static void main(String args[]) {
        try {

            Socket mySocket = new Socket("127.0.0.1", 6666);

            DataOutputStream outStream = new DataOutputStream(mySocket.getOutputStream());

            BufferedReader inStream = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

			String divider = "\n✼ •• ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈ •• ✼\n";
			System.out.println(divider);
			String cmdOptions = "Your choices of commands (case-sensitive) are:\n" 
				+ " - Add: x\n"
				+ "   Where x can be any integer value (e.g., \"Add: 74\")\n\n" 
				+ " - Remove: x\n"
				+ "   Where x can be any integer value (e.g., \"Remove: 2\")\n\n" 
				+ " - Get_Summation\n\n"
				+ " - Sort_A\n\n" 
				+ " - Exit\n\n" 
				+ " NOTE: Commands may also be chained when seperated by \";\" (e.g., \"Add: 74, 9, 2; Get_Summation\")\n\n"  
				+ " - Enter your command: ";
					
			String statement = "";
            Scanner in = new Scanner(System.in);
            Gson gson = new Gson();

            while (!statement.equals("Exit")) {

				System.out.print(cmdOptions);

                // read user input from teminal & create Java Message object
                statement = in.nextLine(); 
                Message msg = new Message(statement);

                // Convert Message object to JSON string
                String jsonMsg = gson.toJson(msg);
                //System.out.println("this is ur json msg that will be sent to server: \n" + jsonMsg);

                // Send the jsonMsg command over the socket to server
                outStream.writeBytes(jsonMsg + "\n"); 

                // receive JSON string response from server
				String str = inStream.readLine();

				// Convert that JSON String to a Java Object 
				Message objFromJsn = gson.fromJson(str, Message.class);

				//System.out.println("\nConverting from JSON back to Java Object...\n");
				str = objFromJsn.toString();
				//System.out.println(objFromJsn.toString());

                if (!statement.equals("Exit")) {
                    System.out.println("\n" +str + "\n" + divider); // print this response                    
                } else
                    System.out.println("\n" +str + "\n"); // Good bye msg

                // System.out.println(str); // print this response
            }

            System.out.println("Closing the connection and the sockets");

            // close connections and scanner
            outStream.close();
            inStream.close();
            mySocket.close();
            in.close(); 
        } catch (Exception exc) {
            System.out.println("Error is : " + exc.toString());

        }
    }
}