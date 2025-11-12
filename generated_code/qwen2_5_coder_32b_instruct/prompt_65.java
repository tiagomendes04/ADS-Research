```java
package geometry;

public class Circle {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}

package geometry;

public class Rectangle {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }
}

package geometry;

public class Square {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    public double getArea() {
        return side * side;
    }

    public double getPerimeter() {
        return 4 * side;
    }
}

package geometry;

public class Triangle {
    private double base;
    private double height;
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    public Triangle(double sideA, double sideB, double sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public double getArea() {
        if (base != 0 && height != 0) {
            return 0.5 * base * height;
        } else {
            double s = (sideA + sideB + sideC) / 2;
            return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
        }
    }

    public double getPerimeter() {
        if (sideA != 0 && sideB != 0 && sideC != 0) {
            return sideA + sideB + sideC;
        } else {
            throw new IllegalArgumentException("Sides of the triangle must be provided to calculate perimeter.");
        }
    }
}
```