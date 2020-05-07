import java.net.DatagramSocket;
import java.net.Socket;
import java.util.concurrent.PriorityBlockingQueue;

public class RequestServer {
    private String uniqueId;

    private Socket serverSocket;
    private DatagramSocket clientSocket;

    private PriorityBlockingQueue<byte[]> question;
    private PriorityBlockingQueue<byte[]> answer;

    /*
     * methods that create threads to work with the variables
     */
}
