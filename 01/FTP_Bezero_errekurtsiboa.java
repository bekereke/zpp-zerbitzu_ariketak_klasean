package unieibar;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTP_Bezero_errekurtsiboa {

	public static void main(String[] args) throws IOException {
		
		String ftp_zerbitzaria = "localhost";
		String user = "erabiltzailea";
		String password = "pasahitza";
		
		FTPClient ftp_bezeroa = new FTPClient();
		
		try
		{
			System.out.println("FTP zerbitzarira atzitzera goaz: " + ftp_zerbitzaria);
			ftp_bezeroa.connect(ftp_zerbitzaria);
			// Erabiltzailea eta pasahitza sartu, konektatu ondoren
			boolean login = ftp_bezeroa.login(user, password);
			if (login)
			{
				System.out.println("Ongi logeatu gara!");
				ftp_bezeroa.enterLocalPassiveMode();
			}
			else
			{
				System.out.println("Logina ez da ongi burutu. Deskonektatzen...");
				ftp_bezeroa.disconnect();
				System.exit(1);
			}
			
			// Erro karpetako edukia bistaratu
			System.out.println("\nKarpetako edukia errekurtsiboki erakusten:");
			karpetakoEdukiaBistaratu(ftp_bezeroa, 0, "/");
			System.out.println("\n");
			
			
			// Logout
			boolean logout = ftp_bezeroa.logout();
			if (logout)
			{
				System.out.println("FTP zerbitzaritik logout onartua...");
			}
			else
			{
				System.out.println("FTP zerbitzaritik logout egiterakoan ERROREA");
			}

			// Zerbitzaritik deskonektatu
			ftp_bezeroa.disconnect();
			System.out.println("Konexioa itxi da.");
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
			
	}
	
	
	private static void karpetakoEdukiaBistaratu(FTPClient ftp_bezeroa, int maila, String pathOsoa) {
		try {
			// Karpetako fitxategi eta azpikarpeta zerrenda jaso
			FTPFile[] fitx_dir = ftp_bezeroa.listFiles();
			String motak[] = {"[F]", "[K]", "[L]"};
			
			//  Fitxategi eta azpikarpeta guztiak zerrendatu
			for (int i=0; i<fitx_dir.length; i++)
			{
				// Gauden mailaren arabera, tabulazioak marraztu pantailan
				for (int t=0; t<maila; t++) {
					System.out.print("\t");
				}
				// Izena eta mota pantailan marraztu
				System.out.println(fitx_dir[i].getName() + " " + motak[fitx_dir[i].getType()]);
				
				// Karpeta baldin bada (==1) errekurtsiboki deitu
				if (fitx_dir[i].getType() == 1) {
					// Azpikarpetaren izena jaso
					String azpikarpeta = fitx_dir[i].getName();
					// FTP Bezeroa azpikarpetara mugitu
					if(ftp_bezeroa.changeWorkingDirectory(azpikarpeta)) {
						String pathOsoBerria = pathOsoa + "/" + azpikarpeta;
						// Dei errekurtsiboa egin
						karpetakoEdukiaBistaratu(ftp_bezeroa, maila+1, pathOsoBerria);
						// Bueltan goiko karpetara itzuli
						ftp_bezeroa.changeWorkingDirectory("..");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}