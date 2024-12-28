package paagbi;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Main {
    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();
        //String password = "pasahitza";
        //String user = "erabiltzailea";
        String password = "";
        String user = "anonymous";
        try {
            ftpClient.connect("ftp.freebsd.org", 21);
            //ftpClient.connect("localhost", 21);
            ftpClient.login(user, password);
            System.out.println("Connected to FTP server.");
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                System.out.println(file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}