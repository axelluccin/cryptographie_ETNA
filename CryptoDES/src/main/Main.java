package main;

import function.DES;
public class Main{
	public static void main(String[] args) {
		String msg = "Inter haec Orfitus praefecti potestate regebat urbem aeternam ultra modum delatae dignitatis sese efferens insolenter, vir quidem prudens et forensium negotiorum oppido gnarus, sed splendore liberalium doctrinarum minus quam nobilem decuerat institutus, quo administrante seditiones sunt concitatae graves ob inopiam vini: huius avidis usibus vulgus intentum ad motus asperos excitatur et crebros.";
		String key = "pourvoirsicelamarhce";
		
		
		DES message_a_crypter = new DES(key);
		String l = message_a_crypter.Crypt(msg);
		System.out.println(l);
		String m = message_a_crypter.Decrypt(l);
		System.out.println(m);
	}

}
