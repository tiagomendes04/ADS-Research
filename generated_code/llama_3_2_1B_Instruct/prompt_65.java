```java
import java.util.Arrays;

public class GeometricShapeCalculator {
    public static double areaCircle(double radius) {
        return Math.PI * Math.pow(radius, 2);
    }

    public static double areaTriangle(double side1, double side2, double side3) {
        return Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2) - 2 * side1 * side2 * Math.cos(Math.toRadians(90 - Math.toRadians(60)) + Math.toRadians(90 - Math.toRadians(45))));
    }

    public static double perimeterCircle(double radius) {
        return 2 * Math.PI * radius;
    }

    public static double perimeterTriangle(double side1, double side2, double side3) {
        double perimeter = side1 + side2 + side3;
        return perimeter;
    }

    public static double distanceBetweenPoints(double[] point1, double[] point2) {
        return Math.sqrt(Math.pow(point2[0] - point1[0], 2) + Math.pow(point2[1] - point1[1], 2));
    }

    public static double distanceBetweenSides(double side1, double side2) {
        return Math.sqrt(Math.pow(side1 - side2, 2));
    }

    public static double calculateArea(double[][] points) {
        return areaCircle(Math.sqrt(Math.pow(points[0][0] - points[1][0], 2) + Math.pow(points[0][1] - points[1][1], 2)));
    }

    public static double calculatePerimeter(double[][] points) {
        double perimeter = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                perimeter += Math.sqrt(Math.pow(points[i][0] - points[j][0], 2) + Math.pow(points[i][1] - points[j][1], 2));
            }
        }
        return perimeter;
    }

    public static void main(String[] args) {
        double[][] circlePoints = {{0, 0}, {1, 0}, {0.5, 1}};
        System.out.println("Circle area: " + areaCircle(Math.sqrt(Math.pow(0.5 - 1, 2) + Math.pow(1 - 0, 2))));
        double[] trianglePoints = {{0, 0}, {1, 0}, {0.5, 1}};
        System.out.println("Triangle perimeter: " + perimeterTriangle(Math.sqrt(Math.pow(0.5 - 1, 2) + Math.pow(1 - 0, 2)), Math.sqrt(Math.pow(0.5 - 1, 2) + Math.pow(0 - 1, 2)), Math.sqrt(Math.pow(0.5 - 1, 2) + Math.pow(1 - 1, 2))));
        System.out.println("Distance between points: " + distanceBetweenPoints(new double[]{0, 0}, new double[]{1, 0}));
        System.out.println("Distance between sides: " + distanceBetweenSides(1, 2));
        double[][] circlePoints2 = {{0, 0}, {1, 0}, {0, 1}};
        System.out.println("Circle area: " + areaCircle(Math.sqrt(Math.pow(0, 2) + Math.pow(1, 2))));
        double[] trianglePoints2 = {{0, 0}, {1, 0}, {0.5, 1}};
        System.out.println("Triangle perimeter: " + perimeterTriangle(Math.sqrt(Math.pow(0, 2) + Math.pow(1, 2), Math.pow(0.5 - 1, 2) + Math.pow(1 - 0, 2))));
    }
}
```