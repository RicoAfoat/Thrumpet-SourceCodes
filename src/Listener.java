import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Listener implements Runnable
{
    static Socket socket;
    static InputStreamReader is;
    static BufferedReader br;
    public Listener(Socket sockEt) throws IOException
    {
        socket=sockEt;
        is=new InputStreamReader(socket.getInputStream());
        br=new BufferedReader(is);
    }
    public void run()
    {
        String info=null;
        File file=new File("LOGOUTPUT.txt");
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        try
        {
            PrintWriter pw=new PrintWriter(file);
            while(socket.isClosed()==false)
            {
                info=br.readLine();
                if(info!=null)
                {
                    // info=new String(info.getBytes(),"GBK");
                    // info=new String(info.getBytes(),"UTF-8");
                    // info=new String(info.getBytes("GBK"));
                    System.out.println(info);
                    pw.println(info);
                    pw.flush();
                }
            }
            socket.close();
            pw.close();
        }catch(IOException e){throw new RuntimeException(e);};
        return;
    }
}
//用户开写时记得暂停输出