package no.ntnu.server;

import no.ntnu.constants.Config;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {


  public boolean start() {
    boolean success = true;

    try (ServerSocket serverSocket = new ServerSocket(Config.PORT)) {
      Socket clientSocket = serverSocket.accept();


    } catch (Exception e) {
      System.out.println(e.getMessage());
      success = false;
    }

    return success;
  }


  public static void main(String[] args) {
    ServerApplication server = new ServerApplication();

    if (server.start()) {
      System.out.println("Server started successfully");
    } else {
      System.out.println("Server failed to start");
    }
  }
}
