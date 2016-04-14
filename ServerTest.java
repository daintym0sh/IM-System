import java.io.IOException;
import javax.swing.JFrame;
/**
 *ServerTest tests the functionality of the Server class.
 * 
 * @author John
 */
public class ServerTest extends JFrame {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setDefaultCloseOperation(EXIT_ON_CLOSE);
        server.run();
    }
}
