package controler.controlerOnline;

import model.observable.MazeGame;
import tools.ReceptionSocket;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeGameControlerOnlineClient extends MazeGameControlerOnline {
    public MazeGameControlerOnlineClient(MazeGame mazeGame, String host, int port) {
        super(mazeGame);

        try {
            connexion = new Socket(host, port);
            writer = new PrintWriter(connexion.getOutputStream(), true);
            this.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //this.response = sendMessage("GETMAZEGAME");
        //this.response = sendMessage("TOTO");

    }
    
    private void open() {
        Thread ServerHandler = new Thread(new Runnable(){
    		public void run(){
    	        System.out.println("Traitement de la connexion serveur- côté client");
        		while(!connexion.isClosed()){
    	            try {
    	                writer = new PrintWriter(connexion.getOutputStream());
    	
    	                String query = read();
    	                String debug;
    	                debug = "\t -> Commande reçue : " + query + "\n";
    	                System.err.println("\n" + debug);
    	                
    	                if(query == null) { return; }
    	
    	                ArrayList<Object> objects = new ArrayList<>();
    	                objects.add("testfromclienttoserver");
    	                ObjectOutputStream sortie = new ObjectOutputStream(connexion.getOutputStream());
    	                sortie.writeObject(objects.size());
    	                for(Object tmp : objects){
    	                    sortie.writeObject(tmp);
    	                }
    	                sortie.flush();
    	
    	                if(query.equals("CLOSE")){
    	                    System.err.println("COMMANDE CLOSE DETECTEE ! ");
    	                    writer = null;
    	                    connexion.close();
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
        		System.out.println("isClosed");
    		}
        });
        ServerHandler.start();
    }
    
    private String read() throws IOException{
        BufferedInputStream reader = new BufferedInputStream(connexion.getInputStream());;
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
    	if(command == null) {
    		return null;
    	}
        writer.write(command);
        writer.flush();
        
        // lecture et retour du message
        ArrayList<Object> recieveObjects = new ArrayList<>();
        try {
            ObjectInputStream entree = new ObjectInputStream(connexion.getInputStream());
            int taille = (int) entree.readObject();
            for(int i=0; i<taille; i++){
                recieveObjects.add(entree.readObject());
            }
            System.out.println(recieveObjects);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReceptionSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return recieveObjects.size() > 0 ? (ArrayList<Object>) recieveObjects.get(0) : null;
    }

    private Socket connexion;
    private ArrayList<Object> response;
    private PrintWriter writer;
}
