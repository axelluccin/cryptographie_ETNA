import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
class des{
    public static void main(String[] args){
    	//implémentatation méthode lecture des entrées du clavier et déclaration des varibales
    	Scanner sc = new Scanner(System.in);
		String str;
		int longueur;
		//menu avec 3 possibilités: message, fichier ou quitte
    	System.out.println("Tapez 1 si vous souhaitez entré votre message directement");
    	System.out.println("Tapez 2 si vous souhaitez récupérer le message dans un fichier");
    	System.out.println("Tapez 0 si vous souhaitez quitter");
        System.out.println("Saisissez votre choix:");
        str = sc.nextLine();
        System.out.println("Vous avez saisi: " + str);
        //selon choix, on se dirige dans le switch
        switch (str)
        {
        	case "1":
        	//on entre directement le message
        		System.out.println("Veuillez entrer votre message:");
        		str = sc.nextLine();
				System.out.println("Vous avez saisi: " + str);
				//on détermine la taille en caractères
				longueur = str.length();
				System.out.println("la chaine de caractère fait " + longueur + " caractères");
				//on détermine la taille en bits
				longueur = longueur * 8;
				System.out.println("la chaine de caractère fait " + longueur + " bits");
				//byte[] rec = new byte[8];
        		break;
        	case "2":
        	//on récupère le fichier
        		System.out.println("Veuillez copier votre fichier à côté de ce programme:");
        		System.out.println("Veuillez entrer le nom exact du fichier ex:MonFichier.txt");
        		str = sc.nextLine();
        		File fichier = new File(str);
        		System.out.println("Chemin absolu du fichier: " + fichier.getAbsolutePath());
        		System.out.println("Nome du fichier: " + fichier.getName());
        		System.out.println("Est-ce qu'il existe? " + fichier.exists());
        		System.out.println("Est-ce un répertoire? " + fichier.isDirectory());
        		System.out.println("Est-ce un fichier? " + fichier.isFile());
        		boolean exist = fichier.isFile();
        		if(exist)
        			if(fichier.length() <= 1)
        				System.out.println("La taille du fichier est de: " + fichier.length() + " octet");
        			else
						System.out.println("La taille du fichier est de: " + fichier.length() + " octets");
				//fin va lire le fichier
				//fout va écrire dans un autre fichier
        		FileInputStream fin = null;
        		FileOutputStream fout = null;
        		try
        		{
        			fin = new FileInputStream(new File(str));
        			fout = new FileOutputStream(new File("test2.txt"));
    				//on créé un tableau de byte en lui donnant comme taille le nombre d'octet à lire
    				byte[] rec = new byte[8];
    				//on créé une variable pour affecter ce que l'on as vu
    				int lec = 0;
    				//on boucle car tant que l'on peut lire, on lis
    				while((lec = fin.read(rec)) >= 0)
    				{
    					//on écrit dans le second fichier
    					fout.write(rec);
    					//on affiche ce que l'on voit
    					for(byte bit : rec)
    					{
    						System.out.println("\t" + bit + "(" + (char) bit + ")");
    						System.out.println("");	
    					}
    					//on réinitialise le tableau
    					rec = new byte[8];
    				}
    				System.out.println("Copie terminée !");
        		} 
        		catch (FileNotFoundException e)
        		{
        			//exception si FileInputStream ne trouve pas de fichier
        			e.printStackTrace();	
        		} 
        		catch (IOException e)
        		{
        			//exception lorsque erreur de lecture ou d'écriture
        			e.printStackTrace();	
        		} 
        		finally
        		{
        			//on ferme tous les flux
        			//et on sécurise
        			try
        			{
        				if (fin != null)
        					fin.close();	
        			} 
        			catch (IOException e)
        			{
        				e.printStackTrace();	
        			}
        			try
        			{
        				if (fout != null)
        					fout.close();	
        			} 
        			catch (IOException e)
        			{
        				e.printStackTrace();
        			}
        		}
        		break;
        	case "0":
        	//on quitte le programme
        		System.out.println("...Au Revoir...");
        		break;
        	default:
        	//sécurité avant fermeture du programme
        		System.out.println("Le numéro entré n'est pas valide ");
        		return;
        }
    }
}

//liens pouvant être utile si besoin
//http://java.developpez.com/faq/java/?page=langage_fichiers