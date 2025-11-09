import java.io.*;
 import java.net.*;
 import java.util.Scanner;
 
 public class ChatClient {
 
     private static final String SERVER_ADDRESS = "localhost";
     private static final int SERVER_PORT = 12345;
 
     public static void main(String[] args) {
         try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              Scanner scanner = new Scanner(System.in)) {
 
             System.out.println("Connected to the Chat Server.");
 
             // Read welcome messages from the server
             String line;
             while ((line = in.readLine()) != null) {
                 System.out.println(line);
                 if (line.startsWith("Welcome,")) {
                     break; // Stop reading initial messages after receiving welcome message
                 }
             }
 
             // Start a separate thread to read messages from the server
             new Thread(() -> {
                 try {
                     String serverMessage;
                     while ((serverMessage = in.readLine()) != null) {
                         System.out.println(serverMessage);
                     }
                 } catch (IOException e) {
                     System.err.println("Error reading from server: " + e.getMessage());
                 }
             }).start();
 
             // Read user input and send it to the server
             String userInput;
             while (true) {
                 userInput = scanner.nextLine();
                 out.println(userInput);
                 if (userInput.equals("/quit")) {
                     break;
                 }
             }
 
         } catch (IOException e) {
             System.err.println("Could not connect to the server: " + e.getMessage());
         }
     }
 }