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

        Thread t = new Thread() {
            public void run(){
                try{
                    while (true) {
                        message_check(loopCheckIN());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        System.out.println("Working");
    }

    protected PlayingField setupPlayingfield() throws Exception {
        PlayingField pf_holder = null;

        String[] in_size = loopCheckIN().split(" ");
        System.out.println("true");
        if (in_size[0].equals("size")) {
            System.out.println("true");
            Send("done");


            String[] in_ships_Str = loopCheckIN().split(" ");
            pf_holder = new PlayingField(Integer.parseInt(in_size[1]), ship_array_toInt(in_ships_Str, 1), role_server);

            Send("done");

            if (loopCheckIN().equals("ready")) {
                Send("ready");
            }

        }
        else {
            pf_holder = new PlayingField();
            pf_holder.loadGame(Long.valueOf(in_size[1]));
        }
        this.myTurn = false;
        return pf_holder;
    }

    public void ClientCommunicate() throws Exception {
        while (true) {
            if(!in_check()) break;
            Receive();

            if(!out_check()) break;
            Send("C");
        }
    }
}
