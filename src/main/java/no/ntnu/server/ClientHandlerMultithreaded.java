package no.ntnu.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandlerMultithreaded extends ClientHandler implements Runnable{

  public ClientHandlerMultithreaded(Socket socket) throws IOException {
    super(socket);
  }

  @Override
  public void run() {
    try {
      handleMessage();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
