/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez.pecas;
import atividades.xadrez.Cor;
import atividades.xadrez.Jogo;
import atividades.xadrez.Peca;
import atividades.xadrez.TipoPeca;
import atividades.xadrez.Tabuleiro;
import atividades.xadrez.Jogada;
import atividades.xadrez.Casa;
import atividades.xadrez.Posicao;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ruama
 */
public class Cavalo extends Peca {

    public Cavalo(Cor cor) {
        super(cor, TipoPeca.CAVALO);
    }

    @Override
    public List<Jogada> calcularMovimentosLegais(Tabuleiro tabuleiro, Jogo jogo) {
        List<Jogada> movimentos = new ArrayList<>();
        Casa casaOrigem = null;

        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                char c = (char) ('a' + j);
                int l = Tabuleiro.TAMANHO - i;
                Casa casa = tabuleiro.getCasa(new Posicao(c, l));
                if (casa.getPeca() == this) {
                    casaOrigem = casa;
                    break;
                }
            }
            if (casaOrigem != null) break;
        }

        if (casaOrigem == null) {
            return movimentos;
        }

        Posicao posOrigem = casaOrigem.getPosicao();
        // Todos os 8 movimentos possíveis do cavalo
        int[][] movimentosCavalo = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] m : movimentosCavalo) {
            int novaLinha = posOrigem.getLinha() + m[0];
            char novaColunaChar = (char) (posOrigem.getColuna() + m[1]);

            if (novaLinha >= 1 && novaLinha <= Tabuleiro.TAMANHO && novaColunaChar >= 'a' && novaColunaChar <= 'h') {
                Posicao posDestino = new Posicao(novaColunaChar, novaLinha);
                Casa casaDestino = tabuleiro.getCasa(posDestino);

                if (casaDestino.estaVazia() || casaDestino.getPeca().getCor() != this.getCor()) {
                    Peca pecaCapturada = casaDestino.estaVazia() ? null : casaDestino.getPeca();
                    movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this, pecaCapturada, false, false, null));
                }
            }
        }
        return movimentos;
    }
}