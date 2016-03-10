import java.io.*;
import java.net.*;

public class Server {

    public void run() throws IOException {
        ServerSocket servsock = new ServerSocket(444);
        Socket sock = servsock.accept();
        
        InputStreamReader input = new InputStreamReader(sock.getInputStream());
        BufferedReader reader = new BufferedReader(input);
        
        String message = reader.readLine();
        System.out.println(message);
        
        if(message != null){
            PrintStream print = new PrintStream(sock.getOutputStream());
            print.println("Message recieved");
        }
    }
	
    public static void main(String[] args) throws IOException {
         Server server = new Server();
         server.run();
     }
}