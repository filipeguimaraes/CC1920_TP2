import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Request extends Thread{

    int id;
    String pedido;
    Socket socketTCP;
    InetAddress overlay_peer;
    DatagramSocket datagrama;


    public Request(int id, String pedido, Socket socketTCP, InetAddress overlay_peer, DatagramSocket datagrama) {
        this.id = id;
        this.pedido = pedido;
        this.socketTCP = socketTCP;
        this.overlay_peer = overlay_peer;
        this.datagrama = datagrama;
    }

}
