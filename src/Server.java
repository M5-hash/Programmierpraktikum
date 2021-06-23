package src;

import src.Com_base;

import java.net.*;
import java.io.*;
import java.util.Enumeration;

class Server extends Com_base {


    private ServerSocket ss;


    public Server(String start_mode, int in_size, String in_ships) throws IOException, NullPointerException {

        super();
        this.ss = new ServerSocket(this.port);


        IP_Ausgabe();
        System.out.println("Waiting for client connection ...");
        this.s = this.ss.accept();
        System.out.println("Connection established.");
        this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.out = new OutputStreamWriter(this.s.getOutputStream());
        this.usr = new BufferedReader(new InputStreamReader(System.in));


        if(start_mode.equals("setup")){
            this.pf = new PlayingField(in_size);
            Send("size "+ in_size);
            if(Receive().equals("done")){
                Send("ships " + in_ships);
            }
            if(Receive().equals("done")){
                Send("ready");
            }
        }
        else{
            this.pf = new PlayingField(0);
            pf.loadGame(Long.valueOf(start_mode.split(" ")[1]));
        }

        ServerCommunicate();
        KillSocket();
    }



    public void IP_Ausgabe() throws IOException {
        Enumeration<NetworkInterface> nis =
                NetworkInterface.getNetworkInterfaces();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            Enumeration<InetAddress> ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = ias.nextElement();
                if (!ia.isLoopbackAddress()) {
                    System.out.print(" " + ia.getHostAddress());
                }
            }
        }
    }

    protected boolean setup_server(){

        //send(Größe Spielfeld)
        //if receive != done ???
        //send(Schiffanzahl)
        //if receive != done ???
        //send("ready")
        //if receive != ready ???
        // ServerCommunicate();
        return true;
    }

    public void ServerCommunicate() throws IOException {
        while (true) {

            if(!out_check()) break;

            Send("aa");

            if(!in_check()) break;

            Receive();
        }
    }
}
