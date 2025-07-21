package atividades.xadrez;

public class Posicao {
    private char coluna; // 'a' a 'h'
    private int linha;   // 1 a 8

    public Posicao(char coluna, int linha) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posicao posicao = (Posicao) o;
        return coluna == posicao.coluna && linha == posicao.linha;
    }

    @Override
    public int hashCode() {
        return Character.hashCode(coluna) * 31 + Integer.hashCode(linha);
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }
}
