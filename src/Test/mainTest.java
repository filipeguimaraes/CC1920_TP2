package Test;

import Headers.HeaderData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.UUID;

public class mainTest {

    public static void main(String[] args) throws Exception {
        String m = "Hello World!";

        HeaderData hd = new HeaderData("A", UUID.randomUUID().toString(),InetAddress.getByName("127.0.0.1").getAddress(),54,123,m.getBytes());

        byte [] before = hd.toArrayByte();
        byte [] encode = HeaderData.encodeArrayByte(before);
        byte [] decode = HeaderData.decodeArrayByte(encode);

        System.out.println(new String(before));
        System.out.println(new String(encode));
        System.out.println(new String(decode));


    }
}
