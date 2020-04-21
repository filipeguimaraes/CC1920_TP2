import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Transfer {
    private DataOutputStream bw;
    private DataInputStream br;

    private static final int OFFSET = 0;
    private static final int MAX_SIZE = 5;


    public Transfer(DataInputStream br, DataOutputStream bw) {
        this.bw = bw;
        this.br = br;
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
