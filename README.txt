Instructions for compiling and running
--------------------------------------

1. Make sure your current working directory is "Battleships".

2. Compile by running:

        javac @sources.txt

3. Start the server by running:

        java -classpath src server.Server

4. In two or more other terminals, open two or more clients by running:

        java -classpath src view.MatchRoomView


Configuration
-------------

You may change the hostname and port that the client connects to by editing config.properties.

The server takes two optional arguments:

        java -classpath src server.Server <port> <multi move enabled: true/false>

The port is the port number the server should listen on. Multi move mode is where you get another turn if you hit a ship.
