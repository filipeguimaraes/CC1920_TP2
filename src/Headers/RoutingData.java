package Headers;

import BufferThreads.BufferToTCP;
import BufferThreads.BufferToUDP;
import BufferThreads.TCPToBuffer;
import Requests.IRequest;
import Requests.RequestClient;
import Requests.RequestServer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoutingData {
    private ConcurrentHashMap<String, IRequest> requests;
    private DatagramSocket datagramSocket;

    public String SERVER_HOST = "10.3.3.1";
    public int SERVER_PORT = 80;
    public int UDP_PORT = 6666;


    public RoutingData (String sh, int sp, int up, DatagramSocket ds) {
        requests = new ConcurrentHashMap<>();
        datagramSocket = ds;
        SERVER_HOST = sh;
        SERVER_PORT = sp;
        UDP_PORT = up;
    }

    public void addRequestClient (RequestClient r) throws IOException {
        while (requests.putIfAbsent(r.getKeyUid(),r) != null) {
            r.swapUniqueId();
        }

        Thread b2t = new Thread(new BufferToTCP(r.getClientSocket(),r.getAnswer(),"Q",r.getUniqueId()));
        Thread b2u = new Thread(new BufferToUDP(r.getServerSocket(),r.getQuestion()));
        Thread t2b = new Thread(new TCPToBuffer(r.getClientSocket(),r.getQuestion(),"Q",r.getUniqueId(),r.getAddr()));

        List<Thread> lt = r.getThreads();

        lt.add(b2t);
        lt.add(b2u);
        lt.add(t2b);

        lt.forEach(Thread::start);

    }

    public void addRequestServer (RequestServer r) throws IOException {
        while (requests.putIfAbsent(r.getKeyUid(),r) != null) {
            r.swapUniqueId();
        }

        Thread b2t = new Thread(new BufferToTCP(r.getServerSocket(),r.getQuestion(),"A",r.getUniqueId()));
        Thread b2u = new Thread(new BufferToUDP(r.getClientSocket(),r.getAnswer()));
        Thread t2b = new Thread(new TCPToBuffer(r.getServerSocket(),r.getAnswer(),"A",r.getUniqueId(),r.getAddr()));

        List<Thread> lt = r.getThreads();

        lt.add(b2t);
        lt.add(b2u);
        lt.add(t2b);

        lt.forEach(Thread::start);

    }

    public void addPacketToRequest (HeaderData hd) throws IOException {
        IRequest req = requests.getOrDefault(hd.getKeyUid(),null);

        if ( req == null && hd.getType().equals("Q")) {
            RequestServer rs = new RequestServer(new Socket(SERVER_HOST, SERVER_PORT), datagramSocket, hd.getAddress());
            rs.setUniqueId(hd.getUid());
            req = rs;
            addRequestServer(rs);
        }

        req.addUDPToBuffer(hd);
    }
}
