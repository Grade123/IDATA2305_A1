package no.ntnu.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
  private PrintWriter writer;
  private BufferedReader reader;

  public ClientHandler(Socket socket) throws IOException {

    this.writer = new PrintWriter(socket.getOutputStream(), true);
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  public void send(String message) {
    writer.println(message);
  }

  public String receive() throws IOException {
    return reader.readLine();
  }

  public void handleMessage() throws IOException{
    String message = receive();
    double response = act(message);
    send(String.valueOf(response));
  }

  private double act(String message) {
    double response = 0;
    String operator;
    double num1;
    double num2;
    try {
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
      default:
        send("Invalid operator");
    }
    return response;

  }

}
