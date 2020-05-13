package Test;

import Headers.HeaderData;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.UUID;

public class mainTest {

    public static void main(String[] args) throws IOException {
        String m = "adoqpowdo qqq";

        byte [] b = HeaderData.insertHeader("ABC",UUID.randomUUID().toString(),InetAddress.getByName("127.0.0.1").getAddress(),"123",m.getBytes());

        System.out.println(Arrays.toString(b));
        System.out.println(new String(b));


        HeaderData hd = new HeaderData("ABC", UUID.randomUUID().toString(),InetAddress.getByName("127.0.0.1").getAddress(),123,123,m.getBytes());


        System.out.println(Arrays.toString(hd.toArrayByte()));
        System.out.println(new String(hd.toArrayByte()));


    }
}
