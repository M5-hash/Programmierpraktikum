package src;

import src.Com_base;

import java.net.*;
import java.io.*;
import java.util.Enumeration;
import java.util.ArrayList;

class Server extends Com_base {


    private ServerSocket ss;


    public Server(String start_mode, int in_size, String in_ships) throws Exception{

        super();
        this.ss = new ServerSocket(this.port);

        IP_Ausgabe();
        System.out.println("Waiting for client connection ...");
        this.s = this.ss.accept();
        System.out.println("Connection established.");
        this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.out = new OutputStreamWriter(this.s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));
        this.pf = setupPlayingfield(start_mode, in_size, in_ships);
        this.run();
    }

    protected PlayingField setupPlayingfield(String start_mode, int in_size, String in_ships) throws IOException{
        PlayingField pf_holder;
        if(start_mode.equals("setup")){
            pf_holder = new PlayingField();
            Send("size "+ in_size);
            if(Receive().equals("done")){
                Send("ships " + in_ships);
            }
            if(Receive().equals("done")){
                Send("ready");
            }
        }

        else{
            pf_holder = new PlayingField();
            pf_holder.loadGame(Long.valueOf(start_mode.split(" ")[1]));
        }

        this.myTurn = true;
        return pf_holder;
    }

    public static String[] IP_Ausgabe() throws IOException {
        ArrayList<String> IPs = new ArrayList<String>();
        Enumeration<NetworkInterface> nis =
                NetworkInterface.getNetworkInterfaces();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            Enumeration<InetAddress> ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = ias.nextElement();
                if (!ia.isLoopbackAddress() && ia.getHostAddress().startsWith("192.")) {
                    IPs.add(ia.getHostAddress());
                }
            }
        }
        return IPs.toArray(new String[0]);
    }


    public void ServerCommunicate() throws IOException {
        while (true) {
            if(!out_check()) break;
            Send(" ");

            if(!in_check()) break;
            Receive();
        }
    }
}
