package src;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//
public class Client extends Com_base {

    private final String IP;

    public Client(String IP_in, JFrame loadScreen) throws Exception{
        super(loadScreen);
        this.role_server = false;
        this.IP = IP_in;
        try {
            this.s = new Socket(this.IP, this.port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection konnte nicht hergestellt werden");
        }
        this.SocketActive = true;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new OutputStreamWriter(s.getOutputStream());
        this.pf = setupPlayingfield();
        if(config.onlineCom && this.comPl == null) {
            this.comPl = new ComPlayerNormal(this.pf);
        }

        config.fieldsize = this.pf.getField().length;
    }

    protected PlayingField setupPlayingfield() throws Exception {
        PlayingField pf_holder;

        String[] in_size = loopCheckIN(false).split(" ");

        if (in_size[0].equals("size")) {
            config.fieldsize = Integer.parseInt(in_size[1]);
            setTurn(true);
            Send("done");


            String[] in_ships_Str = loopCheckIN(false).split(" ");
            int[] ships_int_arr = ship_array_toInt(in_ships_Str, 1);
            config.size2 = 0;
            config.size3 = 0;
            config.size4 = 0;
            config.size5 = 0;

            for(int i = 0; i<ships_int_arr.length; i++) {
                if (ships_int_arr[i]==2){config.size2++;}
                else if (ships_int_arr[i]==3){config.size3++;}
                else if (ships_int_arr[i]==4){config.size4++;}
                else if (ships_int_arr[i]==5){config.size5++;}
            }
            pf_holder = new PlayingField(Integer.parseInt(in_size[1]), ships_int_arr, role_server);

            setTurn(true);
            Send("done");

            if (loopCheckIN(false).equals("ready")) {

                setTurn(true);
                Send("ready");
            }
        }
        else{
            this.loaded = true;
            if(config.onlineCom){
                this.comPl = new ComPlayerNormal(Long.parseLong(in_size[1]));
                pf_holder = this.comPl.getPlayingField();
            }else {
                pf_holder = new PlayingField();
                pf_holder.loadGame(Long.parseLong(in_size[1]));
            }
        }

        this.myTurn = false;
        return pf_holder;
    }
}
