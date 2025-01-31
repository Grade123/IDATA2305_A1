package no.ntnu.server;

import java.util.Scanner;

import no.ntnu.constants.Config;

public class ServerCLI {
  private Scanner scanner;
  private Server server;

  public void start() {
    this.scanner = new Scanner(System.in);
    this.server = new Server(Config.PORT);

    boolean shouldClose = false;

    while (!shouldClose) {
      shouldClose = this.acceptCommand();
    }

    this.scanner.close();
    this.server.stop();
  }

  public boolean acceptCommand() {
    boolean shouldClose = false;

    System.out.println("Enter command (help for commands): ");

    String command = scanner.nextLine();
    switch (command) {
      case "exit":
        this.showExiting();
        shouldClose = true;
        break;
      
      case "help":
        this.showHelp();
        break;

      case "start":
        showStarting();
        this.server.start();
        break;

      case "stop":
        showStopping();
        this.server.stop();
        break;

      default:
        this.showInvalidCommand();
        break;
    }

    return shouldClose;
  } 

  private void showHelp() {
    System.err.println("Available commands:");
    System.err.println("exit: Close the server");
    System.err.println("help: Show this help message");
    System.err.println("start: Start the server");
    System.err.println("stop: Stop the server");
  }

  private void showExiting() {
    System.out.println("Exiting server");
  }

  private void showStarting() {
    System.out.println("Starting server");
  }

  private void showStopping() {
    System.out.println("Stopping server");
  }

  private void showInvalidCommand() {
    System.out.println("Invalid command");
  }

  public static void main(String[] args) {
    ServerCLI cli = new ServerCLI();

    try {
      cli.start();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
