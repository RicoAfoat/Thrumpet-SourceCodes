import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class tcpListener implements Runnable{
    private static boolean flag=true;
    BufferedReader br;
    Socket socket;
    PrintWriter os;
    ServerSocket serverSocket;
    InputStreamReader io;
    public static void modify()
    {
        flag=!flag;
    }
    public static boolean pan()
    {
        return flag;
    }
    //synchronized，没关系，没有你，我照样可以写代码。
    //？反正多跑一次无所谓?
    public void run(){

        System.out.println("tcpListener start!\n");
        try
        {
            int listenPort=5130;
            serverSocket = new ServerSocket(listenPort);
            System.out.println("Listenning to get port...\n");
            while(flag)
            {
                socket=new Socket();
                socket=serverSocket.accept();
                os=new PrintWriter(socket.getOutputStream());
                io=new InputStreamReader(socket.getInputStream());
                br=new BufferedReader(io);
                try
                {
                    String info=null;
                    do
                    {
                        if(info!=null)
                        {
                            os.println("system:重名,继续输入");
                            os.flush();
                        }
                        info=br.readLine();
                        info=info.substring(info.indexOf(":")+1);
                    }while(ClientThread.ifexi(info));
                    if(!socket.isClosed())new Thread(new ClientThread(socket,info)).start();
                }catch(IOException e){throw new RuntimeException(e);};
            }
            serverSocket.close();
        }catch(IOException e){throw new RuntimeException(e);}
        return;
    }
}
