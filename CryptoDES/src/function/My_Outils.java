package function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface My_Outils {

	public static int[] bytetobitarray(byte[] b)
	{
		int j = 0;
		int[] m = new int[8 * b.length];
		for(int i = 0; i < b.length; i++){
			for(int k = 7; k >= 0; k--){
				m[j] = (b[i] & (1 << k)) != 0 ? 1 : 0;
				j++;
			}
		}
		return m;
	}
	
	public static List<Integer> integerArrayToList(int[] array) {
		List<Integer> res = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			res.add(array[i]);
		}
		return res;
	}
	
	public static int[] integerListToArray(List<Integer> list) {
		int[] res = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	
	public static int[] rotateLeftIntArray(int[] array) {
		int tmp = array[0];
		for (int i = 0; i < array.length - 1; i++) {
			array[i] = array[i + 1];
		}
		array[array.length - 1] = tmp;
		return array;
	}
	
	public static int[] permutation(int[] array, int[] tab)
	{
		int[] res = new int[tab.length];
		for(int i = 0; i < tab.length; i++){
			res[i] = array[tab[i] - 1];
		}
		return res;
	}
	
	public static int[][] splitmessage(int[] ok){
		int[][] messagecrypt;
		int tour = ok.length / 64;
		int reste = ok.length % 64;
		
		if(tour == 0 && reste != 0){
			tour = 1;
			messagecrypt = new int[tour][64];
		}
		else{
			tour++;
			messagecrypt = new int[tour][64];
		}
				
		int l = 0;
		if( tour > 1){
			
			for(int i = 0; i < (tour - 1) ; i++){
				for(int j = 0; j < 64; j++){
					messagecrypt[i][j] = ok[l];
					l++;
				}
			}
		}
		int k = 0;
		if(reste != 0){
			for(int i = 0; i < reste; i++){
					messagecrypt[tour-1][k] = ok[l];
					l++;
					k++;
			}
			for(int i = 0; i < (64 - reste); i++){
				messagecrypt[tour-1][k] = 0;
				k++;
			}
		}
		return messagecrypt;
	}
	
	public static int[] bitMatriceToBitArray(int[][] matrice) {
		int fullSize = matrice.length * matrice[0].length;
		int[] res = new int[fullSize];
		int cpt = 0;
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[i].length; j++) {
				res[cpt++] = matrice[i][j];
			}
		}
		return res;
	}
	
	public static int[] xorBitArrays(int[] bloc1, int[] bloc2) {
		int[] res = new int[bloc1.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = bloc1[i] ^ bloc2[i];
		}
		return res;
	}
	
	public static int[][] split8x6BitMessage(int[] array) {
		int[][] res = new int[8][6];
		int cpt = 0;
		for (int i = 0; i < res.length; i++) {
			res[i] = Arrays.copyOfRange(array, cpt, cpt + 6);
			cpt += 6;
		}
		return res;
	}
	
	public static String bitMatriceToString(int[][] matrice) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[i].length; j++) {
				sb.append(matrice[i][j]);
			}
		}
		return sb.toString();
	}
	
	public static String hexToASCII(String hexValue) {
		StringBuilder output = new StringBuilder("");
		for (int i = 0; i < hexValue.length(); i += 2) {
			String str = hexValue.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}
	
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
