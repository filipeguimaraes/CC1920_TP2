import java.net.DatagramSocket;
import java.util.concurrent.PriorityBlockingQueue;

public class RequestAnonGW {
    private String uniqueId;

    private DatagramSocket clientSocket;
    private DatagramSocket serverSocket;

    private PriorityBlockingQueue<byte[]> question;
    private PriorityBlockingQueue<byte[]> answer;

    /*
     * methods that create threads to work with the variables
     */
}
