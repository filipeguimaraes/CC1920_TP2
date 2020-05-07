import java.net.DatagramSocket;
import java.net.Socket;
import java.util.concurrent.PriorityBlockingQueue;

public class RequestClient {
    private String uniqueId;

    private Socket clientSocket;
    private DatagramSocket serverSocket;

    private PriorityBlockingQueue<byte[]> question;
    private PriorityBlockingQueue<byte[]> answer;

    /*
     * methods that create threads to work with the variables
     */
}
