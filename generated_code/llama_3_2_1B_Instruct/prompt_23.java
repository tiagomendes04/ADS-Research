```java
// CoreApplication.java

package com.example.core;

public class CoreApplication {
    private static CoreApplication instance;

    private CoreApplication() {}

    public static CoreApplication getInstance() {
        if (instance == null) {
            instance = new CoreApplication();
        }
        return instance;
    }

    public void run() {
        // Core application code
    }
}
```