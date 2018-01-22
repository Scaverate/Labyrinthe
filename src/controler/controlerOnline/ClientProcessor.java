package controler.controlerOnline;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class ClientProcessor implements Runnable{

    private Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    public ClientProcessor(Socket pSock){
        sock = pSock;
    }

    public void run(){
        System.out.println("Lancement du traitement de la connexion cliente");
        while(!sock.isClosed()){
            try {
            	System.out.println("test");
                writer = new PrintWriter(sock.getOutputStream());
                reader = new BufferedInputStream(sock.getInputStream());

                String query = read();
            	System.out.println("testtest");
                InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();

                String debug;
                debug = "Thread : " + Thread.currentThread().getName() + ". ";
                debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";
                debug += " Sur le port : " + remote.getPort() + ".\n";
                debug += "\t -> Commande reçue : " + query + "\n";
                System.err.println("\n" + debug);

                writer.write(query.toUpperCase());
                //Il FAUT IMPERATIVEMENT UTILISER flush()
                //Sinon les données ne seront pas transmises au client
                //et il attendra indéfiniment
                writer.flush();

                if(query.equals("CLOSE")){
                    System.err.println("COMMANDE CLOSE DETECTEE ! ");
                    writer = null;
                    reader = null;
                    sock.close();
                    break;
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