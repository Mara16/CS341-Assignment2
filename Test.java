/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 *  This a test file. Ignore. 
 * */

import com.google.gson.*;

public class Test {

    public static void main(String[] args) {
        String testInput = "Add: 10, 20; Remove: 5;";
        Message msg1 = new Message(testInput);

        System.out.println();
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String jsonMsg = prettyGson.toJson(msg1);
        
        
        for (int i = 30; i < 38; i++) {
            System.out.println("\033[0;" + i + "m");
            System.out.println("\\033[0;" + i + "m");
            System.out.println(jsonMsg);
        }

        // Blue "\033[0;36m"
        // Green "\033[0;32m"
        // NEON yellow "\033[0;33m"
        // RESET "\033[0m"
    }
}
