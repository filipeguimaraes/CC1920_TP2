package Requests;

import BufferThreads.BufferToTCP;
import Headers.HeaderData;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

public class RequestClient implements Comparable, IRequest {
    List<Thread> threads;

    private String uniqueId;

    private Socket clientSocket;
    private DatagramSocket serverSocket;

    private byte[] addr;

    private PriorityBlockingQueue<HeaderData> question;
    private PriorityBlockingQueue<HeaderData> answer;


    public RequestClient (Socket s, DatagramSocket ds, byte[] a) {
        threads = new ArrayList<>();

        uniqueId = UUID.randomUUID().toString();

        clientSocket = s;
        serverSocket = ds;

        addr = a.clone();

        question = new PriorityBlockingQueue<>();
        answer = new PriorityBlockingQueue<>();
    }

    public void swapUniqueId() {
        this.uniqueId = UUID.randomUUID().toString();
    }

    public byte[] getAddr() {
        return addr;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public DatagramSocket getServerSocket() {
        return serverSocket;
    }

    public PriorityBlockingQueue<HeaderData> getQuestion() {
        return question;
    }

    public PriorityBlockingQueue<HeaderData> getAnswer() {
        return answer;
    }

    public String getUniqueId () {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    /*
     * methods that create threads to work with the variables
     */

    @Override
    public int compareTo(Object o) {
        RequestClient r = (RequestClient) o;
        return uniqueId.compareTo(r.uniqueId);
    }


    @Override
    public void addUDPToBuffer(HeaderData hd) {
        answer.add(hd);
    }

    @Override
    public String getKeyUid() {
        return uniqueId;
    }
}
