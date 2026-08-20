/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez;
import java.util.List;
/**
 *
 * @author ruama
 */

public abstract class Peca {
    
    protected Cor cor; 
    protected boolean jaMoveu; 
    protected TipoPeca tipo; 
   
    public Peca(Cor cor, TipoPeca tipo) {
        this.cor = cor;
        this.tipo = tipo;
        this.jaMoveu = false; // Por padrão, a peça não se moveu ao ser criada
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