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
    String line = scanner.nextLine();
    String[] sections = line.split(" ");
    


    ServerCLICommand command = ServerCLICommand.fromString(sections[0]);

    switch (command) {
      case EXIT:
        this.showExiting();
        shouldClose = true;
        break;
      
      case HELP:
        this.showHelp();
        break;

      case RUNNING:
        this.showIsRunning();
        break;

      case START:
        this.startServer(sections[1]);
        break;

      case STOP:
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
    
    for (ServerCLICommand command : ServerCLICommand.values()) {
      System.out.println(command.getCommand() + ": " + command.getDescription());
    }
  }

  private void showExiting() {
    System.out.println("Exiting server");
  }

  private void startServer(String arg1) {
    boolean multiThreaded = Boolean.parseBoolean(arg1);

    if (this.server.start(multiThreaded)) {
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
