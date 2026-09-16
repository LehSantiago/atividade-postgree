package br.edu.mackenzie.gerenciadornomes;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorNomesLista implements GerenciadorNomes {

    private final List<String> nomes = new ArrayList<>();

    @Override
    public boolean adicionar(String nome) {
        if (nome == null || nome.isBlank() || nomes.contains(nome)) {
            return false;
        }

        nomes.add(nome);
        return true;
    }

    @Override
    public List<String> obter() {
        return List.copyOf(nomes);
    }

    @Override
    public boolean atualizar(String nomeAntigo, String novoNome) {
        if (novoNome == null || novoNome.isBlank()) {
            return false;
        }

        int indice = nomes.indexOf(nomeAntigo);
        if (indice == -1 || nomes.contains(novoNome)) {
            return false;
        }

        nomes.set(indice, novoNome);
        return true;
    }

    @Override
    public boolean remover(String nome) {
        return nomes.remove(nome);
    }
}
