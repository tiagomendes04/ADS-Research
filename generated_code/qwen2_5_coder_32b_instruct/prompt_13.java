```java
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static final Map<String, Set<PrintWriter>> rooms = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            new Handler(serverSocket.accept()).start();
        }
    }

    private static class Handler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String roomName;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    out.println("Enter room name:");
                    roomName = in.readLine();
                    if (roomName == null) return;
                    synchronized (rooms) {
                        if (!rooms.containsKey(roomName)) rooms.put(roomName, Collections.newSetFromMap(new ConcurrentHashMap<>()));
                    }
                    rooms.get(roomName).add(out);
                    break;
                }

                while (true) {
                    String input = in.readLine();
                    if (input == null) return;
                    for (PrintWriter writer : rooms.get(roomName)) {
                        writer.println(input);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) rooms.get(roomName).remove(out);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

```java
import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(() -> {
            String input;
            try {
                while ((input = in.readLine()) != null) {
                    System.out.println(input);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
        }
    }
}
```