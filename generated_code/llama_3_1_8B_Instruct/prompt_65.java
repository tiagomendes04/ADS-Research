```java
public class GeometricShape {
    public interface Shape {
        double area();
        double perimeter();
    }

    public static class Circle implements Shape {
        private double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public double area() {
            return Math.PI * Math.pow(radius, 2);
        }

        @Override
        public double perimeter() {
            return 2 * Math.PI * radius;
        }
    }

    public static class Rectangle implements Shape {
        private double width;
        private double height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
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

    public static class Triangle implements Shape {
        private double side1;
        private double side2;
        private double side3;

        public Triangle(double side1, double side2, double side3) {
            this.side1 = side1;
            this.side2 = side2;
            this.side3 = side3;
        }

        @Override
        public double area() {
            // Heron's formula
            double semiPerimeter = (side1 + side2 + side3) / 2;
            return Math.sqrt(semiPerimeter * (semiPerimeter - side1) * (semiPerimeter - side2) * (semiPerimeter - side3));
        }

        @Override
        public double perimeter() {
            return side1 + side2 + side3;
        }
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        GeometricShape.Circle circle = new GeometricShape.Circle(5);
        System.out.println("Circle Area: " + circle.area());
        System.out.println("Circle Perimeter: " + circle.perimeter());

        GeometricShape.Rectangle rectangle = new GeometricShape.Rectangle(4, 6);
        System.out.println("Rectangle Area: " + rectangle.area());
        System.out.println("Rectangle Perimeter: " + rectangle.perimeter());

        GeometricShape.Triangle triangle = new GeometricShape.Triangle(3, 4, 5);
        System.out.println("Triangle Area: " + triangle.area());
        System.out.println("Triangle Perimeter: " + triangle.perimeter());
    }
}
```