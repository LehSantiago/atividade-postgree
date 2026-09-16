package br.edu.mackenzie.gerenciadornomes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class GerenciadorNomesBD implements GerenciadorNomes {

    private final Connection connection;

    public GerenciadorNomesBD(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean adicionar(String nome) {
        String sql = "INSERT INTO nomes (nome) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            int quantidade = statement.executeUpdate();
            return quantidade > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> obter() {
        String sql = "SELECT nome FROM nomes ORDER BY nome";
        List<String> nomes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                nomes.add(resultSet.getString("nome"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nomes;
    }

    @Override
    public boolean atualizar(String nomeAntigo, String novoNome) {
        String sql = "UPDATE nomes SET nome = ? WHERE nome = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoNome);
            statement.setString(2, nomeAntigo);
            int quantidade = statement.executeUpdate();
            return quantidade > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remover(String nome) {
        String sql = "DELETE FROM nomes WHERE nome = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            int quantidade = statement.executeUpdate();
            return quantidade > 0;
        } catch (Exception e) {
            return false;
        }
    }
}