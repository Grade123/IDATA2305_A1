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

      case "running":
        this.showIsRunning();
        break;

      case "start":
        this.startServer();
        break;

      case "stop":
        this.stopServer();
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

  private void startServer() {
    if (this.server.start()) {
      System.out.println("Server started");
    } else {
      System.out.println("Failed to start server");
    }
  }

  private void stopServer() {
    if (this.server.stop()) {
      System.out.println("Server stopped");
    } else {
      System.out.println("Failed to stop server");
    }
  }

  private void showInvalidCommand() {
    System.out.println("Invalid command");
  }

  private void showIsRunning() {
    if (this.server.isRunning()) {
      System.out.println("Server is running");
    } else {
      System.out.println("Server is not running");
    }
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
