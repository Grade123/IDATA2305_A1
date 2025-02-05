package no.ntnu.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Represents a client handler for the math server.
 *
 * <p>This class is responsible for handling the communication between the math
 * server and the math client.
 * <p>It supports sending and receiving messages, as well as acting on the messages received.
 * <p>Received messages are expected to be in the format "operator number1 number2".
 * The result of the operation is then sent back to the client.
 *
 * @author Ludvik Lund-Hole
 */
public class ClientHandler {
  private final PrintWriter writer;
  private final BufferedReader reader;

  /**
   * Creates a new instance of the ClientHandler class.
   *
   * @param socket The socket to communicate with the client.
   * @throws IOException If an I/O error occurs when creating the input or output stream.
   */
  public ClientHandler(Socket socket) throws IOException {

    this.writer = new PrintWriter(socket.getOutputStream(), true);
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  /**
   * Sends a message to the client.
   *
   * @param message The message to send.
   */
  public void send(String message) {
    writer.println(message);
  }

  /**
   * Receives a message from the client.
   *
   * @return The message received.
   * @throws IOException If an I/O error occurs when reading the message.
   */
  public String receive() throws IOException {
    return reader.readLine();
  }

  /**
   * Gets message, acts on it and sends the response.
   *
   * @throws IOException If an I/O error occurs when sending the response.
   */
  public void handleMessage() throws IOException{
    String message = receive();
    double response = act(message);
    send(String.valueOf(response));
  }

  /**
   * Performs the operation specified in the message.
   *
   * <p>The message is expected to be in the format "operator number1 number2".
   *
   * @param message The message containing the operator and the numbers.
   * @return The result of the operation.
   */
  private double act(String message) {
    double response = 0;
    String operator;
    double num1;
    double num2;
    try {
      Thread.sleep(200);
      String[] parts = message.split(" ");
      operator = parts[0];
      num1 = Double.parseDouble(parts[1]);
      num2 = Double.parseDouble(parts[2]);
    } catch (Exception e) {
      send("Invalid input");
      throw new IllegalArgumentException("Invalid input, " + message + ". " + e.getMessage());
    }


    switch (operator) {
      case "A":
        response = num1 + num2;
        break;
      case "S":
        response = num1 - num2;
        break;
      case "M":
        response = num1 * num2;
        break;
      case "D":
        if (num2 == 0) {
          send("Cannot divide by zero");
          throw new IllegalArgumentException("Cannot divide by zero");
        }
        response = num1 / num2;
        break;
      case "F":
        response = num1 % num2;
        break;
      default:
        send("Invalid operator");
    }
    return response;

  }

}
