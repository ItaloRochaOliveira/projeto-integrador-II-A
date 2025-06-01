import java.util.ArrayList;
import java.util.List;

import models.APIConection.Request;
import models.APIConection.Response;
import server.ServerCRUD;

public class App {
    private static List<Usuario> usuarios = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerCRUD serverCRUD = new ServerCRUD();

        serverCRUD.get("/usuarios", (Request req, Response res) -> {
           try {
                String[] partes = req.path.split("/");
                if (partes.length == 3 && !partes[2].isEmpty()) { // /usuarios/{id}
                    int id = Integer.parseInt(partes[2]);
                    res.send("HTTP/1.1 200 OK\r\n", buscarUsuario(id));
                } else if (partes.length == 3 && partes[2].isEmpty()) { // /usuarios/{id}
                    int id = Integer.parseInt(partes[2]);
                    res.send("HTTP/1.1 200 OK\r\n", buscarUsuario(id));
                } else {
                    res.send("HTTP/1.1 400 Bad Request\r\n", "{\"erro\": \"ID não informado\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send("HTTP/1.1 400 Bad Request\r\n", "{\"erro\": \"Requisição inválida\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        serverCRUD.get("/usuarios/id", (Request req, Response res) -> {
            try {
                String[] partes = req.path.split("/");
                System.out.println(partes);
                if (partes.length == 3) { // /usuarios/{id}
                    int id = Integer.parseInt(partes[2]);
                    res.send("HTTP/1.1 200 OK\r\n", buscarUsuario(id));
                } else {
                    res.send("HTTP/1.1 400 Bad Request\r\n", "{\"erro\": \"ID não informado\"}");
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
                // Optionally, send an error response
                // res.send("HTTP/1.1 500 Internal Server Error\r\n", "{\"erro\": \"Erro interno do servidor\"}");
            }
        });

        serverCRUD.start();
    }

      // --- Métodos auxiliares e modelo ---
    private static String listarUsuarios() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < usuarios.size(); i++) {
            sb.append(usuarios.get(i).toJson());
            if (i < usuarios.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private static String buscarUsuario(int id) {
        return usuarios.stream()
                .filter(u -> u.id == id)
                .findFirst()
                .map(Usuario::toJson)
                .orElse("{\"erro\": \"Usuário não encontrado\"}");
    }

    static class Usuario {
        int id;
        String nome;

        Usuario(int id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        String toJson() {
            return String.format("{\"id\":%d,\"nome\":\"%s\"}", id, nome);
        }
    }
}
