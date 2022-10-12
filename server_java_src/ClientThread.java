import java.net.Socket;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClientThread implements Runnable,Comparable<ClientThread>{
    private Socket socket;
    PrintWriter os;
    InputStreamReader io;
    BufferedReader br;
    String name;
    @Override
    public int compareTo(ClientThread A)
    {
        return this.name.compareTo(A.name);
    }
    private static TreeSet<ClientThread> transtalker=new TreeSet<ClientThread>();
    public static synchronized void addSet(ClientThread A)
    {
        if(!transtalker.contains(A))transtalker.add(A);
    }
    public static synchronized void delSet(ClientThread A)
    {
        if(transtalker.contains(A))transtalker.remove(A);
    }
    public static synchronized ClientThread findSet(String nAme)
    {
        return transtalker.floor(new ClientThread(null, nAme));
    }
    public static boolean ifexi(String nAme)
    {
        if(ClientThread.findSet(nAme)!=null)return ClientThread.findSet(nAme).name.equals(nAme);
        return false;
    }
    public synchronized void listput() throws IOException
    {
        for(ClientThread op:transtalker)os.println("list:"+op.name);
        os.flush();
    }
    public ClientThread(Socket sockEt,String nAme)
    {
        socket=sockEt;
        name=nAme;
        if(socket!=null&&nAme!=null)
        {
            System.out.println("Registration from "+name+" "+socket.getInetAddress().toString()+":"+socket.getPort()+"\n");
            addSet(this);
            try
            {
                os=new PrintWriter(socket.getOutputStream());
                io=new InputStreamReader(socket.getInputStream());
                br=new BufferedReader(io);
            }catch(IOException e){throw new RuntimeException(e);};
        }
    }
    private void sendMessage(String argv)
    {
        System.out.println(argv);
        os.write(argv+'\n');
        os.flush();
    }
    private void talkto(String nAme)
    {
        if(nAme.substring(nAme.indexOf(":")+1).equals("list"))
        {
            try {
                listput();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        int breakpoint=nAme.indexOf(":");
        ClientThread tarGet=ClientThread.findSet(nAme.substring(0, breakpoint));
        if(tarGet==null||!tarGet.name.equals(nAme.substring(0, breakpoint)))
        {
            os.write("system:Error,the target user is currently offline\n");
            os.flush();
            return;
        }
        tarGet.sendMessage(name+":"+nAme.substring(breakpoint+1));
    }
    public void run()
    {
        os.write("system:Successful Registration\n");
        os.flush();
        try {
            listput();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try
        {
            String info=null;
            while((info=br.readLine())!=null)talkto(info);
        }catch(IOException e){throw new RuntimeException(e);};
        System.out.println(name+"END\n");
        ClientThread.delSet(this);
    }
}