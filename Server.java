import java.io.*;
import java.net.*;

public class Server {
    
    public void run() throws IOException {

        /* Creates server socket on port 444 and listens for 
           client connection requests 
        */
        ServerSocket servsock = new ServerSocket(444);
        Socket sock = servsock.accept();
        
        // Sets up a buffered input reader to read data from input stream
        InputStreamReader input = new InputStreamReader(sock.getInputStream());
        BufferedReader reader = new BufferedReader(input);
        
        //Makes a message string equal to message sent from client
        String message = reader.readLine();
        
        //If client message is not null, echoes message by printing to output stream
        if(message != null){
            PrintStream print = new PrintStream(sock.getOutputStream());
            print.println("Message recieved: " + message);
        }
        
        //Closes server socket
        servsock.close();
    }
	
    public static void main(String[] args) throws IOException {
         Server server = new Server();
         server.run();
     }
}