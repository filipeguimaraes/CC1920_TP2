package Requests;

import Headers.HeaderData;

import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

public class RequestClient implements Comparable {
    List<Thread> threads;

    private String uniqueId;

    private Socket clientSocket;
    private DatagramSocket serverSocket;

    private PriorityBlockingQueue<HeaderData> question;
    private PriorityBlockingQueue<HeaderData> answer;


    public RequestClient (Socket s, DatagramSocket ds) {
        question = new PriorityBlockingQueue<>();
        answer = new PriorityBlockingQueue<>();
        threads = new ArrayList<>();

        uniqueId = UUID.randomUUID().toString();

        clientSocket = s;
        serverSocket = ds;
    }

    public void swapUniqueId() {
        this.uniqueId = UUID.randomUUID().toString();
    }


    /*
     * methods that create threads to work with the variables
     */

    @Override
    public int compareTo(Object o) {
        RequestClient r = (RequestClient) o;
        return uniqueId.compareTo(r.uniqueId);
    }
}
