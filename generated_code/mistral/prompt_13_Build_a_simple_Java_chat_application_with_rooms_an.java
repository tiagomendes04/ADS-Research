Here's a simple Java chat application with rooms and message broadcasting:

```java
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clients = new HashSet<>();
    private static Map<String, Set<ClientHandler>> rooms = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat Server started on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, String roomName) {
        if (roomName == null) {
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        } else {
            Set<ClientHandler> roomClients = rooms.get(roomName);
            if (roomClients != null) {
                for (ClientHandler client : roomClients) {
                    client.sendMessage(message);
                }
            }
        }
    }

    public static void addToRoom(String username, String roomName) {
        rooms.computeIfAbsent(roomName, k -> new HashSet<>()).add(new ClientHandler(username));
        broadcast(username + " has joined the room: " + roomName, roomName);
    }

    public static void removeFromRoom(String username, String roomName) {
        Set<ClientHandler> roomClients = rooms.get(roomName);
        if (roomClients != null) {
            roomClients.removeIf(client -> client.getUsername().equals(username));
            broadcast(username + " has left the room: " + roomName, roomName);
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private String currentRoom;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public ClientHandler(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("ENTER_USERNAME");
            username = in.readLine();
            out.println("ENTER_ROOM");
            currentRoom = in.readLine();
            ChatServer.addToRoom(username, currentRoom);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/join")) {
                    String roomName = message.substring(6);
                    ChatServer.removeFromRoom(username, currentRoom);
                    currentRoom = roomName;
                    ChatServer.addToRoom(username, currentRoom);
                } else if (message.startsWith("/leave")) {
                    ChatServer.removeFromRoom(username, currentRoom);
                    currentRoom = null;
                } else {
                    ChatServer.broadcast(username + ": " + message, currentRoom);
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + username);
        } finally {
            try {
                if (currentRoom != null) {
                    ChatServer.removeFromRoom(username, currentRoom);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clients.remove(this);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}

class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            String response;
            while ((response = in.readLine()) != null) {
                if (response.equals("ENTER_USERNAME")) {
                    System.out.print("Enter your username: ");
                    String username = consoleInput.readLine();
                    out.println(username);
                } else if (response.equals("ENTER_ROOM")) {
                    System.out.print("Enter room name: ");
                    String room = consoleInput.readLine();
                    out.println(room);
                } else {
                    System.out.println(response);
                }

                if (response.equals("ENTER_USERNAME") || response.equals("ENTER_ROOM")) {
                    continue;
                }

                String message = consoleInput.readLine();
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```