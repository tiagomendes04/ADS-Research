```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = "Hello, World!";
        String output = compress(input);
        System.out.println("Compressed: " + output);
        String decompressed = decompress(output);
        System.out.println("Decompressed: " + decompressed);
    }

    public static String compress(String input) {
        String output = input;
        return compress(output, 0, 0);
    }

    public static String compress(String input, int offset, int count) {
        if (input.isEmpty()) {
            return "";
        }
        if (count == 0) {
            return input;
        }
        if (offset >= input.length()) {
            return input;
        }
        char[] array = input.toCharArray();
        char[] output = new char[array.length - count];
        for (int i = 0; i < array.length; i++) {
            if (i < count) {
                output[i] = array[i];
            } else {
                output[i] = array[i - count];
            }
        }
        return new String(output);
    }

    public static String decompress(String input) {
        return decompress(input, 0, 0);
    }

    public static String decompress(String input, int offset, int count) {
        if (input.isEmpty()) {
            return "";
        }
        if (count == 0) {
            return input;
        }
        if (offset >= input.length()) {
            return input;
        }
        String output = input.substring(offset, offset + count);
        char[] array = output.toCharArray();
        for (int i = 0; i < array.length; i += 2) {
            if (i + 1 < array.length) {
                array[i] = (char) (array[i] + 1);
            }
        }
        return new String(array);
    }
}
```