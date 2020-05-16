package BufferThreads;

import Headers.HeaderData;
import Headers.RoutingData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPToBuffer  implements Runnable{
    private RoutingData rd;
    private DatagramSocket udp;


    public UDPToBuffer (RoutingData r, DatagramSocket u) {
        rd = r;
        udp = u;
    }

    public void storeData (byte[] buff_byte, InetAddress addr) throws Exception {
        HeaderData hd = new HeaderData(HeaderData.decodeArrayByte(buff_byte));
        hd.setAddress(addr.getAddress());
        rd.addPacketToRequest(hd);


        System.out.println("UDPToBuffer: " + hd.getUid() +" "+addr.toString());
        System.out.println("UDPToBuffer: " + new String(hd.getMessage()));
    }

    @Override
    public void run() {
        int k = 1024;
        byte[] buff_byte = new byte[k];

        DatagramPacket p = new DatagramPacket(buff_byte,k);

        try {
            while (true) {
                udp.receive(p);
                storeData(p.getData().clone(),p.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
