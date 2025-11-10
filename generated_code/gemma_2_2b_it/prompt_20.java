```java
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DeviceController {

    private final int PORT = 5000;
    private final String IP_ADDRESS = "192.168.1.10"; 

    private DatagramSocket socket;

    public DeviceController() throws SocketException {
        socket = new DatagramSocket(PORT);
    }

    public void sendCommand(String command) {
        byte[] data = command.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(IP_ADDRESS), PORT);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }

}

```

```java
import java.io.IOException;

public class LightDevice {

    public void turnOn() {
        System.out.println("Turning on the light.");
    }

    public void turnOff() {
        System.out.println("Turning off the light.");
    }
}
```


```java
import java.io.IOException;

public class FanDevice {

    public void turnOn() {
        System.out.println("Turning on the fan.");
    }

    public void turnOff() {
        System.out.println("Turning off the fan.");
    }
}
```