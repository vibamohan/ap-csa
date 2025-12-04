
public class ArrayTest {

  public void printArray(int[] arr) {
    for (int num : arr) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  public boolean searchNumber1(int num, int[] arr) {
    for (int n : arr) {
      if (n == num) return true;
    }
    return false;
  }

  public int searchNumber2(int num, int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == num) return i;
    }
    return -1;
  }

  public int findLargest(int[] arr) {
    int largest = arr[0];
    for (int n : arr) {
      if (n > largest) largest = n;
    }
    return largest;
  }

  public int findSmallest(int[] arr) {
    int smallest = arr[0];
    for (int n : arr) {
      if (n < smallest) smallest = n;
    }
    return smallest;
  }

  public int sum(int[] arr) {
    int total = 0;
    for (int n : arr) {
      total += n;
    }
    return total;
  }

  public int product(int[] arr) {
    int total = 1;
    for (int n : arr) {
      total *= n;
    }
    return total;
  }
}
