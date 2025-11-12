public class Runner {
  public static void main(String[] args) {
    ForTest2 test = new ForTest2();
    test.printCount1(10);
    int sum1 = test.getSum1(5);
    System.out.println("Sum1: " + sum1);
    test.printCount2(3, 10);
    int sum2 = test.getSum2(5);
    System.out.println("Sum2: " + sum2);
  }
}
