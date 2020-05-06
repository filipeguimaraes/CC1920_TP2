import java.io.*;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class Transfer {

    private static final int OFFSET = 0;
    private static final int MAX_SIZE = 5;

    private final DataOutputStream bw;
    private final DataInputStream br;
    private final List<DatagramPacket> response;


    public Transfer(InputStream br, OutputStream bw) {
        this.bw = new DataOutputStream(bw);
        this.br = new DataInputStream(br);
        this.response = new ArrayList<>();
    }

    public void receiveResponse() throws IOException {
        byte[] byte_read = new byte[MAX_SIZE];
        int num_read = br.read(byte_read, OFFSET, MAX_SIZE);

        while (0 < num_read) {
            response.add(new DatagramPacket(byte_read.clone(), num_read));
            num_read = br.read(byte_read, OFFSET, MAX_SIZE);
        }
    }

    public void sendResponse() throws IOException {
        for (DatagramPacket r : response)
            bw.write(r.getData(), r.getOffset(), r.getLength());
    }

    public void transferResponse() throws IOException {
        int num_read = 0;
        byte[] byte_read = new byte[MAX_SIZE];

        num_read = br.read(byte_read, OFFSET, MAX_SIZE);

        while (0 < num_read) {
            bw.write(byte_read, OFFSET, num_read);
            bw.flush();
            num_read = br.read(byte_read, OFFSET, MAX_SIZE);
        }
    }
}
