package alexey.sinitsin;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) throws IOException {
        int port = 9999;

        ServerSocket ss = new ServerSocket(port);
        System.out.println("Waiting for a client...");
        Socket socket = ss.accept();
        System.out.println("Got a client!");

        InputStream is = socket.getInputStream();
        OutputStream ous = socket.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ous));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while (!(line = br.readLine()).equals("")) {
            sb.append(line);
        }
        if (sb.toString().contains("GET")) {
            File file = new File("C:\\Users\\User\\IdeaProjects\\hw6");
            if (file.isDirectory()) {
                for (File item : file.listFiles()) {
                    if (item.isDirectory()) {
                        System.out.println(item.getName());
                    }
                }
            }
        }else{
            System.out.println("HTTP/1.1 404 Not Found");
            bw.write(line);
            bw.flush();
        }

    }

}

