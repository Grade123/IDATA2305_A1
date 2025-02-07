# How to run
Two runnable classes are provided in the project:
- `ServerCLI`
- `ClientApplication`

To run the server, run the ServerCLI class. This will start the server on port 4040.

**Commands**
- `start` `true`or `false`, false for single threaded, true for multi threaded.
- `stop` stops the server.
- `exit` exits the server.

To run the client, run the ClientApplication class. To execute commands on the client side, the server needs to be 
running. 

## Prerequisites
- Java 21
- Maven 3.8.1

# Performance comparison
Our test was executed with 50 requests. During the test we observed the following results:


**Single threaded:**
10.2s

**Multi threaded:**
0.22s

For demo, see the provided video.


# Contributions
- Server (Stian)
- ClientHandler (Ludvik)
- ClientApplication (Kristian)

*See commit log for further details*

## Members
Stian Øye Jensse
Kristian Garder
Ludvik Lund-Hole