package src;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Client extends Com_base {

    private final String IP;

    public Client(String IP_in) throws Exception {
        super();
        this.role_server = false;
        this.IP = IP_in;
        this.s = new Socket(this.IP, this.port);
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new OutputStreamWriter(s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));
        this.pf = setupPlayingfield();

    }

    protected PlayingField setupPlayingfield() throws IOException {
        PlayingField pf_holder = null;
        while (!this.in_check()){

        }
        String[] in_size = Receive().split(" ");
        if (in_size[0].equals("size")) {

            while (!this.out_check()){}
            Send("done");

            while (!this.in_check()){}
            String[] in_ships_Str = Receive().split(" ");
            new PlayingField(Integer.parseInt(in_size[1]), ship_array_toInt(in_ships_Str, 1), role_server);

            while (!this.out_check()){}
            Send("done");
        }


        else{
            pf_holder = new PlayingField();
            pf_holder.loadGame(Long.valueOf(in_size[1]));
        }


        while (!this.in_check()){}
        if (Receive().equals("ready")) {}
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
