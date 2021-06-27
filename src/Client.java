package src;

import src.Com_base;

import java.net.*;
import java.io.*;

class Client extends Com_base {

    private final String IP;

    public Client(String IP_in) throws Exception {
        super();
        System.out.println("Bitte geben Sie die IP Ihres Spielpartners ein:");
        this.IP = IP_in;
        this.s = new Socket(this.IP, this.port);
        System.out.println("Connection established.");
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new OutputStreamWriter(s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));
        this.pf = setupPlayingfield();
        this.run();
    }

    protected PlayingField setupPlayingfield() throws IOException {
        PlayingField pf_holder;
        String[] in_size = Receive().split(" ");
        if (in_size[0].equals("size")) {
            pf_holder = new PlayingField(Integer.parseInt(in_size[1]));

            Send("done");

            String[] in_ships = Receive().split(" ");
            //Schiffe setzen param: ship_array(in_ships);
            Send("done");
        }

        //else if(in_size[0].equals("load"))

        else{
        pf_holder = new PlayingField(0);
        pf_holder.loadGame(Long.valueOf(in_size[1]));
    }

        if (Receive().equals("ready")) {

        }
        this.myTurn = false;
        return pf_holder;
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
