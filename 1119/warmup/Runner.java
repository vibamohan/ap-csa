public class Runner {
  public static void main(String[] args) {
    PracticeQuiz quiz = new PracticeQuiz();
    int[] arr = new int[5];
    for (int i = 0; i <= 4; i++) {
      arr[i] = (int) (Math.random() * 5) + 1;
    }

    quiz.print(arr);
    boolean has3 = quiz.contains(arr, 3);
    System.out.println("Array has 3: " + has3);

    int prod = quiz.getProduct(arr);
    System.out.println("Product : " + prod);

    int largest = quiz.getLargest(arr);
    System.out.println("Largest: " + largest);
  }
}
