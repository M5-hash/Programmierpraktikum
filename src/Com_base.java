package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;

public abstract class Com_base {


    protected final int port;
    protected Socket s;
    protected BufferedReader in;
    protected Writer out;
    protected BufferedReader usr;
    protected String line;


    public Com_base(){
        this.port = 50000;

    }

    public void Send(String input) throws IOException {
        this.out.write(String.format("%s%n", input));
        this.out.flush();
    }

    public String Receive() throws IOException{
        System.out.println(this.line);
        return this.line;
    }


    public void KillSocket() throws IOException{
        this.s.shutdownOutput();
        System.out.println("Connection closed.");
    }

    public boolean out_check() throws IOException{
        this.line = this.usr.readLine();
        if (this.line == null || this.line.equals("")){
            return false;
        }
        return true;

    }

    public boolean in_check() throws IOException {
        this.line = this.in.readLine();
        if (this.line == null || this.line.equals("")) {
            return false;
        }
        return true;
    }
}
