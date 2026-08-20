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
public class Rainha extends Peca {

    public Rainha(Cor cor) {
        super(cor, TipoPeca.RAINHA);
    }

    @Override
    public List<Jogada> calcularMovimentosLegais(Tabuleiro tabuleiro, Jogo jogo) {
        List<Jogada> movimentos = new ArrayList<>();
        Casa casaOrigem = null;

        // Encontra a casa onde a rainha está
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                char coluna = (char) ('a' + j);
                int linha = Tabuleiro.TAMANHO - i;
                Casa casa = tabuleiro.getCasa(new Posicao(coluna, linha));
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
        
       
        int[][] direcoes = {
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}, // Diagonais
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}  // Retas (vertical e horizontal)
        };

        for (int[] d : direcoes) {
            adicionarMovimentosEmLinha(tabuleiro, jogo, casaOrigem, d[0], d[1], movimentos);
        }

        return movimentos;
    }

    private void adicionarMovimentosEmLinha(Tabuleiro tabuleiro, Jogo jogo, Casa casaOrigem, int dLinha, int dColuna, List<Jogada> movimentos) {
        Posicao posOrigem = casaOrigem.getPosicao();
        
        for (int i = 1; i < Tabuleiro.TAMANHO; i++) {
            int novaLinha = posOrigem.getLinha() + i * dLinha;
            char novaColunaChar = (char) (posOrigem.getColuna() + i * dColuna);

           
            if (novaLinha < 1 || novaLinha > Tabuleiro.TAMANHO || novaColunaChar < 'a' || novaColunaChar > 'h') {
                break; 
            }

            Posicao posDestino = new Posicao(novaColunaChar, novaLinha);
            Casa casaDestino = tabuleiro.getCasa(posDestino);

            if (casaDestino.estaVazia()) {
                // Se a casa está vazia, é um movimento válido
                movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this));
            } else {
                // Se a casa contém uma peça
                Peca pecaNoDestino = casaDestino.getPeca();
                if (pecaNoDestino.getCor() != this.getCor()) {
                    
                     movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this, pecaNoDestino, false, false, null));
                }
                
                break;
            }
        }
    }
}
