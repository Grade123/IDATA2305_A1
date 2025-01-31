package no.ntnu.server;

import java.net.ServerSocket;

public class Server {
  private ServerSocket serverSocket;
  private final int PORT;

  public Server(int PORT) {
    this.PORT = PORT;
  }

  private boolean connect() {
    boolean success = true;

    try {
      serverSocket = new ServerSocket(this.PORT);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      success = false;
    }

    return success;
  }

  public void disconnect() {
    try {
      serverSocket.close();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  public boolean start() {
    return this.connect();
  }

  public void stop() {
    this.disconnect();
  }

}
