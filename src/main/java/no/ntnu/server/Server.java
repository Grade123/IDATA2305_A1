package no.ntnu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

  public boolean start(boolean multiThreaded) {
    boolean started = false;

    if (!this.isRunning()) {
        started = this.connect();
        this.running = true;
    }
    while (this.running) {
      try {
        Socket socket = serverSocket.accept();

        if (multiThreaded) {
          ClientHandlerMultithreaded clientHandler = new ClientHandlerMultithreaded(socket);
          new Thread(clientHandler).start();
        } else {
          ClientHandler clientHandler = new ClientHandler(socket);
          clientHandler.handleMessage();
        }
      } catch (IOException ioException) {
        System.out.println(ioException.getStackTrace());
        this.running = false;
      }

    }
    return started;
  }

  public boolean stop() {
    boolean stopped = false;

    if (this.running) {
      stopped = this.disconnect();

      this.running = !stopped;
    }
    return stopped;
  }

}
