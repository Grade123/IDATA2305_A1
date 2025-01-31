package no.ntnu.client;

public class ClientApplication {
  boolean running = false;
  public static void main(String[] args) {
    ClientApplication app = new ClientApplication();
    app.run();
  }

  private void run() {
    running = true;
    while (running) {
      printMenu();
    }
  }

  private void printMenu() {
    System.out.println("1: Addition");
    System.out.println("2: Subtraction");
    System.out.println("3: Multiplication");
    System.out.println("4: Division");
    System.out.println("5: Modulo");
    System.out.println("6: Specified amount of simultaneous requests");
  }
}
