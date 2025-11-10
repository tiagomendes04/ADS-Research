```java
import java.util.Scanner;

public class ShapeCalculator {

    public static double calculateArea(String shape) {
        switch (shape) {
            case "rectangle":
                return calculateRectangleArea();
            case "circle":
                return calculateCircleArea();
            default:
                return -1;
        }
    }

    public static double calculatePerimeter(String shape) {
        switch (shape) {
            case "rectangle":
                return calculateRectanglePerimeter();
            case "circle":
                return calculateCirclePerimeter();
            default:
                return -1;
        }
    }

    private static double calculateRectangleArea() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter length: ");
        double length = scanner.nextDouble();
        System.out.print("Enter width: ");
        double width = scanner.nextDouble();
        return length * width;
    }

    private static double calculateRectanglePerimeter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter length: ");
        double length = scanner.nextDouble();
        System.out.print("Enter width: ");
        double width = scanner.nextDouble();
        return 2 * (length + width);
    }

    private static double calculateCircleArea() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter radius: ");
        double radius = scanner.nextDouble();
        return Math.PI * radius * radius;
    }

    private static double calculateCirclePerimeter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter radius: ");
        double radius = scanner.nextDouble();
        return 2 * Math.PI * radius;
    }
}
```