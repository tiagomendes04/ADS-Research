```java
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatApplication {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8000);
            System.out.println("Server started. Listening for incoming connections...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getInetAddress());

                Thread thread = new Thread(new ChatHandler(socket));
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing server socket: " + e.getMessage());
                }
            }
        }
    }
}
```

```java
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatHandler implements Runnable {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Map<String, List<PrintWriter>> rooms;

    public ChatHandler(Socket socket) {
        this.socket = socket;
        this.rooms = new HashMap<>();

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error initializing chat handler: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String username = reader.readLine();
            System.out.println("Username: " + username);

            writer.println("Welcome to the chat application! You are now connected.");

            while (true) {
                String input = reader.readLine();
                if (input.equals("/join")) {
                    String roomName = reader.readLine();
                    joinRoom(roomName, username);
                } else if (input.equals("/leave")) {
                    String roomName = reader.readLine();
                    leaveRoom(roomName, username);
                } else if (input.startsWith("/")) {
                    writer.println("Invalid command. Type /help for available commands.");
                } else {
                    String[] parts = input.split(" ");
                    if (parts.length > 1) {
                        String message = parts[0];
                        String recipient = parts[1];
                        sendPrivateMessage(recipient, username, message);
                    } else {
                        String message = input;
                        String roomName = getRoomName(username);
                        if (roomName != null) {
                            broadcastMessage(roomName, username, message);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling chat handler: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    private void joinRoom(String roomName, String username) {
        if (!rooms.containsKey(roomName)) {
            rooms.put(roomName, new ArrayList<>());
        }
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        rooms.get(roomName).add(writer);
        writer.println("You have joined the " + roomName + " room.");
    }

    private void leaveRoom(String roomName, String username) {
        if (rooms.containsKey(roomName)) {
            rooms.get(roomName).remove(getWriter(roomName, username));
            writer.println("You have left the " + roomName + " room.");
        }
    }

    private void sendPrivateMessage(String recipient, String sender, String message) {
        ChatHandler recipientHandler = getHandler(recipient);
        if (recipientHandler != null) {
            recipientHandler.writer.println(sender + ": " + message);
        }
    }

    private void broadcastMessage(String roomName, String sender, String message) {
        List<PrintWriter> writers = rooms.get(roomName);
        for (PrintWriter writer : writers) {
            writer.println(sender + ": " + message);
        }
    }

    private PrintWriter getWriter(String roomName, String username) {
        return rooms.get(roomName).stream()
                .filter(writer -> writer.getAutoFlush())
                .findFirst()
                .orElse(null);
    }

    private ChatHandler getHandler(String username) {
        for (ChatHandler handler : Thread.getAllStackTraces().keySet()) {
            if (handler instanceof ChatHandler) {
                ChatHandler chatHandler = (ChatHandler) handler;
                if (chatHandler.reader != null && chatHandler.reader.readLine().equals(username)) {
                    return chatHandler;
                }
            }
        }
        return null;
    }

    private String getRoomName(String username) {
        for (Map.Entry<String, List<PrintWriter>> entry : rooms.entrySet()) {
            List<PrintWriter> writers = entry.getValue();
            for (PrintWriter writer : writers) {
                if (writer.getAutoFlush() && writer.println(username).equals(username)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
}
```

```java
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8000);
            System.out.println("Connected to server.");

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread thread = new Thread(new ClientHandler(socket));
            thread.start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                if (input.equals("/quit")) {
                    writer.println("/quit");
                    break;
                }
                writer.println(input);
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }
    }
}
```

```java
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error initializing client handler: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String input;
            while ((input = reader.readLine()) != null) {
                System.out.println(input);
                if (input.startsWith("Welcome to the chat application!")) {
                    writer.println("Hello, server!");
                } else if (input.startsWith("You have joined the")) {
                    String roomName = input.substring(24);
                    System.out.println("You have joined the " + roomName + " room.");
                } else if (input.startsWith("You have left the")) {
                    String roomName = input.substring(22);
                    System.out.println("You have left the " + roomName + " room.");
                } else if (input.startsWith("Invalid command.")) {
                    System.out.println(input);
                } else if (input.startsWith("Hello, ")) {
                    String message = input.substring(7);
                    System.out.println(message);
                } else {
                    String[] parts = input.split(": ");
                    String sender = parts[0];
                    String message = parts[1];
                    System.out.println(sender + ": " + message);
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client handler: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
```