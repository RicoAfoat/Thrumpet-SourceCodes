import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
// import java.nio.charset.Charset;
import java.util.Scanner;
public class TestClient
{
    static Socket socket;
    static PrintWriter os;
    static Scanner userin;
    static Thread t2;
    public static void repOrt() throws IOException, InterruptedException
    {
        os=new PrintWriter(socket.getOutputStream());
        userin=new Scanner(System.in);
        t2=new Thread(new Listener(socket));
        t2.setDaemon(true);
        t2.start();
        String info=null;
        while(true)
        {
            info=userin.nextLine();
            // info=new String(info.getBytes(),"utf8");
            os.println(info);
            os.flush();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException
    {
        // System.out.println("system:"+Charset.defaultCharset());
        socket=new Socket(args[0],Integer.parseInt(args[1]));
        // socket=new Socket("43.139.26.143",5130);
        TestClient.repOrt();
        System.out.println("system:Currently Offline\nYou can restart the program again\n");
        return;
    }
}