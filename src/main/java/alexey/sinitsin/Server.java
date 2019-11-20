package alexey.sinitsin;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) throws IOException {
        int port = 9999;

        ServerSocket ss = new ServerSocket(port);
        System.out.println("Waiting for a client...");
        while (true) {
            try(Socket socket = ss.accept()) {
                System.out.println("Got a client!");



                try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while (!(line = br.readLine()).equals("")) {
                        sb.append(line);
                    }
                    if (sb.toString().contains("GET")) {
                        File file = new File(".");
                        StringBuilder body = new StringBuilder();

                        if (file.isDirectory()) {
                            for (File item : file.listFiles()) {
                                if (item.isDirectory()) {
                                    System.out.println(item.getName());
                                    body.append(" <li>").append(item.getName()).append(" </li>\n");

                                }
                            }
                        }

                        String bodySmall = new String(body.toString());
                        body = new StringBuilder();
                        body.append("<html><body><h1>Hello ").append("<ul>\n").append(bodySmall).append("</ul></h1></body></html>");
                        StringBuilder header = new StringBuilder();
                          header.append("HTTP/1.1 200 OK").append("\r\n").append("Server: YarServer/2009-09-09\r\n")
                                  .append("Content-Type: text/html;charset=utf-8\r\n").append("Content-Length: ")
                                  .append(body.length()).append("\r\n").append("Connection: close\r\n\r\n");

                        bw.write(header.append(body.toString()).toString());
                        bw.flush();
                    } else {
                        StringBuilder header = new StringBuilder();
                        header.append("HTTP/1.1 404 Not Found").append("\r\n").append("Server: YarServer/2009-09-09\r\n")
                                .append("Content-Type: text/html;charset=utf-8\r\n").append("Content-Length: ")
                                .append(0).append("\r\n").append("Connection: close\r\n\r\n");

                        bw.write(header.toString());
                        bw.flush();

                    }

                }
            }

        }

    }

}

