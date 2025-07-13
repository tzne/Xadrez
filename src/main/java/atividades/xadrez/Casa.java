/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez;

/**
 *
 * @author ruama
 */
public class Casa {
    private Posicao posicao; // A posição da casa no tabuleiro
    private Peca peca;       // A peça que ocupa esta casa (pode ser null se vazia)

    public Casa(Posicao posicao) {
        this.posicao = posicao;
        this.peca = null; // Uma casa começa vazia por padrão
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public Peca getPeca() {
        return peca;
    }

    // Método para colocar uma peça nesta casa
    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    // Método para remover a peça desta casa
    public Peca removerPeca() {
        Peca pecaRemovida = this.peca;
        this.peca = null;
        return pecaRemovida;
    }

    // Método para verificar se a casa está vazia
    public boolean estaVazia() {
        return peca == null;
    }

    @Override
    public String toString() {
        return "Casa{" +
               "posicao=" + posicao +
               ", peca=" + (peca != null ? peca.getTipo() + " " + peca.getCor() : "Vazia") +
               '}';
    }
}