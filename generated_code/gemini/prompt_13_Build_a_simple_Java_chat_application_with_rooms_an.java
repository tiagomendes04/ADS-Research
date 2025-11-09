```java
 import java.io.*;
 import java.net.*;
 import java.util.*;
 import java.util.concurrent.*;
 
 public class ChatServer {
 
     private static final int PORT = 12345;
     private static final Map<String, Set<ClientHandler>> rooms = new ConcurrentHashMap<>();
     private static final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);
 
     public static void main(String[] args) throws IOException {
         try (ServerSocket serverSocket = new ServerSocket(PORT)) {
             System.out.println("Chat Server started on port " + PORT);
 
             while (true) {
                 Socket clientSocket = serverSocket.accept();
                 System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                 ClientHandler client = new ClientHandler(clientSocket);
                 clientProcessingPool.submit(client);
             }
         }
     }
 
     private static class ClientHandler implements Runnable {
         private final Socket clientSocket;
         private PrintWriter out;
         private BufferedReader in;
         private String nickname;
         private String currentRoom;
 
         public ClientHandler(Socket socket) {
             this.clientSocket = socket;
         }
 
         @Override
         public void run() {
             try {
                 out = new PrintWriter(clientSocket.getOutputStream(), true);
                 in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 
                 out.println("Welcome to the Chat Server!");
                 out.println("Please enter your nickname:");
 
                 nickname = in.readLine();
                 out.println("Welcome, " + nickname + "!");
 
                 while (true) {
                     out.println("Available commands: /join <room>, /leave, /list, /nick <new_nickname>, /quit");
                     String command = in.readLine();
 
                     if (command == null) {
                         break; // Client disconnected
                     }
 
                     if (command.startsWith("/")) {
                         processCommand(command);
                     } else {
                         if (currentRoom != null) {
                             broadcastMessage(currentRoom, nickname + ": " + command);
                         } else {
                             out.println("You are not in any room. Use /join <room> to join a room.");
                         }
                     }
                 }
 
             } catch (IOException e) {
                 System.err.println("Client error: " + e.getMessage());
             } finally {
                 cleanup();
             }
         }
 
         private void processCommand(String command) throws IOException {
             if (command.startsWith("/join ")) {
                 String roomName = command.substring(6).trim();
                 joinRoom(roomName);
             } else if (command.equals("/leave")) {
                 leaveRoom();
             } else if (command.equals("/list")) {
                 listRooms();
             } else if (command.startsWith("/nick ")) {
                 String newNickname = command.substring(6).trim();
                 changeNickname(newNickname);
             } else if (command.equals("/quit")) {
                 out.println("Goodbye!");
                 cleanup();
             } else {
                 out.println("Invalid command.");
             }
         }
 
         private void joinRoom(String roomName) {
             if (currentRoom != null) {
                 leaveRoom();
             }
 
             rooms.computeIfAbsent(roomName, k -> ConcurrentHashMap.newKeySet()).add(this);
             currentRoom = roomName;
             out.println("Joined room: " + roomName);
             broadcastMessage(roomName, nickname + " has joined the room.");
         }
 
         private void leaveRoom() {
             if (currentRoom != null) {
                 rooms.get(currentRoom).remove(this);
                 broadcastMessage(currentRoom, nickname + " has left the room.");
                 currentRoom = null;
                 out.println("Left the room.");
             } else {
                 out.println("You are not in any room.");
             }
         }
 
         private void listRooms() {
             if (rooms.isEmpty()) {
                 out.println("No rooms available.");
             } else {
                 out.println("Available rooms: " + String.join(", ", rooms.keySet()));
             }
         }
 
         private void changeNickname(String newNickname) {
             if (currentRoom != null) {
                 broadcastMessage(currentRoom, nickname + " is now known as " + newNickname);
             }
             nickname = newNickname;
             out.println("Nickname changed to " + nickname);
         }
 
         private void broadcastMessage(String roomName, String message) {
             Set<ClientHandler> clients = rooms.get(roomName);
             if (clients != null) {
                 for (ClientHandler client : clients) {
                     if (client != this) {
                         client.out.println(message);
                     }
                 }
             }
         }
 
         private void cleanup() {
             try {
                 if (currentRoom != null) {
                     leaveRoom();
                 }
                 if (out != null) {
                     out.close();
                 }
                 if (in != null) {
                     in.close();
                 }
                 clientSocket.close();
                 System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
             } catch (IOException e) {
                 System.err.println("Error closing connection: " + e.getMessage());
             }
         }
     }
 }
 ```
 ```java
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
 ```
