package src;

import java.lang.Thread;
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
        this.pf = setupPlayingfield();

    }

    protected PlayingField setupPlayingfield() throws Exception {
        PlayingField pf_holder = null;

        String[] in_size = loopCheckIN().split(" ");

        if (in_size[0].equals("size")) {
            config.fieldsize = Integer.parseInt(in_size[1]);
            setTurn(true);
            Send("done");


            String[] in_ships_Str = loopCheckIN().split(" ");
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

            if (loopCheckIN().equals("ready")) {

                setTurn(true);
                Send("ready");
            }
        }
        else{
            pf_holder = new PlayingField();
            pf_holder.loadGame(Long.valueOf(in_size[1]));
        }

        this.myTurn = false;
        return pf_holder;
    }
}
