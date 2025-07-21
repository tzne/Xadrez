package atividades.xadrez;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private Cor cor; // BRANCA ou PRETA
    private List<Peca> pecas; // lista de peças que o jogador possui

    public Jogador(Cor cor) {
        this.cor = cor;
        this.pecas = new ArrayList<>(); 
    }

    public Cor getCor() {
        return cor;
    }

    public List<Peca> getPecas() {
        return pecas;
    }

    public void adicionarPeca(Peca peca) {
        if (peca.getCor() == this.cor) {
            this.pecas.add(peca);
        } else {
            System.err.println("Erro: Tentativa de adicionar peça de cor diferente ao jogador.");
        }
    }
    
    public boolean removerPeca(Peca peca) {
        return this.pecas.remove(peca);
    }

    @Override
    public String toString() {
        return "Jogador " + cor.name();
    }
}
