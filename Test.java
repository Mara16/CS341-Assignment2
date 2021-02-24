/*
 * CS 341-1 Parallel Computing and Distributed Systems
 * Prof Ahmed Khaled
 *
 * Submission from team:
 *   Obsmara Ulloa
 *   Sebin Puthenthara Suresh
 *
 *  Task 1 - This a test file. Ignore. 
 * */

import com.google.gson.*;

public class Test {

    public static void main(String[] args) {
        String testInput = "Add: 10, 20; Remove: 5;";
        Message msg1 = new Message(testInput);

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String jsonMsg = prettyGson.toJson(msg1);
        System.out.println(jsonMsg);

    }
}
