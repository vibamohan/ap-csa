public class PracticeQuiz {
  public void print(int[] arr) {
    for (int i : arr) {
      System.out.print(i + " ");
    }
    System.out.println();
  }

  public boolean contains(int[] arr, int num) {
    for (int i : arr) {
      if (i == num) return true;
    }

    return false;
  }

  public int getProduct(int[] arr) {
    int prod = 1;
    for (int i : arr) {
      prod *= i;
    }
    return prod;
  }

  public int getLargest(int[] arr) {
    quicksort(arr, 0, arr.length - 1);
    return arr[arr.length - 1];
  }

  private static void quicksort(int[] a, int l, int r) {
    if (l >= r) return;
    int p = partition(a, l, r);
    quicksort(a, l, p - 1);
    quicksort(a, p + 1, r);
  }

  private static int partition(int[] a, int l, int r) {
    int pivot = a[r];
    int i = l;
    for (int j = l; j < r; j++) {
      if (a[j] <= pivot) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        i++;
      }
    }
    int temp = a[i];
    a[i] = a[r];
    a[r] = temp;

    return i;
  }
}
