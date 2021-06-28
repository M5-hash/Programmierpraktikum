package src;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Server extends Com_base {


    private ServerSocket ss;


    public Server(String start_mode, int in_size, String in_ships) throws Exception{
        super();
        this.role_server = true;
        this.ss = new ServerSocket(this.port);
        this.s = this.ss.accept();
        this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.out = new OutputStreamWriter(this.s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));
        this.pf = setupPlayingfield(start_mode, in_size, in_ships);


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

    protected PlayingField setupPlayingfield(String start_mode, int in_size, String in_ships) throws Exception{
        PlayingField pf_holder;
        if(start_mode.equals("setup")){

            pf_holder = new PlayingField(in_size, ship_array_toInt(in_ships.split(" "), 0), role_server);
            TimeUnit.SECONDS.sleep(5);
            Send("size "+ in_size);

            if(loopCheckIN().equals("done")){

                Send("ships " + in_ships);
            }


            if(loopCheckIN().equals("done")){


                Send("ready");
            }
            if(loopCheckIN().equals("ready"));
        }

        else{
            pf_holder = new PlayingField();
            pf_holder.loadGame(Long.valueOf(start_mode.split(" ")[1]));
        }

        this.myTurn = true;
        return pf_holder;
    }



    public void ServerCommunicate() throws Exception {
        while (true) {
            if(!out_check()) break;
            Send("S");

            if(!in_check()) break;
            Receive();
        }
    }
}
