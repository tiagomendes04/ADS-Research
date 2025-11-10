```java
public class GeometryLibrary {

    public static double areaOfCircle(double radius) {
        return Math.PI * radius * radius;
    }

    public static double perimeterOfCircle(double radius) {
        return 2 * Math.PI * radius;
    }

    public static double areaOfRectangle(double length, double width) {
        return length * width;
    }

    public static double perimeterOfRectangle(double length, double width) {
        return 2 * (length + width);
    }

    public static double areaOfTriangle(double base, double height) {
        return 0.5 * base * height;
    }

    public static double perimeterOfTriangle(double side1, double side2, double side3) {
        return side1 + side2 + side3;
    }

    public static double areaOfSquare(double side) {
        return side * side;
    }

    public static double perimeterOfSquare(double side) {
        return 4 * side;
    }
}
```