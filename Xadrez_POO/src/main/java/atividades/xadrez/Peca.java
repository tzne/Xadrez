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
    // Atributos conforme o diagrama
    protected Cor cor; // Você precisará definir o enum Cor (PRETA, BRANCA)
    protected boolean jaMoveu; // Indica se a peça já se moveu (útil para roque, peão, etc.)
    protected TipoPeca tipo; // Para saber o tipo da peça sem precisar de instanceof

    // Construtor
    public Peca(Cor cor, TipoPeca tipo) {
        this.cor = cor;
        this.tipo = tipo;
        this.jaMoveu = false; // Por padrão, a peça não se moveu ao ser criada
    }

    // Métodos Getters
    public Cor getCor() {
        return cor;
    }

    public boolean jaMoveu() {
        return jaMoveu;
    }

    public TipoPeca getTipo() {
        return tipo;
    }

    // Método Setter para jaMoveu (se necessário, ou pode ser setado internamente)
    public void setJaMoveu(boolean jaMoveu) {
        this.jaMoveu = jaMoveu;
    }

    /**
     * Método abstrato para calcular os movimentos legais de uma peça
     * dado o estado atual do tabuleiro e do jogo.
     *
     * @param tabuleiro O tabuleiro atual do jogo.
     * @param jogo O objeto Jogo que contém o estado atual.
     * @return Uma lista de objetos Jogada representando todos os movimentos legais possíveis para esta peça.
     */
    public abstract List<Jogada> calcularMovimentosLegais(Tabuleiro tabuleiro, Jogo jogo);
}