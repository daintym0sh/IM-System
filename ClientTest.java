import java.io.IOException;
import javax.swing.JFrame;
/**
 *ClientTest tests the functionality of the Client class
 * 
 * @author John
 */
public class ClientTest extends JFrame {
    public static void main(String[] args) throws IOException {
        //Creates new client and calls run() method
        Client client = new Client();
        client.setDefaultCloseOperation(EXIT_ON_CLOSE);
        client.run();
    }

}
