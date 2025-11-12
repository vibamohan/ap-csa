public class ForTest {

  public void printCount() {
    printCount(100);
    System.out.println();
  }

  public void printCount(int num) {
    printCount(1, num);
    System.out.println();
  }

  public void printCount(int start, int end) {
    for (int i = start; i <= end; i++) {
      System.out.print(i + " ");
    }
    System.out.println();
  }

  public int getFactorial(int num) {
    int result = 1;
    for (int i = 1; i <= num; i++) {
      result *= i;
    }
    return result;
  }

  public void drawBox() {
    int rows = 5;
    int cols = 10;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print("*");
      }
      System.out.println();
    }
  }
}
