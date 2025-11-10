```java
// ChatServer.java
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 12345;
    private final Map<String, ChatRoom> rooms = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        new ChatServer().start();
    }

    private void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Chat server started on port " + PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ClientHandler(socket, this)).start();
        }
    }

    ChatRoom getOrCreateRoom(String name) {
        return rooms.computeIfAbsent(name, ChatRoom::new);
    }

    void removeEmptyRoom(String name) {
        rooms.computeIfPresent(name, (k, v) -> v.isEmpty() ? null : v);
    }
}
```

```java
// ChatRoom.java
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatRoom {
    private final String name;
    private final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    ChatRoom(String name) {
        this.name = name;
    }

    void addClient(ClientHandler client) {
        clients.add(client);
        broadcast("SYSTEM", client.getUserName() + " has joined the room.");
    }

    void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcast("SYSTEM", client.getUserName() + " has left the room.");
    }

    void broadcast(String sender, String message) {
        String fullMsg = "[" + name + "] " + sender + ": " + message;
        for (ClientHandler c : clients) {
            c.send(fullMsg);
        }
    }

    boolean isEmpty() {
        return clients.isEmpty();
    }

    String getName() {
        return name;
    }
}
```

```java
// ClientHandler.java
import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ChatServer server;
    private BufferedReader in;
    private PrintWriter out;
    private String userName = "Anonymous";
    private ChatRoom currentRoom;

    ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    String getUserName() {
        return userName;
    }

    void send(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("WELCOME Enter your name:");
            String line = in.readLine();
            if (line != null && !line.trim().isEmpty()) {
                userName = line.trim();
            }
            out.println("INFO Hello " + userName + "! Use commands: JOIN <room>, MSG <text>, LEAVE, QUIT");
            String input;
            while ((input = in.readLine()) != null) {
                if (input.startsWith("JOIN ")) {
                    joinRoom(input.substring(5).trim());
                } else if (input.startsWith("MSG ")) {
                    sendMessage(input.substring(4).trim());
                } else if (input.equalsIgnoreCase("LEAVE")) {
                    leaveRoom();
                } else if (input.equalsIgnoreCase("QUIT")) {
                    break;
                } else {
                    out.println("ERROR Unknown command");
                }
            }
        } catch (IOException e) {
            // ignore
        } finally {
            cleanup();
        }
    }

    private void joinRoom(String roomName) {
        leaveRoom();
        currentRoom = server.getOrCreateRoom(roomName);
        currentRoom.addClient(this);
        out.println("INFO Joined room: " + roomName);
    }

    private void leaveRoom() {
        if (currentRoom != null) {
            currentRoom.removeClient(this);
            server.removeEmptyRoom(currentRoom.getName());
            out.println("INFO Left room: " + currentRoom.getName());
            currentRoom = null;
        }
    }

    private void sendMessage(String msg) {
        if (currentRoom != null) {
            currentRoom.broadcast(userName, msg);
        } else {
            out.println("ERROR You are not in a room. Use JOIN <room>");
        }
    }

    private void cleanup() {
        leaveRoom();
        try {
            socket.close();
        } catch (IOException ignored) {}
    }
}
```

```java
// ChatClient.java
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        // Thread to read server messages
        Thread reader = new Thread(() -> {
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException ignored) {}
        });
        reader.setDaemon(true);
        reader.start();

        // Initial handshake
        String welcome = in.readLine(); // WELCOME
        System.out.print(welcome + " ");
        String name = console.readLine();
        out.println(name);

        // Main loop
        String userInput;
        while ((userInput = console.readLine()) != null) {
            out.println(userInput);
            if (userInput.equalsIgnoreCase("QUIT")) break;
        }

        socket.close();
    }
}
```