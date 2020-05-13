package Test;

import Headers.HeaderData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.UUID;

public class mainTest {

    public static void main(String[] args) throws IOException {
        String m = "adoqpowdo qqq";

        byte[] tr = new byte[1];
        DatagramPacket dp = new DatagramPacket(tr,0,0,InetAddress.getByName("127.0.0.1"),1001);

        byte [] b = HeaderData.insertHeader("Q",UUID.randomUUID().toString(),dp.getAddress().getAddress(),"123",m.getBytes());

        System.out.println(Arrays.toString(b));
        System.out.println(new String(b));


        HeaderData hd = new HeaderData("A", UUID.randomUUID().toString(),InetAddress.getByName("127.0.0.1").getAddress(),123,123,m.getBytes());

        System.out.println(Arrays.toString(hd.toArrayByte()));
        System.out.println(new String(hd.toArrayByte()));


        HeaderData h = new HeaderData("R", UUID.randomUUID().toString(),InetAddress.getByName("127.0.0.1").getAddress(),123,123,m.getBytes());

        System.out.println(Arrays.toString(h.toArrayByte()));
        System.out.println(new String(h.toArrayByte()));

    }
}
