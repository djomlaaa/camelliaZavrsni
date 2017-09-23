package camellia.brankovic.mladen;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        byte[] plain = {(byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67,(byte) 0x89, (byte)0xab, (byte)0xcd, (byte)0xef, (byte)0xfe,(byte) 0xdc, (byte) 0xba,(byte) 0x98,(byte) 0x76,(byte) 0x54,(byte) 0x32,(byte) 0x10};
		byte[] key = {(byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67,(byte) 0x89, (byte)0xab, (byte)0xcd, (byte)0xef, (byte)0xfe,(byte) 0xdc, (byte) 0xba,(byte) 0x98,(byte) 0x76,(byte) 0x54,(byte) 0x32,(byte) 0x10};

		byte[] cipher = new byte[16];

		for (int i = 0; i<16 ; i++){
			System.out.print(plain[i]);
			System.out.print(" ");
		}

		System.out.println("");
		CamelliaAlgorithm a = new CamelliaAlgorithm();
		a.init(true, key);
		a.processBlock(plain, 0, cipher, 0);
		for (int i = 0; i<16 ; i++){
			System.out.print(cipher[i]);
			System.out.print(" ");
		}
		System.out.println("");

		CamelliaAlgorithm da = new CamelliaAlgorithm();
		a.init(false,key);
		byte[] deciphered = new byte[16];
		a.processBlock(cipher,0,deciphered,0);

		for (int i = 0; i<16 ; i++){
			System.out.print(deciphered[i]);
			System.out.print(" ");
		}

    }
}
