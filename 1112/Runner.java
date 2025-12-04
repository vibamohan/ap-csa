public class Runner {
  public static void main(String[] args) {
    ArrayTest at = new ArrayTest();
    int[] nums = new int[10];
    for (int i = 0; i < nums.length; i++) {
      nums[i] = (int) (Math.random() * 9 + 1);
    }

    at.printArray(nums);

    boolean found = at.searchNumber1(5, nums);
    System.out.println("searchNumber1 (5): " + found);

    int index = at.searchNumber2(5, nums);
    System.out.println("searchNumber2 (5): " + index);

    int largest = at.findLargest(nums);
    System.out.println("Largest: " + largest);

    int smallest = at.findSmallest(nums);
    System.out.println("Smallest: " + smallest);

    int sum = at.sum(nums);
    System.out.println("Sum: " + sum);

    int product = at.product(nums);
    System.out.println("Product: " + product);
  }
}
