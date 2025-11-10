```java
// File: src/main/java/com/example/geometry/Shape.java
package com.example.geometry;

public interface Shape {
    double area();
    double perimeter();
}
```

```java
// File: src/main/java/com/example/geometry/Circle.java
package com.example.geometry;

public final class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive.");
        }
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }
}
```

```java
// File: src/main/java/com/example/geometry/Rectangle.java
package com.example.geometry;

public class Rectangle implements Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive.");
        }
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }
}
```

```java
// File: src/main/java/com/example/geometry/Square.java
package com.example.geometry;

public final class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
    }

    public double getSide() {
        return getWidth();
    }
}
```

```java
// File: src/main/java/com/example/geometry/Triangle.java
package com.example.geometry;

public final class Triangle implements Shape {
    private final double a;
    private final double b;
    private final double c;

    public Triangle(double a, double b, double c) {
        if (!isValidTriangle(a, b, c)) {
            throw new IllegalArgumentException("Invalid triangle side lengths.");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private static boolean isValidTriangle(double a, double b, double c) {
        return a > 0 && b > 0 && c > 0 &&
               a + b > c && a + c > b && b + c > a;
    }

    public double getSideA() { return a; }
    public double getSideB() { return b; }
    public double getSideC() { return c; }

    @Override
    public double perimeter() {
        return a + b + c;
    }

    @Override
    public double area() {
        double s = perimeter() / 2.0;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}
```

```java
// File: src/main/java/com/example/geometry/ShapeUtils.java
package com.example.geometry;

import java.util.Collection;

public final class ShapeUtils {
    private ShapeUtils() {}

    public static double totalArea(Collection<? extends Shape> shapes) {
        return shapes.stream().mapToDouble(Shape::area).sum();
    }

    public static double totalPerimeter(Collection<? extends Shape> shapes) {
        return shapes.stream().mapToDouble(Shape::perimeter).sum();
    }
}
```