package controler.controlerOnline;

import model.observable.MazeGame;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class ClientProcessor implements Runnable{
    public ClientProcessor(Socket socket, MazeGame mazeGame){
        this.mazeGame = mazeGame;
        this.socket = socket;
    }

    public void run(){
        PrintWriter writer = null;
        System.out.println("Lancement du traitement de la connexion cliente");
        while(!this.socket.isClosed()){
            try {
                writer = new PrintWriter(this.socket.getOutputStream());
                reader = new BufferedInputStream(this.socket.getInputStream());

                String query = read();

                String debug;
                debug = "\t -> Commande reçue : " + query + "\n";
                System.err.println("\n" + debug);

                ArrayList<Object> objects = new ArrayList<>();
                objects.add(this.mazeGame);
                ObjectOutputStream sortie = new ObjectOutputStream(this.socket.getOutputStream());
                sortie.writeObject(objects.size());
                for(Object tmp : objects){
                    sortie.writeObject(tmp);
                }
                sortie.flush();

                if(query.equals("CLOSE")){
                    System.err.println("COMMANDE CLOSE DETECTEE ! ");
                    writer = null;
                    reader = null;
                    this.socket.close();
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

    private Socket socket;
    private MazeGame mazeGame;
    private BufferedInputStream reader = null;

}