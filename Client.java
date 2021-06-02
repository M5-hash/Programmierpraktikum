import src.Com_base;

import java.net.*;
import java.io.*;

class Client extends Com_base {

    private final String IP;

    public Client() throws IOException{
        super();
        System.out.println("Bitte geben Sie die IP Ihres Spielpartners ein:");
        //
        // IP einlesen implementieren
        //
        // Remove test IP
        this.IP = "192.168.206.1";
        this.s = new Socket(this.IP, this.port);
        System.out.println("Connection established.");
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new OutputStreamWriter(s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));
        ClientCommunicate();
        KillSocket();
    }

   public void ClientCommunicate() throws IOException{
       while (true) {
            if(!out_check()) break;

           Send("aa");

           if(!in_check()) break;

           Receive();
       }
    }
}
