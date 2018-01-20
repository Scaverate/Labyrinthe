package controler.controlerOnline;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.text.DateFormat;
import java.util.Date;

public class ClientProcessor implements Runnable{

    private Socket sock;
    private DatagramSocket socket;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    public ClientProcessor(Socket pSock){
        sock = pSock;
        try{
        socket = new DatagramSocket(4445);
        } catch(Exception e) {e.printStackTrace();}
    }

    public void run(){
        System.err.println("Lancement du traitement de la connexion cliente");
        while(!sock.isClosed()){
            try {
                writer = new PrintWriter(sock.getOutputStream());
                reader = new BufferedInputStream(sock.getInputStream());

                String response = read();
                InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();

                String debug;
                debug = "Thread : " + Thread.currentThread().getName() + ". ";
                debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";
                debug += " Sur le port : " + remote.getPort() + ".\n";
                debug += "\t -> Commande reçue : " + response + "\n";
                System.err.println("\n" + debug);

                writer.write(response.toUpperCase());
                //Il FAUT IMPERATIVEMENT UTILISER flush()
                //Sinon les données ne seront pas transmises au client
                //et il attendra indéfiniment
                writer.flush();

                if(response.equals("CLOSE")){
                    System.err.println("COMMANDE CLOSE DETECTEE ! ");
                    writer = null;
                    reader = null;
                    sock.close();
                    break;
                }
                else if(response.equals("MULTICAST")){
                    try {
                        byte[] buf;
                        String dString;
                        dString = "toAll";
                        buf = dString.getBytes();

                        InetAddress group = InetAddress.getByName("203.0.113.0");
                        DatagramPacket packet;
                        packet = new DatagramPacket(buf, buf.length, group, 4446);
                        socket.send(packet);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch(SocketException e){
                System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //La méthode que nous utilisons pour lire les réponses
    private String read() throws IOException{
        String response;
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }

}