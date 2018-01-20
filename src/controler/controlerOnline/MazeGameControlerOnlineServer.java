package controler.controlerOnline;

import model.EnvoiSocket;
import model.observable.MazeGame;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeGameControlerOnlineServer extends MazeGameControlerOnline {
    public MazeGameControlerOnlineServer(MazeGame mazeGame, String host, int port) {
        super(mazeGame);

        try {
            serverSocket = new ServerSocket(port, 100, InetAddress.getByName(host));
            System.out.println("server created");
            this.open();
        } catch(Exception ex) {
            Logger.getLogger(EnvoiSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void open(){
        Thread t = new Thread(new Runnable(){
            public void run(){
                while(isRunning){
                    try {
                        //On attend une connexion d'un client
                        Socket client = serverSocket.accept();

                        //Une fois reçue, on la traite dans un thread séparé
                        System.out.println("Connexion cliente reçue.");

                        Thread t = new Thread(new ClientProcessor(client));
                        t.start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("stopped running");

                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    serverSocket = null;
                }
            }
        });

        t.start();
    }

    /*public void close(){
        this.isRunning = false;
    }*/

    private ServerSocket serverSocket;
    private boolean isRunning = true;
}
