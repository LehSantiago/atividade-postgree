package br.edu.mackenzie.gerenciadornomes;

import java.util.List;

public interface GerenciadorNomes {

    boolean adicionar(String nome);

    List<String> obter();

    boolean atualizar(String nomeAntigo, String novoNome);

    boolean remover(String nome);
}
