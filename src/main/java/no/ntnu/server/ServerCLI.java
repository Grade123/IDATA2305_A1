package no.ntnu.server;

import java.util.Scanner;

import no.ntnu.constants.Config;

/**
 * ServerCLI class for the server
 * 
 * This class is used to start the server and accept commands
 * 
 * @author Stian Øye Jenssen
 * @since 31/1/2025
 */
public class ServerCLI {
  private Scanner scanner;
  private Server server;

  /**
   * Starts the server cli
   */
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

  /**
   * Accepts a command from the user
   * 
   * @return true if the application should close
   */
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

  /**
   * Shows the help menu
   */
  private void showHelp() {
    System.err.println("Available commands:");
    
    for (ServerCLICommand command : ServerCLICommand.values()) {
      System.out.println(command.getCommand() + ": " + command.getDescription());
    }
  }

  /**
   * shows the exiting message
   */
  private void showExiting() {
    System.out.println("Exiting server");
  }

  /**
   * Starts the server
   * Shows a message if the server started or not
   * 
   * Boolean is parsed from a string and sent to the server
   * 
   * @param arg1 a string representation of a boolean
   */
  private void startServer(String arg1) {
    boolean multiThreaded = Boolean.parseBoolean(arg1);

    if (this.server.start(multiThreaded)) {
      System.out.println("Server started");
    } else {
      System.out.println("Failed to start server");
    }
  }

  /**
   * Stops the server
   * Shows a message if the server stopped or not
   */
  private void stopServer() {
    if (this.server.stop()) {
      System.out.println("Server stopped");
    } else {
      System.out.println("Failed to stop server");
    }
  }

  /**
   * Shows an invalid command message
   */
  private void showInvalidCommand() {
    System.out.println("Invalid command");
  }

  /**
   * Shows if the server is running or not
   */
  private void showIsRunning() {
    if (this.server.isRunning()) {
      System.out.println("Server is running");
    } else {
      System.out.println("Server is not running");
    }
  }

  /**
   * Main method
   * @param args
   */
  public static void main(String[] args) {
    ServerCLI cli = new ServerCLI();

    try {
      cli.start();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
