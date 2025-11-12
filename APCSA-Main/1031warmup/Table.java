public class Table {
  private int row;
  private int col;

  public Table(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public void printDrawBox() {
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        System.out.print("*");
      }
      System.out.println();
    }
  }
}
