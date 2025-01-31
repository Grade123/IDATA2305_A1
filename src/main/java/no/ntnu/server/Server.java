package no.ntnu.server;

import java.net.ServerSocket;

public class Server {
  private ServerSocket serverSocket;
  private boolean running = false;
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

  private boolean disconnect() {
    boolean success = true;
    try {
      serverSocket.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      success = false;
    }

    return success;
  }

  public boolean isRunning() {
    return this.running;
  }

  public boolean start() {
    boolean started = this.connect();

    if (started) {
      this.running = true;
    }

    return started;
  }

  public boolean stop() {
    boolean stopped = this.disconnect();

    if (stopped) {
      this.running = false;
    }

    return stopped;
  }

}
