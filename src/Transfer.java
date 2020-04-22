import java.io.*;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Transfer {
    private DataOutputStream bw;
    private DataInputStream br;

    private List<DatagramPacket> response;

    private static final int OFFSET = 0;
    private static final int MAX_SIZE = 5;


    public Transfer(InputStream br, OutputStream bw) {
        this.bw = new DataOutputStream(bw);
        this.br = new DataInputStream(br);
        this.response = new ArrayList<>();
    }

    public void receiveResponse() throws IOException {
        int num_read = 0;
        byte[] byte_read = new byte[MAX_SIZE];
        int off_set = 0;

        num_read = br.read(byte_read, OFFSET, MAX_SIZE);
        response.add(new DatagramPacket(byte_read,off_set,num_read));
        off_set += num_read;

        while (0 < num_read) {
            num_read = br.read(byte_read, OFFSET, MAX_SIZE);
            response.add(new DatagramPacket(byte_read,off_set,num_read));
            off_set += num_read;
        }
    }

    public void sendResponse() throws IOException {
        for (DatagramPacket r : response) bw.write(r.getData(),OFFSET,r.getLength());
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
