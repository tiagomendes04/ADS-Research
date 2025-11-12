```java
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class RemoteControl {
    private static final int PORT = 5000;
    private static final String IP_ADDRESS = "127.0.0.1";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter device ID:");
        int deviceID = scanner.nextInt();

        try {
            InetAddress serverAddress = InetAddress.getByName(IP_ADDRESS);
            RemoteControlServer server = new RemoteControlServer(serverAddress, PORT);
            server.start();
            System.out.println("Device " + deviceID + " connected.");
            while (true) {
                System.out.println("Enter command (on/off/brightness):");
                String command = scanner.nextLine();
                server.sendCommand(deviceID, command);
                System.out.println("Command sent to device " + deviceID + ".");
            }
        } catch (UnknownHostException e) {
            System.err.println("Host not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

class RemoteControlServer {
    private final InetAddress serverAddress;
    private final int port;
    private Thread serverThread;

    public RemoteControlServer(InetAddress serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void start() {
        serverThread = new Thread(() -> {
            try {
                System.out.println("Starting server...");
                serverThread.join();
                System.out.println("Server stopped.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        });
        serverThread.start();
    }

    public void sendCommand(int deviceID, String command) throws Exception {
        // Implementation for sending commands to the device
    }
}
```

```java
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class RemoteControlDevice {
    private final int port;
    private final String ipAddress;
    private final DatagramSocket socket;

    public RemoteControlDevice(int port, String ipAddress) {
        this.port = port;
        this.ipAddress = ipAddress;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Error creating socket: " + e.getMessage());
        }
    }

    public void sendCommand(String command) {
        try {
            byte[] buffer = command.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ipAddress), port);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("Error sending command: " + e.getMessage());
        }
    }
}
```