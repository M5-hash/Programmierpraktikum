import java.net.*;
import java.io.*;
import java.util.Enumeration;

class Server {

    final int port;
    private ServerSocket ss;
    private Socket s;
    private BufferedReader in;
    private Writer out;
    private BufferedReader usr;
    private String line;

    public Server(int Port) throws IOException, NullPointerException {

        this.port = 50000;
        this.ss = new ServerSocket(port);


        IP_Ausgabe();
        System.out.println("Waiting for client connection ...");
        this.s = this.ss.accept();
        System.out.println("Connection established.");
        this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.out = new OutputStreamWriter(this.s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));
        ServerCommunicate();
        KillSocket();

    }

    public void IP_Ausgabe() throws IOException {
        Enumeration<NetworkInterface> nis =
                NetworkInterface.getNetworkInterfaces();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            Enumeration<InetAddress> ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = ias.nextElement();
                if (!ia.isLoopbackAddress()) {
                    System.out.print(" " + ia.getHostAddress());
                }
            }
        }
    }

    public void Send(String input) throws IOException{
        this.out.write(String.format("%s%n", input));
        this.out.flush();
    }

    public String Receive() throws IOException{
        System.out.println(this.line);
        return this.line;
    }

    public boolean out_check() throws IOException {
        this.line = this.usr.readLine();
        if (this.line == null || this.line.equals("")) {
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

    public void ServerCommunicate() throws IOException {
        while (true) {
            if(in_check() == false) break;

            Receive();

            if(out_check() == false) break;

            Send("aa");
        }

    }

    public void KillSocket() throws IOException{
        this.s.shutdownOutput();
        System.out.println("Connection closed.");
    }
}
