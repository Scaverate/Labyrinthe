package tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * 
 */
public class Introspection {
	/**
	 * private pour empecher de creer des instances de la classe
	 */
	private Introspection() {}
		
	/**
	 * Invocation d'une methode connaissant son nom sur un objet o
	 * en lui passant les bons parametres
	 * 
	 * @param o - l'objet sur lequel agit la m�thode
	 * @param args - la liste des param�tres de la m�thode
	 * @param nomMethode - le nom de la m�thode
	 * @return la m�thode invoqu�e
	 * @throws Exception
	 */
	public static Object invoke(Object o, Object[] args, String nomMethode) throws Exception	{
		Class<? extends Object>[] paramTypes = null;
		if(args != null){
			paramTypes = new Class<?>[args.length];
			for(int i=0;i<args.length;++i)	{
				paramTypes[i] = args[i].getClass();
			}
		}
		Method m = o.getClass().getMethod(nomMethode,paramTypes);
		return m.invoke(o,args);
	}
	

	/**
	 * creation d'un objet connaissant le nom de la classe
	 * utilise un constructeur sans parametre
	 * 
	 * @param className
	 * @return le nouvel objet cree
	 */
	public static Object newInstance(String className) {
		Object o = null;
		try	    {
			o = Class.forName (className).newInstance ();
		}
		catch (ClassNotFoundException e)	    {
			// La classe n'existe pas
			e.printStackTrace();
		}
		catch (InstantiationException e)	    {
			// La classe est abstract ou est une interface ou n'a pas de constructeur accessible sans param�tre
			e.printStackTrace();
		}
		catch (IllegalAccessException e)	    {
			// La classe n'est pas accessible
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * construction a partir du nom de la classe et des parametres du constructeur
	 * 
	 * @param className
	 * @param args - la liste des arguments du constructeur
	 * @return le nouvel objet cree
	 */
	public static Object newInstance(String className, Object[] args) {
		Object o = null;

		try {
			//On cree un objet Class
			Class<?> classe = Class.forName(className);
			// on recupere le constructeur qui a les parametres args
			Class<?>[] paramTypes = null;
			if(args != null){
				paramTypes = new Class[args.length];
				for(int i=0;i<args.length;++i)	{
					paramTypes[i] = args[i].getClass();
				}
			}
			Constructor<?> ct = classe.getConstructor(paramTypes);
			// on instantie un nouvel objet avec ce constructeur et le bon parametre
			o =  ct.newInstance (args);		
		}
		catch (ClassNotFoundException e) {
			// La classe n'existe pas
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			// La classe n'a pas le constructeur recherche
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			// La classe est abstract ou est une interface
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// La classe n'est pas accessible
			e.printStackTrace();
		}
		catch (java.lang.reflect.InvocationTargetException e) {
			// Exception declenchee si le constructeur invoque
			// a lui-meme declenche une exception
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)		{
			// Mauvais type de parametre			
			e.printStackTrace();
		}
		return o;
	}



}
