package br.edu.mackenzie.gerenciadornomes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class GerenciadorNomesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GerenciadorNomesApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Map<String, String> env = carregarEnv();

        String url = env.get("DB_URL");
        String usuario = env.get("DB_USUARIO");
        String senha = env.get("DB_SENHA");

        try (Connection connection =
                DriverManager.getConnection(url, usuario, senha)) {

            codigoAnterior(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> carregarEnv() {
        Map<String, String> valores = new HashMap<>();

        try {
            for (String linha : Files.readAllLines(Path.of(".env"))) {
                if (linha.isBlank() || linha.startsWith("#")) {
                    continue;
                }
                String[] partes = linha.split("=", 2);
                valores.put(partes[0].trim(), partes[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return valores;
    }

    private void codigoAnterior(Connection connection) {
        GerenciadorNomes gerenciador = new GerenciadorNomesBD(connection);

        gerenciador.adicionar("Ana");
        gerenciador.adicionar("Bruno");
        gerenciador.adicionar("Carlos");

        System.out.println("Nomes cadastrados:");
        for (String nome : gerenciador.obter()) {
            System.out.println("- " + nome);
        }

        System.out.print("\nAlterando Bruno para Beatriz... ");
        System.out.println(gerenciador.atualizar("Bruno", "Beatriz"));

        System.out.print("\nAlterando Bruno outra vez para Beatriz... ");
        System.out.println(gerenciador.atualizar("Bruno", "Beatriz"));

        System.out.print("Removendo Carlos... ");
        System.out.println(gerenciador.remover("Carlos"));

        System.out.print("Removendo Italo que não existe... ");
        System.out.println(gerenciador.remover("Italo"));

        System.out.println("\nNomes finais:");
        for (String nome : gerenciador.obter()) {
            System.out.println("- " + nome);
        }
    }
}
