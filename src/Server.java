package src;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
//
public class Server extends Com_base {




    private ServerSocket ss;


    public Server(int in_size, String in_ships) throws Exception{
        super();

        this.role_server = true;
        try{
        this.ss = new ServerSocket(this.port);
        this.s = this.ss.accept();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection konnte nicht hergestellt werden");
            TimeUnit.SECONDS.sleep(5);
            System.exit(1);
        }
        this.SocketActive = true;
        this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.out = new OutputStreamWriter(this.s.getOutputStream());
        this.pf = setupPlayingfield(in_size, in_ships);
        if(config.onlineCom && this.comPl == null) {
            this.comPl = new ComPlayerNormal(this.pf);
        }

        config.fieldsize = this.pf.getField().length;
    }





    protected PlayingField setupPlayingfield(int in_size, String in_ships) throws Exception {
        PlayingField pf_holder ;

        if(!config.filepath.equals("")){
            if(config.onlineCom){
                this.comPl = new ComPlayerNormal(config.filepath);
                pf_holder = this.comPl.getPlayingField();
            }else {
                pf_holder = new PlayingField();
                pf_holder.loadGame(config.filepath);
            }
            TimeUnit.MILLISECONDS.sleep(100);
            this.loaded = true;
            setMyTurn(true);
            this.Send("load "+pf_holder.getFilenameLongID(config.filepath));
            setMyTurn(true);
        }
        else {
            pf_holder = new PlayingField(in_size, ship_array_toInt(in_ships.split(" "), 0), role_server);
            TimeUnit.MILLISECONDS.sleep(100);

            setMyTurn(true);
            Send("size " + in_size);

            if (ReceiveCheckedInputStream().equals("done")) {

                setMyTurn(true);
                Send("ships " + in_ships);
            }


            if (ReceiveCheckedInputStream().equals("done")) {

                setMyTurn(true);
                Send("ready");
            }
            if (ReceiveCheckedInputStream().equals("ready")) ;


            this.myTurn = true;
        }
        return pf_holder;
    }
}