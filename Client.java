import java.net.*;
import java.io.*;

class Client {
    final int port;
    final String IP;
    private Socket s;
    private BufferedReader in;
    private Writer out;
    private BufferedReader usr;
    private String line;

    public Client(String IP_in, int Port) throws IOException, ConnectException{
        this.port = 50000;
        this.IP = IP_in;
        this.s = new Socket(this.IP, this.port);
        System.out.println("Connection established.");
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new OutputStreamWriter(s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));
        ClientCommunicate();
        KillSocket();
    }

    public void Send(String input) throws IOException{
        this.out.write(String.format("%s%n", input));
        this.out.flush();
    }

    public String Receive() throws IOException{
        System.out.println(this.line);
        return this.line;
    }


    public void KillSocket() throws IOException{
        this.s.shutdownOutput();
        System.out.println("Connection closed.");
    }

    public boolean out_check() throws IOException{
        this.line = this.usr.readLine();
        if (this.line == null || this.line.equals("")){
            return false;
        }
        return true;

    }

    public boolean in_check() throws IOException {
        this.line = this.in.readLine();
        if (this.line == null || this.line.equals("")) {
            return false;
        }
        return true;
    }

   public void ClientCommunicate() throws IOException{
       while (true) {
            if(out_check() == false) break;

           Send("aa");

           if(in_check() == false) break;

           Receive();
       }
    }
}
