import src.Com_base;

import java.net.*;
import java.io.*;
import java.util.Enumeration;

class Server extends Com_base {


    private ServerSocket ss;


    public Server() throws IOException, NullPointerException {

        super();
        this.ss = new ServerSocket(this.port);


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


    public void ServerCommunicate() throws IOException {
        while (true) {
            if(!in_check()) break;

            Receive();

            if(!out_check()) break;

            Send("aa");
        }
    }
}
