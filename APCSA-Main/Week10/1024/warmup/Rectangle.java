public class Rectangle {
    private double area(double width, double length) {
        return width * length;
    }

    public double volRect(double width, double length, double height) {
        double rectArea = area(width, length);
        double volume = rectArea * height;
        return volume;
    }
}
