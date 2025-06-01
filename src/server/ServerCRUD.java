package server;

import java.io.*;
import java.net.*;
import java.util.*;
// import java.util.concurrent.atomic.AtomicInteger;

import models.APIConection.Handler;
import models.APIConection.Request;
import models.APIConection.Response;

public class ServerCRUD {
    // private static AtomicInteger idCounter = new AtomicInteger(1);
    private Map<String, Handler> rotasGet = new HashMap<>();

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Servidor rodando na porta 8080...");
            while (true) {
                Socket socket = serverSocket.accept();
                tratarRequisicao(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get(String rota, Handler handler) {
        rotasGet.put(rota, handler);
    }

    private void tratarRequisicao(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            String[] parts = requestLine.split(" ");
            String metodo = parts[0];
            String caminho = parts[1];

            if (metodo.equals("GET")) {
                for (String rota : rotasGet.keySet()) {
                    if (caminho.equals(rota) || caminho.matches(rota + "/\\d+")) {
                        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                            Request req = new Request();
                            req.path = caminho;
                            req.method = metodo;
                            Response res = new Response(out);
                            rotasGet.get(rota).handle(req, res);
                        }
                        return;
                    }
                }

                // Rota não encontrada
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                    String resposta = "{\"erro\": \"Rota não encontrada\"}";
                    out.write("HTTP/1.1 404 Not Found\r\n");
                    out.write("Content-Type: application/json\r\n");
                    out.write("Content-Length: " + resposta.length() + "\r\n");
                    out.write("\r\n");
                    out.write(resposta);
                    out.flush();
                } 

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

  
}
