**ChatServer.java**
```java
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private ServerSocket serverSocket;
    private Map<String, List<Socket>> rooms;

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        rooms = new HashMap<>();
    }

    public void start() throws IOException {
        System.out.println("Server started. Listening for incoming connections...");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Incoming connection from " + socket.getInetAddress());
            Thread thread = new Thread(new ChatHandler(socket, this));
            thread.start();
        }
    }

    public void joinRoom(Socket socket, String room) {
        rooms.computeIfAbsent(room, k -> new ArrayList<>()).add(socket);
    }

    public void leaveRoom(Socket socket, String room) {
        rooms.get(room).remove(socket);
    }

    public void broadcast(String room, String message) {
        rooms.get(room).forEach(socket -> {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
            } catch (IOException e) {
                System.out.println("Error broadcasting message: " + e.getMessage());
            }
        });
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer(8000);
        server.start();
    }
}
```

**ChatHandler.java**
```java
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatHandler implements Runnable {
    private Socket socket;
    private ChatServer server;

    public ChatHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String room = in.readLine();
            server.joinRoom(socket, room);

            String message;
            while ((message = in.readLine()) != null) {
                server.broadcast(room, message);
            }

            server.leaveRoom(socket, room);
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
```

**ChatClient.java**
```java
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ChatClient(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void joinRoom(String room) {
        out.println(room);
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println("Error receiving message: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing socket: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        ChatClient client = new ChatClient("localhost", 8000);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter room name:");
        String room = scanner.nextLine();
        client.joinRoom(room);

        while (true) {
            System.out.println("Enter message:");
            String message = scanner.nextLine();
            client.sendMessage(message);

            String receivedMessage = client.receiveMessage();
            if (receivedMessage != null) {
                System.out.println("Received message: " + receivedMessage);
            }
        }
    }
}
```