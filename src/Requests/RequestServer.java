package Requests;

import Headers.HeaderData;

import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

public class RequestServer implements Comparable {
    List<Thread> threads;

    private String uniqueId;

    private Socket serverSocket;
    private DatagramSocket clientSocket;

    private PriorityBlockingQueue<HeaderData> question;
    private PriorityBlockingQueue<HeaderData> answer;


    public RequestServer (Socket s, DatagramSocket ds) {
        question = new PriorityBlockingQueue<>();
        answer = new PriorityBlockingQueue<>();
        threads = new ArrayList<>();

        uniqueId = UUID.randomUUID().toString();

        clientSocket = ds;
        serverSocket = s;
    }

    public void swapUniqueId() {
        this.uniqueId = UUID.randomUUID().toString();
    }

    /*
     * methods that create threads to work with the variables
     */

    @Override
    public int compareTo(Object o) {
        RequestServer r = (RequestServer) o;
        return uniqueId.compareTo(r.uniqueId);
    }
}
