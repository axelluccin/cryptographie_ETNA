package function;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DES implements My_Outils, My_Tabs{
	
	private List<int[]> allKeys  ;
	private String keycrypt;
	
	public DES(String key){
		this.keycrypt = key;
		this.allKeys = calculatekeys(key);
	}
	
	private int[] keySchedule(String key, int turn) {
		// On r�cup�re dans la liste la cl� correspondant � la ronde en cours
		return allKeys.get(turn);
	}
	
	private List<int[]> calculatekeys(String key) {
		byte[] keyb = key.getBytes();
		int[] keycrypt = My_Outils.bytetobitarray(keyb);
		
		keycrypt = My_Outils.permutation(keycrypt, CP1);
		int[] gauchek = Arrays.copyOfRange(keycrypt, 0, 28);
		int[] droitek = Arrays.copyOfRange(keycrypt, 28, 56);
		
		List<List<Integer>> allGKeys = new ArrayList<List<Integer>>();
		List<List<Integer>> allDKeys = new ArrayList<List<Integer>>();

		// On ajoute les cl�s gauche et droite initiales
		allGKeys.add(My_Outils.integerArrayToList(gauchek));
		allDKeys.add(My_Outils.integerArrayToList(droitek));
		for (int i = 1; i < 16; i++) {
			int[] tmpG;
			int[] tmpD;
			// On initialise les nouvelles cl�s avec les pr�c�dentes
			tmpG = My_Outils.integerListToArray(allGKeys.get(i - 1));
			tmpD = My_Outils.integerListToArray(allDKeys.get(i - 1));
			for (int j = 0; j < MOVE_TURN[i]; j++) {
				// On d�cale les bits vers la gauche
				tmpG = My_Outils.rotateLeftIntArray(tmpG);
				tmpD = My_Outils.rotateLeftIntArray(tmpD);
			}
			// On ajoute les nouvelles cl�s � leur liste respective
			allGKeys.add(My_Outils.integerArrayToList(tmpG));
			allDKeys.add(My_Outils.integerArrayToList(tmpD));
		}
		// On cr�� la liste des 16 sous-cl�s d�finitives.
		ArrayList<int[]> keys = new ArrayList<int[]>();
		// Pour chaque ensemble des sous-cl�s gauche et droite de 32 bits
		for (int i = 0; i < allGKeys.size(); i++) {
			// On cr�� une sous-cl� de 56 bits
			int[] tmpKey = new int[56];

			// On y ins�re la sous-cl� de m�me niveau gauche
			for (int j = 0; j < allGKeys.get(i).size(); j++) {
				tmpKey[j] = allGKeys.get(i).get(j);
			}
			// Puis on y ins�re la sous-cl� de m�me niveau droite
			for (int j = 0; j < allDKeys.get(i).size(); j++) {
				tmpKey[j + 28] = allDKeys.get(i).get(j);
			}
			// On permute la nouvelle sous-cl� avec la matrice CP2
			int[] finalSubKey = My_Outils.permutation(tmpKey, CP2);

			// On ajoute la sous-cl� finale � la liste
			keys.add(finalSubKey);
		}
		return keys;
	}
	
	public String Crypt(String msg) {
		byte[] b = msg.getBytes();
		
		int[] msgcrypt = My_Outils.bytetobitarray(b);
		
		int[][] mymsgsplit = My_Outils.splitmessage(msgcrypt);
		
		for (int i = 0; i < mymsgsplit.length; i++) {
			
			int[] my_bloc = My_Outils.permutation(mymsgsplit[i], IP1);
			
			int[] gaucheM = Arrays.copyOfRange(my_bloc, 0, 32);
			int[] droiteM = Arrays.copyOfRange(my_bloc, 32, 64);
			
			int[] afterIter = rondesEncrypt(this.keycrypt, gaucheM, droiteM);

			int[] finalBloc = My_Outils.permutation(afterIter, My_Tabs.IP_INVERSE);

			mymsgsplit[i] = finalBloc;
		}
		
		BigInteger tmpINT = new BigInteger(My_Outils.bitMatriceToString(mymsgsplit), 2);
		String mymsgcrypt = tmpINT.toString(16);
		//String mymsgcrypt = My_Outils.bitMatriceToString(mymsgsplit);
		return mymsgcrypt;
		
	}
	
	public String Decrypt(String msg) {
		// R�cup�ration des octets du message
		byte[] b = My_Outils.hexStringToByteArray(msg);
		
		int[] msgcrypt = My_Outils.bytetobitarray(b);

		// Transformation en blocs de 64 bits dans un tableau de tableau
		int[][] messageBitMatrice = My_Outils.splitmessage(msgcrypt);

		// Pour chaque bloc de 64 bits
		for (int i = 0; i < messageBitMatrice.length; i++) {
			// Effectuer la permutation initiale
			int[] blocIP = My_Outils.permutation(messageBitMatrice[i], My_Tabs.IP1);
			// On stocke la partie gauche dans un sous tableau de 32 bits.
			int[] gaucheInitiale = Arrays.copyOfRange(blocIP, 0, 32);
			// Ainsi que la partie droite dans un autre sous tableau de 32 bits
			int[] droiteInitiale = Arrays.copyOfRange(blocIP, 32, 64);
			// On effectue les rondes de DEchiffrement et on r�cup�re le nouveau
			// bloc de 64 bits.
			int[] afterIter = rondesDecrypt(this.keycrypt, gaucheInitiale, droiteInitiale);
			// On permute une derni�re fois le bloc de 64 bits
			int[] finalBloc = My_Outils.permutation(afterIter, My_Tabs.IP_INVERSE);
			// On remplace l'ancien bloc de 64 bits non chiffr� par le nouveau,
			// chiffr�.
			messageBitMatrice[i] = finalBloc;
		}

		// On transforme de nouveau tout le message en phrase hexad�cimale
		BigInteger tmpInt = new BigInteger(My_Outils.bitMatriceToString(messageBitMatrice), 2);
		String res = tmpInt.toString(16);
		
		return My_Outils.hexToASCII(res);
	}
	
	private int[] rondesEncrypt(final String key, int[] gauche, int[] droite) {
		// On lance les 16 rondes
		for (int ronde = 0; ronde < 16; ronde++) {
			// On pr�pare le nouveau bloc de gauche
			int[] tmpG = droite;
			// On r�cup�re dans un tableau le r�sultat de la fonction qui
			// calcule le bloc de droite
			int[] fres = expansionAndSubstitution(droite, keySchedule(key, ronde));
			// On pr�pare le nouveau bloc de droite en faisant un XOR entre la
			// partie gauche et le tableau obtenu
			int[] tmpD = My_Outils.xorBitArrays(gauche, fres);
			// On modifie les parties gauches et droites
			gauche = tmpG;
			droite = tmpD;
		}
		// Apr�s 16 it�rations, on recolle les parties gauches et droites
		// obtenues
		int[][] recolle = { droite, gauche };
		// Et on retourne le tableau des bits de ce r�sultat
		return My_Outils.bitMatriceToBitArray(recolle);
	}
	
	private int[] rondesDecrypt(String key, int[] gauche, int[] droite) {
		// On lance les 16 rondes dans l'ordre inverse
		for (int ronde = 15; ronde >= 0; ronde--) {
			// On pr�pare le nouveau bloc de gauche
			int[] tmpG = droite;
			// On r�cup�re dans un tableau le r�sultat de la fonction qui
			// calcule le bloc de droite
			int[] fres = expansionAndSubstitution(droite, keySchedule(key, ronde));
			// On pr�pare le nouveau bloc de droite en faisant un XOR entre la
			// partie gauche et le tableau obtenu
			int[] tmpD = My_Outils.xorBitArrays(gauche, fres);
			// On modifie les parties gauches et droites
			gauche = tmpG;
			droite = tmpD;
		}
		// Apr�s 16 it�rations, on recolle les parties gauches et droites
		// obtenues
		int[][] recolle = { droite, gauche };
		// Et on retourne le tableau des bits de ce r�sultat
		return My_Outils.bitMatriceToBitArray(recolle);
	}
	
	private int[] expansionAndSubstitution(final int[] bloc, final int[] key) {
		// On �tend le bloc de 32 bits en permutant avec la matrice E
		int[] expansed = My_Outils.permutation(bloc, E);
		// On effectue un XOR (ou stricte logique) entre le bloc �tendu et la
		// cl�
		int[] xored = My_Outils.xorBitArrays(expansed, key);
		// On d�coupe le bloc obtenu en 8 blocs de 6 bits
		int[][] matrice = My_Outils.split8x6BitMessage(xored);
		// Pour chaque bloc
		for (int j = 0; j < matrice.length; j++) {
			// On effectue une substitution sur le bloc
			matrice[j] = substitution(matrice[j], j);
		}
		// On applati la matrice des blocs substitu�s obtenue
		int[] reduced = My_Outils.bitMatriceToBitArray(matrice);
		// On permute une derni�re fois le nouveau bloc avec la matrice P
		int[] res = My_Outils.permutation(reduced, P);
		// On renvoie le nouveau bloc
		return res;
	}
	
	private int[] substitution(final int[] bloc, final int sboxnum) {
		// On r�cup�re la table de substitution en cours
		int[][] sbox = My_Tabs.sboxes[sboxnum];
		/*
		 * On trouve la ligne dans la sbox � partir des bits 0 et 5 du bloc, qui
		 * une fois coll�s nous donnent un chiffre binaire compris entre 0 et 3
		 * inclus.
		 * 
		 * Exemple : le bloc est 110010. Les bits � r�cup�rer sont donc
		 * 
		 * 1....0 = 10 = 2 en binaire. La ligne est donc 2.
		 */
		int line = Integer.parseInt("" + bloc[0] + bloc[5], 2);
		/*
		 * On trouve la colonne dans la sbox � partir des bits 1 � 4 du bloc,
		 * qui une fois coll�s nous donnent un chiffre binaire compris entre 0
		 * et 15 inclus.
		 * 
		 * Exemple : le bloc est 100010. Les bits � r�cup�rer sont donc
		 * 
		 * .1001. = 1001 = 9 en binaire. La colonne est donc 9.
		 */
		int column = Integer.parseInt("" + bloc[1] + bloc[2] + bloc[3] + bloc[4], 2);

		// On r�cup�re les bits correspondants � la valeur contenue par la sbox
		// � la ligne et colonne calcul�es
		String out = Integer.toBinaryString(sbox[line][column]);

		// On compl�te le mot avec des z�ros pour palier � la conversion en
		// Integer
		while (out.length() < 4) {
			out = "0" + out;
		}
		// On re coupe la chaine en un tableau de bits sous forme de String
		String[] binstring = out.split("");
		// On pr�pare le tableau r�sultat
		int[] res = new int[4];
		// On remplit le tableau r�sultat avec les Integers correspondant aux
		// String du tableau pr�c�dent
		for (int i = 0; i < binstring.length; i++) {
			res[i] = Integer.parseInt(binstring[i]);
		}

		// On retourne le bloc de 6 bits substitu� en bloc de 4 bits.
		return res;
	}
}
