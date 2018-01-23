package controler.controlerOnline;

import tools.EnvoiSocket;
import tools.ReceptionSocket;
import model.observable.MazeGame;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeGameControlerOnlineServer extends MazeGameControlerOnline implements Observer{
    public MazeGameControlerOnlineServer(MazeGame mazeGame, String host, int port) {
        super(mazeGame);

        try {
            serverSocket = new ServerSocket(port, 100, InetAddress.getByName(host));
            System.out.println("server created");
            this.open();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        if(this.mazeGame != null) {
            this.mazeGame.addObserver((Observer) this);
        }
    }

    public void close(){
        this.isRunning = false;
    }
    
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("update mazegame");

    	this.sendMessage("testserver");
		/*if(client != null) {
			try{
		        ArrayList<Object> objects = new ArrayList<>();
		        objects.add("UPDATED_SERVER");
		        ObjectOutputStream sortie = new ObjectOutputStream(serverSocket.getOutputStream());
		        sortie.writeObject(objects.size());
		        for(Object tmp : objects){
		            sortie.writeObject(tmp);
		        }
		        sortie.flush();
			} catch(IOException e) { e.printStackTrace(); }
		}*/
	}

    private void open(){
    	/*
        Thread t = new Thread(new Runnable(){
            public void run(){
                while(isRunning){
                    try {
                        //On attend une connexion d'un client
                        client = serverSocket.accept();

                        //Une fois reçue, on la traite dans un thread séparé
                        System.out.println("Connexion cliente reçue.");

                        Thread t = new Thread(new ClientProcessor(client, mazeGame));
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
        */

	        Thread ClientHandler = new Thread(new Runnable(){
	    		public void run(){
	    	        try {
		    	        client = serverSocket.accept();
		    	        System.out.println("Traitement de la connexion cliente - côté serveur");
		    	        PrintWriter writer = null;
		    	        BufferedInputStream reader = null;
		        		while(!serverSocket.isClosed()){
		    	            try {
		    	                writer = new PrintWriter(client.getOutputStream());
		    	                reader = new BufferedInputStream(client.getInputStream());
		    	
		    	                String query = read();
		    	                String debug;
		    	                debug = "\t -> Commande reçue : " + query + "\n";
		    	                System.err.println("\n" + debug);
		    	                
		    	                if(query == null) { return; }
		    	
		    	                ArrayList<Object> objects = new ArrayList<>();
		    	                objects.add(mazeGame);
		    	                ObjectOutputStream sortie = new ObjectOutputStream(client.getOutputStream());
		    	                sortie.writeObject(objects.size());
		    	                for(Object tmp : objects){
		    	                    sortie.writeObject(tmp);
		    	                }
		    	                sortie.flush();
		    	
		    	                if(query.equals("CLOSE")){
		    	                    System.err.println("COMMANDE CLOSE DETECTEE ! ");
		    	                    writer = null;
		    	                    reader = null;
		    	                    client.close();
		    	                    break;
		    	                }
		    	            }catch(SocketException e){
		    	            	e.printStackTrace();
		    	                System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
		    	                break;
		    	            } catch (IOException e) {
		    	                e.printStackTrace();
		    	            }
		        		}
		    		}catch (IOException e) {
		                e.printStackTrace();
		            }
	    		}
	        });
	        ClientHandler.start();
    }
    
    private String read() throws IOException{
        BufferedInputStream reader = new BufferedInputStream(client.getInputStream());;
        String response = null;
        int stream = 0;
    	try{
            byte[] b = new byte[4096];
            stream = reader.read(b);
            if(stream != -1) {
                response = new String(b, 0, stream);
            }
    	} catch(java.lang.StringIndexOutOfBoundsException ex) {
    		System.out.println(stream);
    		ex.printStackTrace();
		}
        return response;
    }
    
    private ArrayList<Object> sendMessage(String command) {
    	if(command == null || client == null) {
    		return null;
    	}
    	
    	try{
            //writer = new PrintWriter(client.getOutputStream());
            ArrayList<Object> objects = new ArrayList<>();
            objects.add(mazeGame);
            ObjectOutputStream sortie = new ObjectOutputStream(client.getOutputStream());
            sortie.writeObject(objects.size());
            for(Object tmp : objects){
                sortie.writeObject(tmp);
            }
            sortie.flush();
    	} catch(Exception e){ e.printStackTrace(); }
        
        // lecture et retour du message
        ArrayList<Object> recieveObjects = new ArrayList<>();
        try {
            ObjectInputStream entree = new ObjectInputStream(client.getInputStream());
            int taille = (int) entree.readObject();
            for(int i=0; i<taille; i++){
                recieveObjects.add(entree.readObject());
            }
            System.out.println(recieveObjects);
        } catch (IOException | ClassNotFoundException ex) {
        	ex.printStackTrace();
        }
        
        return recieveObjects.size() > 0 ? (ArrayList<Object>) recieveObjects.get(0) : null;
    }
    
    private ServerSocket serverSocket;
    private Socket client;
    private boolean isRunning = true;
    private PrintWriter writer;
    
    // tests
    public static void main(String[] args) {
    	MazeGameControlerOnlineServer mazeGameControlerOnlineServer = new MazeGameControlerOnlineServer(
			null,
			"127.0.0.1",
			1234
		);
    	MazeGameControlerOnlineClient mazeGameControlerOnlineClient = new MazeGameControlerOnlineClient(
			null,
			"127.0.0.1",
			1234
		);
    }
}
