package no.ntnu.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import no.ntnu.constants.Config;

/**
 * CLI for client application
 * 
 * @author Kristian Garder
 * @since 31/1/2025
 */
public class ClientApplication {
  boolean running = false;

  /**
   * Main method for starting the application
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    ClientApplication app = new ClientApplication();
    app.run();
  }

  /**
   * Runs the main loop of the application
   * 
   * Loop:
   * 1. Print menu
   * 2. Get user input
   * 3. Handle user input
   * 4. Repeat
   * 
   * If user input is invalid, print error message and repeat
   */
  private void run() {
    running = true;

    while (running) {
      printMenu();
      handleUserInput(getUserInt());
    }
  }

  /**
   * Prints the menu
   */
  private void printMenu() {
    System.out.println("1: Addition");
    System.out.println("2: Subtraction");
    System.out.println("3: Multiplication");
    System.out.println("4: Division");
    System.out.println("5: Modulo");
    System.out.println("6: Specified amount of simultaneous requests");
    System.out.println("0: Exit");
  }

  /**
   * Get an integer from the user
   * 
   * @return user input as an integer, returns -1 if input is invalid
   */
  private int getUserInt() {
    try {
      Scanner scanner = new Scanner(System.in);
      return scanner.nextInt();
    } catch (Exception e) {
      System.out.println("Invalid input, input must be an integer");
      return -1;
    }
  }

  /**
   * Handles user input
   * 
   * @param input user input, int reperesenting the menu choice from the user
   */
  private void handleUserInput(int input) {
    if (input != -1) {
      switch (input) {
        case 1:
          System.out.println("Addition");
          handleCommand("A");
          break;
        case 2:
          System.out.println("Subtraction");
          handleCommand("S");
          break;
        case 3:
          System.out.println("Multiplication");
          handleCommand("M");
          break;
        case 4:
          System.out.println("Division");
          handleCommand("D");
          break;
        case 5:
          System.out.println("Modulo");
          handleCommand("F");
          break;
        case 6:
          System.out.println("Specified amount of single threaded requests");
          sendSpecifiedAmountOfRequests();
          break;
        case 0:
          System.out.println("Exit");
          running = false;
          break;
        default:
          System.out.println("Invalid input");
          break;
      }
    }
  }

  /**
   * Sends a specified amount of requests to the server
   */
  private void sendSpecifiedAmountOfRequests() {
    System.out.println("Input amount of requests:");
    int amount = getUserInt();
    while (amount == -1) {
      amount = getUserInt();
    }

    long startTime = System.currentTimeMillis();
    ArrayList<Thread> threads = new ArrayList<>();
    for (int i = 0; i < amount; i++) {
      Thread thread = new Thread(() -> sendCommand("A", 50, 30));
      threads.add(thread);
      thread.start();
    }
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        System.out.println("Error joining thread: " + e.getMessage());
      }
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Time taken: " + (endTime - startTime) + "ms");
  }

  /**
   * Handles a command
   * 
   * @param command the command to send to the server
   */
  private void handleCommand(String command) {
    int firstParam = -1;
    System.out.println("Input first parameter:");
    firstParam = getUserInt();
    while (firstParam == -1) {
      firstParam = getUserInt();
    }
    int secondParam = -1;
    System.out.println("Input second parameter:");
    secondParam = getUserInt();
    while (secondParam == -1) {
      secondParam = getUserInt();
    }

    sendCommand(command, firstParam, secondParam);
  }

  /**
   * Sends a command to the server
   * 
   * @param command the command to send to the server
   * @param firstParam the first parameter to send to the server
   * @param secondParam the second parameter to send to the server
   */
  private void sendCommand(String command, int firstParam, int secondParam) {
    Socket socket = startConnection();
    if (socket != null) {
      try {
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = command + " " + firstParam + " " + secondParam;
        writer.println(message);
        System.out.println("Sent command: " + message);
        String response = reader.readLine();
        System.out.println("Response: " + response);
        socket.close();
      } catch (Exception e) {
        System.out.println("Error sending command: " + e.getMessage());
      }
    }
  }

  /**
   * Starts a connection to the server
   * 
   * @return the socket if connection was successful, null otherwise
   */
  private Socket startConnection() {
    Socket socket = null;
    System.out.println("Attempting to connect to server");
      try {
        socket = new Socket(Config.HOST, Config.PORT);
        System.out.println("Connected to server");
      } catch (Exception e) {
        System.out.println("Could not connect to server");
      }
    return socket;
  }
}
