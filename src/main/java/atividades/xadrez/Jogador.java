/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez;

/**
 *
 * @author ruama
 */
import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private Cor cor; // Cor das peças do jogador (BRANCA ou PRETA)
    private List<Peca> pecas; // Lista de peças que o jogador possui

    public Jogador(Cor cor) {
        this.cor = cor;
        this.pecas = new ArrayList<>(); // Inicializa a lista de peças
    }

    public Cor getCor() {
        return cor;
    }

    public List<Peca> getPecas() {
        return pecas;
    }

    /**
     * Adiciona uma peça à lista de peças do jogador.
     * @param peca A peça a ser adicionada.
     */
    public void adicionarPeca(Peca peca) {
        if (peca.getCor() == this.cor) { // Garante que a peça é da cor do jogador
            this.pecas.add(peca);
        } else {
            System.err.println("Erro: Tentativa de adicionar peça de cor diferente ao jogador.");
        }
    }

    /**
     * Remove uma peça da lista de peças do jogador (ex: quando é capturada).
     * @param peca A peça a ser removida.
     * @return true se a peça foi removida com sucesso, false caso contrário.
     */
    public boolean removerPeca(Peca peca) {
        return this.pecas.remove(peca);
    }

    @Override
    public String toString() {
        return "Jogador " + cor.name(); // Ex: "Jogador BRANCA"
    }
}