package no.ntnu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private ServerSocket serverSocket;
  private boolean running = false;
  private final int port;

  public Server(int port) {
    this.port = port;
  }

  private boolean connect() {
    boolean success = true;

    try {
      serverSocket = new ServerSocket(this.port);
    } catch (Exception e) {
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
    new Thread(() -> {
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
    }).start();
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
