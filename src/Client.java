package src;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client extends Com_base {

    private final String IP;

    public Client(String IP_in) throws Exception{
        super();
        this.role_server = false;
        this.IP = IP_in;
        for(int i = 0; i < 10; i++){
            try {
                this.s = new Socket(this.IP, this.port);
                i = 10;
            } catch (IOException e){
                TimeUnit.SECONDS.sleep(1);
                if(i == 9){
                    JOptionPane.showMessageDialog(null, "Connection konnte nicht hergestellt werden");
                    TimeUnit.SECONDS.sleep(5);
                    System.exit(1);
                }
            }catch (NullPointerException n)
            {
                TimeUnit.SECONDS.sleep(1);
                if(i == 9){
                    JOptionPane.showMessageDialog(null, "Connection konnte nicht hergestellt werden");
                    TimeUnit.SECONDS.sleep(5);
                    System.exit(1);
                }
            }
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

        String[] in_size = ReceiveCheckedInputStream().split(" ");

        if (in_size[0].equals("size")) {
            config.fieldsize = Integer.parseInt(in_size[1]);
            setMyTurn(true);
            Send("done");


            String[] in_ships_Str = ReceiveCheckedInputStream().split(" ");
            int[] ships_int_arr = ParseStringArrayToIntArray(in_ships_Str, 1);
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

            setMyTurn(true);
            Send("done");

            if (ReceiveCheckedInputStream().equals("ready")) {

                setMyTurn(true);
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
