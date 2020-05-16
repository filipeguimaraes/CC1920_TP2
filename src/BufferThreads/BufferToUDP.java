package BufferThreads;

import Headers.HeaderData;
import Requests.RequestClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BufferToUDP implements Runnable{
    private PriorityBlockingQueue<HeaderData> buff;
    private DatagramSocket udp;


    public BufferToUDP (DatagramSocket s, PriorityBlockingQueue<HeaderData> data) {
        buff = data;
        udp = s;
    }

    public void sendData () throws Exception {
        HeaderData hd =  buff.poll(1, TimeUnit.MINUTES);
        if(hd != null) {
            byte[] b = hd.toArrayByte();
            byte[] br = HeaderData.encodeArrayByte(b);
            DatagramPacket dp = new DatagramPacket(br,br.length);
            dp.setAddress(InetAddress.getByAddress(hd.getAddress()));
            dp.setPort(6666);
            udp.send(dp);


            System.out.println("BufferToUDP: " + Arrays.toString(hd.getAddress()));
            System.out.println("BufferToUDP: "  + new String(b));
        }
        else {
            Thread.sleep(1000);
        }
    }

    @Override
    public void run() {
        try {
            while(true)
                sendData();
        }
        catch (InterruptedException | IOException ignored) { } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
