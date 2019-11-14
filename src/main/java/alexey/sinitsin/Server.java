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
            String body=new String();

            if (file.isDirectory()) {
                for (File item : file.listFiles()) {
                    if (item.isDirectory()) {
                        System.out.println(item.getName());
                        body = body+" <li>"+item.getName()+" </li>\n";

                    }
                }
            }
            body="<html><body><h1>Hello " +"<ul>\n"+body+"</ul></h1></body></html>";
            String header = "HTTP/1.1 200 OK"+"\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html;charset=utf-8\r\n" +
                    "Content-Length: " + body.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            bw.write(header+body);
            bw.flush();
        }else{
            String header = "HTTP/1.1 404 Not Found" + "\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html;charset=utf-8\r\n" +
                    "Content-Length: " + 0 + "\r\n" +
                    "Connection: close\r\n\r\n";
            bw.write(header);
            bw.flush();

        }

    }



}

