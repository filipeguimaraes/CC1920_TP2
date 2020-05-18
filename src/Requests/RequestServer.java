package Requests;

import Headers.HeaderData;

import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

public class RequestServer implements Comparable, IRequest {
    List<Thread> threads;

    private String uniqueId;

    private byte[] addr;

    private Socket serverSocket;
    private DatagramSocket clientSocket;

    private PriorityBlockingQueue<HeaderData> question;
    private PriorityBlockingQueue<HeaderData> answer;


    public RequestServer (Socket s, DatagramSocket ds, byte[] a) {
        threads = new ArrayList<>();

        uniqueId = UUID.randomUUID().toString();

        clientSocket = ds;
        serverSocket = s;

        addr = a.clone();

        question = new PriorityBlockingQueue<>();
        answer = new PriorityBlockingQueue<>();
    }

    public byte[] getAddr() {
        return addr;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Socket getServerSocket() {
        return serverSocket;
    }

    public DatagramSocket getClientSocket() {
        return clientSocket;
    }

    public PriorityBlockingQueue<HeaderData> getQuestion() {
        return question;
    }

    public PriorityBlockingQueue<HeaderData> getAnswer() {
        return answer;
    }

    public void swapUniqueId() {
        this.uniqueId = UUID.randomUUID().toString();
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    /*
     * methods that create threads to work with the variables
     */

    @Override
    public int compareTo(Object o) {
        RequestServer r = (RequestServer) o;
        return uniqueId.compareTo(r.uniqueId);
    }


    @Override
    public void addUDPToBuffer(HeaderData hd) {
        synchronized ( question) {

        question.notify();
        question.add(hd);
        }
    }

    @Override
    public String getKeyUid() {
        return uniqueId;
    }
}
