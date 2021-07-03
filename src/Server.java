package src;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Server extends Com_base {


    private ServerSocket ss;


    public Server(int in_size, String in_ships) throws Exception {
        super();
        this.role_server = true;
        this.ss = new ServerSocket(this.port);
        this.s = this.ss.accept();
        this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.out = new OutputStreamWriter(this.s.getOutputStream());
        this.pf = setupPlayingfield(in_size, in_ships);
        if(config.onlineCom) {
            this.comPl = new ComPlayerNormal(this.pf);
        }
        System.out.println("Working");

    }





    protected PlayingField setupPlayingfield(int in_size, String in_ships) throws Exception {
        PlayingField pf_holder ;

        if(!config.filepath.equals("")){
            pf_holder = new PlayingField();

            pf_holder.loadGame(config.filepath);
            TimeUnit.MILLISECONDS.sleep(100);
            loaded = true;
            setTurn(true);
            this.Send("load "+pf_holder.getFilenameLongID(config.filepath));
        }
        else {
            pf_holder = new PlayingField(in_size, ship_array_toInt(in_ships.split(" "), 0), role_server);
            TimeUnit.MILLISECONDS.sleep(100);

            setTurn(true);
            Send("size " + in_size);

            if (loopCheckIN(false).equals("done")) {

                setTurn(true);
                Send("ships " + in_ships);
            }


            if (loopCheckIN(false).equals("done")) {

                setTurn(true);
                Send("ready");
            }
            if (loopCheckIN(false).equals("ready")) ;


            this.myTurn = true;
        }
        return pf_holder;
    }
}