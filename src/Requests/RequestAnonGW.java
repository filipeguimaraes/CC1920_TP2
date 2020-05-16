package Requests;

import Headers.HeaderData;

import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

public class RequestAnonGW implements Comparable, IRequest {
    List<Thread> threads;

    private String uniqueId;

    private DatagramSocket datagramSocket;

    private byte[] addr_server;
    private byte[] addr_client;

    private PriorityBlockingQueue<HeaderData> question;
    private PriorityBlockingQueue<HeaderData> answer;


    public RequestAnonGW (DatagramSocket ds, byte[] client, byte[] server) {
        threads = new ArrayList<>();

        uniqueId = UUID.randomUUID().toString();

        datagramSocket = ds;

        addr_client = client.clone();
        addr_server = server.clone();

        question = new PriorityBlockingQueue<>();
        answer = new PriorityBlockingQueue<>();
    }

    public void swapUniqueId() {
        this.uniqueId = UUID.randomUUID().toString();
    }

    public byte[] getAddr_server() {
        return addr_server;
    }

    public byte[] getAddr_client() {
        return addr_client;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
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

    @Override
    public int compareTo(Object o) {
        RequestAnonGW r = (RequestAnonGW) o;
        return uniqueId.compareTo(r.uniqueId);
    }

    @Override
    public void addUDPToBuffer(HeaderData hd) {
        if (hd.getType().equals("RA")) {
            answer.add(hd);
        }
        if (hd.getType().equals("RQ")) {
            question.add(hd);
        }
    }

    @Override
    public String getKeyUid() {
        return uniqueId;
    }
}
