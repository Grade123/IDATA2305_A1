package no.ntnu.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Represents a client handler for the math server with multithreading.
 */
public class ClientHandlerMultithreaded extends ClientHandler implements Runnable{

  /**
   * Creates a new instance of the ClientHandlerMultithreaded class.
   *
   * @param socket The socket to communicate with the client.
   * @throws IOException If an I/O error occurs when creating the input or output stream.
   */
  public ClientHandlerMultithreaded(Socket socket) throws IOException {
    super(socket);
  }

  /**
   * Runs the client handler.
   */
  @Override
  public void run() {
    try {
      handleMessage();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
