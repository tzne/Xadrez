/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez.pecas;
import atividades.xadrez.Cor;
import atividades.xadrez.Jogo;
import atividades.xadrez.Peca;
import atividades.xadrez.TipoPeca;
import java.util.List;
import java.util.ArrayList; // Exemplo de import
/**
 *
 * @author ruama
 */
// Importe Tabuleiro, Jogo, Jogada quando existirem
// import seu.pacote.Tabuleiro;
// import seu.pacote.Jogo;
// import seu.pacote.Jogada;

public class Cavalo extends Peca {

    public Cavalo(Cor cor) {
        super(cor, TipoPeca.CAVALO);
    }

    @Override
    public List<Jogada> calcularMovimentosLegais(Tabuleiro tabuleiro, Jogo jogo) {
        List<Jogada> movimentos = new ArrayList<>();
        // Lógica específica para calcular movimentos da Torre
        // (movimentos em linhas e colunas, sem pular peças, etc.)
        // Isso será mais complexo e precisará das classes Casa, Posicao, Tabuleiro.
        return movimentos;
    }
}