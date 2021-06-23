package src;

import src.Com_base;

import java.net.*;
import java.io.*;

class Client extends Com_base {

    private final String IP;

    public Client() throws IOException {
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

        String[] in_size = Receive().split(" ");
        if (in_size[0].equals("size")) {
            this.pf = new PlayingField(Integer.parseInt(in_size[1]));

            Send("done");

            String[] in_ships = Receive().split(" ");
            //Schiffe setzen param: ship_array(in_ships);
            Send("done");
        }
        else if(in_size[0].equals("load")){
            this.pf = new PlayingField(0);
            pf.loadGame(Long.valueOf(in_size[1]));
        }

        if (Receive().equals("ready")) {

        }
        ClientCommunicate();
        KillSocket();
    }

   public void ClientCommunicate() throws IOException{
       while (true) {

           if(!in_check()) break;

           Receive();

           if(!out_check()) break;

           Send("aa");
       }
    }
}
