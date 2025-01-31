package no.ntnu.server;

public enum ServerCLICommand {
  EXIT("exit", "Stop the application, this will also stop the server"),
  HELP("help", "Show all available commands"),
  RUNNING("running", "Show if the server is running"),
  START("start", "Start the server"),
  STOP("stop", "Stop the server");

  private final String command;
  private final String description;

  ServerCLICommand(String command, String description) {
    this.command = command;
    this.description = description;
  }

  public String getCommand() {
    return this.command;
  }

  public String getDescription() {
    return this.description;
  }

  public static ServerCLICommand fromString(String command) {
    for (ServerCLICommand c : ServerCLICommand.values()) {
      if (c.getCommand().equals(command)) {
        return c;
      }
    }

    return null;
  }
}
