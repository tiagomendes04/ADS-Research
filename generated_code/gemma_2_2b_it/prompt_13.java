```java
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    private static final int PORT = 8080;
    private static final Map<String, Socket> rooms = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            System.out.println("New client connected: " + clientSocket);
            
            // Get client's room name
            String roomName = readRoomName(clientSocket);
            System.out.println("Room: " + roomName);

            // Add client to the room
            rooms.put(roomName, clientSocket);
            
            // Broadcast a welcome message to the room
            broadcastMessage(roomName, "Welcome to the room!");

            // Read messages from the client and broadcast to all in the room
            while (true) {
                String message = readMessage(clientSocket);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                broadcastMessage(roomName, message);
            }

            // Remove the client from the room
            rooms.remove(roomName);

        } catch (Exception e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

    private static String readRoomName(Socket clientSocket) throws Exception {
        // TODO: Implement reading room name from client
        return "";
    }

    private static String readMessage(Socket clientSocket) throws Exception {
        // TODO: Implement reading message from client
        return "";
    }

    private static void broadcastMessage(String roomName, String message) {
        // TODO: Implement broadcasting message to all clients in the room
    }
}
```