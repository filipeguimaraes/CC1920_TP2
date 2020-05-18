package BufferThreads;

import Headers.HeaderData;
import Headers.RoutingData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UDPToBuffer  implements Runnable{
    private RoutingData rd;
    private DatagramSocket udp;


    public UDPToBuffer (RoutingData r, DatagramSocket u) {
        rd = r;
        udp = u;
    }

    public void storeData (byte[] buff_byte, InetAddress addr) throws Exception {
        System.out.println("UDPToBuffer length: " + buff_byte.length);
        HeaderData hd = new HeaderData(HeaderData.decodeArrayByte(buff_byte));
        hd.setAddress(addr.getAddress());
        rd.addPacketToRequest(hd);

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
                byte[] aux = Arrays.copyOfRange(p.getData(),0,p.getLength());
                storeData(aux,p.getAddress());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
