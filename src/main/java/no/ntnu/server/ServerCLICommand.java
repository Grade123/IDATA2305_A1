package no.ntnu.server;

/**
 * ServerCLICommand enum for different commands available on the server
 */
public enum ServerCLICommand {
  EXIT("exit", "Stop the application, this will also stop the server"),
  HELP("help", "Show all available commands"),
  RUNNING("running", "Show if the server is running"),
  START("start", "Start the server, Multi-threaded: true/false"),
  STOP("stop", "Stop the server");

  private final String command;
  private final String description;

  /** Constructor for ServerCLICommand 
   * @param command the command
   * @param description A description of the command
  */
  ServerCLICommand(String command, String description) {
    this.command = command;
    this.description = description;
  }

  /**
   * Returns the command as a string
   * 
   * @return command as a string
   */
  public String getCommand() {
    return this.command;
  }

  /**
   * Returns the description of the command
   * 
   * @return description of the command
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns a ServerCLICommand from a string
   * 
   * If the string does not match any a command then null is returned
   * 
   * @param command the command as a string
   * @return ServerCLICommand
   */
  public static ServerCLICommand fromString(String command) {
    for (ServerCLICommand c : ServerCLICommand.values()) {
      if (c.getCommand().equals(command)) {
        return c;
      }
    }

    return null;
  }
}
