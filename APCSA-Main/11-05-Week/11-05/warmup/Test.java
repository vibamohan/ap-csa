import java.util.Random;

public class Test {
  private Random rand = new Random();

  public void randomCount() {
    int num = rand.nextInt(11) + 10;
    System.out.println("Counting from 1 to " + num + ":");
    for (int i = 1; i <= num; i++) {
      System.out.print(i + " ");
    }
    System.out.println("\n");
  }

  public void randomSquare() {
    int side = rand.nextInt(6) + 5;
    System.out.println("Drawing a square with side length " + side + ":");
    for (int i = 0; i < side; i++) {
      for (int j = 0; j < side; j++) {
        System.out.print("* ");
      }
      System.out.println();
    }
    System.out.println();
  }
}
