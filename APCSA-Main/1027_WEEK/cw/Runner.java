public class Runner {
  public static void main(String[] args) {
    ForTest test = new ForTest();

    System.out.println("Counting from 1 to 100:");
    test.printCount();

    System.out.println("\nCounting from 1 to 10:");
    test.printCount(10);

    System.out.println("\nCounting from 5 to 20:");
    test.printCount(5, 20);

    System.out.println("\nFactorial of 5:");
    int fact = test.getFactorial(5);
    System.out.println(fact);

    System.out.println("\nDrawing a box:");
    test.drawBox();
  }
}
