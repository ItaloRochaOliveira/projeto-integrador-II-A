import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import models.APIConection.Request;
import models.APIConection.Response;
import server.ServerCRUD;

public class App {
    private static AtomicInteger idCounter = new AtomicInteger(1);
    private static List<Usuario> usuarios = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerCRUD serverCRUD = new ServerCRUD();

        serverCRUD.get("/usuarios", (Request req, Response res) -> {
            System.out.print(req.body);
           try {
                String[] partes = req.path.split("/");
                if (partes.length == 3 ) { // /usuarios/{id}

                    if(partes[2].isEmpty()) throw new Exception("id não informado");

                    int id = Integer.parseInt(partes[2]);
                    res.send(200, buscarUsuario(id));
                } else { 
                    res.send(200, listarUsuarios());
                } 
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"Requisição inválida\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        serverCRUD.post("/usuarios", (Request req, Response res) -> {
            System.out.print(req.body);
            try {
                String response = criarUsuario(req.body);

                if(response != null){
                    res.send(201, response);
                } else {

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

    private static String criarUsuario(String body) {
        String nome = extrairCampoJson(body, "nome");
        Usuario u = new Usuario(idCounter.getAndIncrement(), nome);
        usuarios.add(u);
        return u.toJson();
    }

    private static String extrairCampoJson(String json, String campo) {
        // Simples extração de campo do tipo: {"nome":"Joao"}
        int i = json.indexOf(campo);
        if (i == -1) return "";
        int start = json.indexOf("\"", i + campo.length() + 2) + 1;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
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
