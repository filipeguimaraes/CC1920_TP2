import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnonGw {

    public static void main(String[] args){

        int argc = args.length;
        String target_server = args[1];
        int port = Integer.parseInt(args[3]);
        List<String> overlay_peers = new ArrayList<>(Arrays.asList(args).subList(5, argc));

        for(String s : overlay_peers){
            System.out.println(s);
        }



    }
}
