package atividades.xadrez;
import java.util.List;

public abstract class Peca {
    protected Cor cor; 
    protected boolean jaMoveu; 
    protected TipoPeca tipo; 
   
    public Peca(Cor cor, TipoPeca tipo) {
        this.cor = cor;
        this.tipo = tipo;
        this.jaMoveu = false;
    }

    public Cor getCor() {
        return cor;
    }

    public boolean jaMoveu() {
        return jaMoveu;
    }

    public TipoPeca getTipo() {
        return tipo;
    }

    public void setJaMoveu(boolean jaMoveu) {
        this.jaMoveu = jaMoveu;
    }

    public abstract List<Jogada> calcularMovimentosLegais(Tabuleiro tabuleiro, Jogo jogo);
}
