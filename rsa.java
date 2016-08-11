import java.util.Scanner;
import java.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class rsa{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int choix;
		System.out.println("Tapez 1 si vous souhaitez chiffrer: ");
		System.out.println("Tapez 2 si vous souhaitez déchiffrer: ");
		System.out.println("Tapez 0 si vous souhaitez quitter");
		choix = sc.nextInt();
		//System.out.println("Vous avez saisi: "+choix);
		saisi(choix);
	}
	
	public static void saisi(int choix)
	{
		Scanner sc = new Scanner(System.in);
		int m;
		switch (choix)
        {
        	case 1:
        		System.out.println("vous chiffrez");
        		int choix2;
        		System.out.println("Tapez 1 si vous souhaitez entrer vos clefs de chiffrement: ");
				System.out.println("Tapez 2 si vous souhaitez générer aléatoirement vos clefs: ");
				System.out.println("Tapez 0 si vous souhaitez quitter");
        		choix2 = sc.nextInt();
        		keysEncrypt(choix2);
        		break;
        	case 2:
        		System.out.println("vous déchiffrez");
        		System.out.println("Tapez le premier nombre de la clef de déchiffrement: ");
        		int n = sc.nextInt();
        		System.out.println("Vous avez saisi: "+n);
        		System.out.println("Tapez le second nombre de la clef de déchiffrement: ");
        		int d = sc.nextInt();
        		System.out.println("Vous avez saisi: "+d);
        		System.out.println("Tapez le chiffre à déchiffrer");
        		m = sc.nextInt();
        		System.out.println("Vous avez saisi: "+m);
        		int decrypt;
        		decrypt = RSADecrypt(n, d, m);
        		System.out.println("Déchiffré cela donne: "+decrypt);
        		break;
        	case 0:
        	//on quitte le programme
        		System.out.println("...Au Revoir...");
        		break;
        	default:
        	//sécurité avant fermeture du programme
        		System.out.println("Le numéro entré n'est pas valide ");
        		return;
        }
	}
	
	public static void keysEncrypt(int choix2)
	{
		Scanner sc = new Scanner(System.in);
		int encrypt;
		switch (choix2)
        {
        	case 1:
        		System.out.println("Tapez le premier nombre de la clef: ");
        		int n = sc.nextInt();
        		System.out.println("Vous avez saisi: "+n);
				System.out.println("Tapez le second nombre de la clef: ");
				int e = sc.nextInt();
				System.out.println("Vous avez saisi: "+e);
				System.out.println("Veuillez entrer un chiffre: ");
				int m = sc.nextInt();
				System.out.println("Vous avez saisi: "+m);
        		encrypt = RSAencrypt(n, e, m);
				System.out.println("encrypt est égal à: "+encrypt);
        		break;
        	case 2:
        		System.out.println("vous chiffrez aléatoirement");
        		int pa = randomInt();
        		System.out.println("p = " + pa);
        		int qa = randomInt();
        		System.out.println("q = " + qa);
        		int na = pa * qa;
        		System.out.println("n = " + na);
        		int pb = pa-1;
        		int qb = qa-1;
        		int y = pb*qb;
        		int z;
        		int ea;
        		int d;
        		System.out.println("====================================================");
        		do {
        			do {
        				ea = randomIntE(pa, qa);
        				System.out.println("e = " + ea);
        				z = pgcd(ea, y);
        				System.out.println("le resultat du PGCD entre E et (p-1)*(q-1) est " + z);
        				System.out.println("====================================================");
        			} while (z != 1);
        			d = determineD(pb, qb, ea);
        		} while (d < 2);
 //       		int d = determineD(pb, qb, ea);
 //       		int ea = randomINt(pa, qa);
 				System.out.println("fin de boucle");
        		System.out.println("Veuillez entrer le chiffre à crypter: ");
				m = sc.nextInt();
				System.out.println("Vous avez saisi: "+m);
        		encrypt = RSAencrypt(na, ea, m);
				System.out.println("le chiffre crypter est egal à: "+encrypt);
				System.out.println("Votre clef de déchiffrement est: " + n + ", " + d);
        		break;
        	case 0:
        	//on quitte le programme
        		System.out.println("...Au Revoir...");
        		break;
        	default:
        	//sécurité avant fermeture du programme
        		System.out.println("Le numéro entré n'est pas valide ");
        		return;
        }	
	}
	public static int determineD(int pb, int qb, int ea){
		int D;
		//on calcule d grace à (e*d)-1 soit un multiple de (p-1)*(q-1)
		D = (int)((pb * qb) - 1) / ea;
		System.out.println("d est égal à: " + D);
		return D;
	}
	
	public static int RSAencrypt(int n, int e, int m)
	{
		System.out.println("n: " + n);
		System.out.println("e: " + e);
		int C = 0;
		//utilisation de la méthode math.pow pour la puissance
		C = (int) (Math.pow(m,e));
		C = (int) C % n;
		return C;
	}
	
	public static int RSAdecrypt(int p, int q, int d, int m)
	{
		int C = 0;
		//utilisation de la méthode math.pow pour la puissance
		C = (int) (Math.pow(m,d));
		C = (int) C % (p*q);
		return C;
	}
	
	public static int RSADecrypt(int n, int d, int m)
	{
		int C = 0;
		//utilisation de la méthode math.pow pour la puissance
		C = (int) (Math.pow(m,d));
		C = (int) C % (n);
		return C;
	}
	
	public static int randomInt()
	{
		int random = 0;
		int max = 128;
		boolean isValid = false;
		while (isValid == false)
		{
			//valeur max = 999999999
			random = (int)(Math.random()*(max));
			random = random + 1;
			isValid = isFirst(random);
		}
		return random;	
	}
	
	public static int randomIntE(int p, int q)
	{
		int random = 0;
		int a = p - 1;
		int b = q - 1;
		int max = p * q;
		boolean isValid = false;

		while (isValid == false)
		{
			//valeur max = 999999999
			random = (int)(Math.random()*(max));
			random = random + 1;
			isValid = isFirst(random);
		}
		return random;
	}
	
	public static boolean isFirst (int random){
		boolean first = true;
		if (random < 0)
			first = false;
		else if (random == 1)
			first = false;
		else if (random != 0 && random != 1)
		{
			for (int i = 2; i <= random/2; i++)
			{
				if (random != i && random % i == 0)
				{
					first = false;
					break;
				}	
			}
		}
		return first;
	}
	
	public static int pgcd(int a, int b) {
      while (a != b) 
      {
         if (a < b)
            b = b - a;
         else
            a = a - b;
      }
      return a;
   }
}