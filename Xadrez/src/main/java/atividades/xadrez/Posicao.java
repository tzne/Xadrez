/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez;

/**
 *
 * @author ruama
 */

public class Posicao {
    private char coluna; // 'a' a 'h'
    private int linha;   // 1 a 8

    public Posicao(char coluna, int linha) {
        // Validações básicas (opcional, mas boa prática)
        if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
            throw new IllegalArgumentException("Posição inválida: " + coluna + linha);
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    // Métodos para facilitar a comparação e depuração
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posicao posicao = (Posicao) o;
        return coluna == posicao.coluna && linha == posicao.linha;
    }

    @Override
    public int hashCode() {
        // Uma forma simples de gerar hashCode combinando coluna e linha
        return Character.hashCode(coluna) * 31 + Integer.hashCode(linha);
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }
}