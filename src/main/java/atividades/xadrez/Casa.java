package atividades.xadrez;

public class Casa {
    private Posicao posicao; // posição da casa no tabuleiro
    private Peca peca;

    public Casa(Posicao posicao) {
        this.posicao = posicao;
        this.peca = null;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Peca removerPeca() {
        Peca pecaRemovida = this.peca;
        this.peca = null;
        return pecaRemovida;
    }

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
