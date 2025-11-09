public class TrafficLight {
    public enum Color { RED, GREEN, YELLOW }

    private Color color;

    public TrafficLight(Color color) {
        this.color = color;
    }

    public void update() {
        System.out.println("Traffic light color is " + color);
    }

    public void changeColor(Color color) {
        this.color = color;
    }
}