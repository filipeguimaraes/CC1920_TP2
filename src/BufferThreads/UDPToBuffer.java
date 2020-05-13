package BufferThreads;

import Headers.HeaderData;
import Headers.RoutingData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPToBuffer  implements Runnable{
    private RoutingData rd;
    private DatagramSocket udp;


    public UDPToBuffer (RoutingData r, DatagramSocket u) throws IOException {
        rd = r;
        udp = u;
    }

    public void storeData (byte[] buff_byte) throws IOException {
        HeaderData hd = new HeaderData(buff_byte);
        rd.addPacketToRequest(hd);


        System.out.println("UDPToBuffer: "+ hd.getUid() + hd.getType());
        System.out.println("UDPToBuffer: "+ hd.getUid() + new String(buff_byte));
    }

    @Override
    public void run() {
        int k = 1024;
        byte[] buff_byte = new byte[k];

        DatagramPacket p = new DatagramPacket(buff_byte,k);

        try {
            while (true) {
                udp.receive(p);
                storeData(p.getData().clone());
            }
        }
        catch (IOException ignored) {}

    }
}
