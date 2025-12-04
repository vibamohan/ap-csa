import java.awt.Font;
import java.awt.Graphics;

public class SlotMachine {
  int num1, num2, num3;
  int winnings;
  int balance;

  public SlotMachine(int balance) {
    this.balance = balance;
  }

  public void spin() {
    if (balance-- <= 0) return;

    num1 = genRandom(0, 9);
    num2 = genRandom(0, 9);
    num3 = genRandom(0, 9);

    if (num1 == 7 && num2 == 7 && num3 == 7) {
      winnings = 100;
    } else if (num1 == num2 && num2 == num3) {
      winnings = 5;
    } else if (num1 == num2 || num2 == num3 || num1 == num3) {
      winnings = 1;
    } else {
      winnings = 0;
    }

    balance += winnings;
  }

  public int getNum1() {
    return num1;
  }

  public int getNum2() {
    return num2;
  }

  public int getNum3() {
    return num3;
  }

  public int getWinnings() {
    return winnings;
  }

  public int getBalance() {
    return balance;
  }

  public void drawMe(Graphics g) {
    Font font = new Font("Arial", Font.PLAIN, 20);
    g.setFont(font);
    g.drawString(num1 + " " + num2 + " " + num3, 50, 100);
    g.drawString("Balance: " + balance, 50, 200);
    g.drawString("You won: " + winnings, 50, 300);
    drawPayoutTable(g);
  }

  public void drawPayoutTable(Graphics g) {
    Font font = new Font("Arial", Font.PLAIN, 20);
    g.setFont(font);
    g.drawString("777 = 100 points", 400, 100);
    g.drawString("3 of a kind = 5 points", 400, 140);
    g.drawString("2 of a kind = 1 point", 400, 180);
  }

  private int genRandom(int min, int max) {
    return (int) (Math.random() * ((max - min) + 1)) + min;
  }
}
