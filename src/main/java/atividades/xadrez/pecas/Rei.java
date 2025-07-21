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

public class Rei extends Peca {

    public Rei(Cor cor) {
        super(cor, TipoPeca.REI);
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
        Cor corOponente = (this.getCor() == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
        
        int[][] movimentosRei = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] m : movimentosRei) {
            int novaLinha = posOrigem.getLinha() + m[0];
            char novaColunaChar = (char) (posOrigem.getColuna() + m[1]);

            if (novaLinha >= 1 && novaLinha <= Tabuleiro.TAMANHO && novaColunaChar >= 'a' && novaColunaChar <= 'h') {
                Posicao posDestino = new Posicao(novaColunaChar, novaLinha);
                Casa casaDestino = tabuleiro.getCasa(posDestino);

                if (casaDestino.estaVazia() || casaDestino.getPeca().getCor() != this.getCor()) {
                    if (!jogo.isCasaEmAtaque(posDestino, corOponente)) {
                        Peca pecaCapturada = casaDestino.estaVazia() ? null : casaDestino.getPeca();
                        movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this, pecaCapturada, false, false, null));
                    }
                }
            }
        }
        
        // roque
        if (!this.jaMoveu() && !jogo.isCasaEmAtaque(posOrigem, corOponente)) {
            // Roque do lado do Rei (Roque Pequeno)
            verificarRoque(jogo, tabuleiro, casaOrigem, 1, corOponente, movimentos);
            // Roque do lado da Rainha (Roque Grande)
            verificarRoque(jogo, tabuleiro, casaOrigem, -1, corOponente, movimentos);
        }

        return movimentos;
    }

    private void verificarRoque(Jogo jogo, Tabuleiro tabuleiro, Casa casaOrigem, int direcao, Cor corOponente, List<Jogada> movimentos) {
        int linha = casaOrigem.getPosicao().getLinha();
        char colunaTorre = (direcao == 1) ? 'h' : 'a';
        Peca pecaTorre = tabuleiro.getPecaEmPosicao(new Posicao(colunaTorre, linha));

        if (pecaTorre != null && pecaTorre.getTipo() == TipoPeca.TORRE && !pecaTorre.jaMoveu()) {
            boolean caminhoLivre = true;
            for (int i = 1; i < (direcao == 1 ? 3 : 4); i++) {
                char colunaVerificada = (char) (casaOrigem.getPosicao().getColuna() + i * direcao);
                Posicao posVerificada = new Posicao(colunaVerificada, linha);
                if (!tabuleiro.getCasa(posVerificada).estaVazia() || (i < 3 && jogo.isCasaEmAtaque(posVerificada, corOponente))) {
                    caminhoLivre = false;
                    break;
                }
            }
            
            if (caminhoLivre) {
                char colunaDestinoRei = (char) (casaOrigem.getPosicao().getColuna() + 2 * direcao);
                Posicao posDestinoRei = new Posicao(colunaDestinoRei, linha);
                Casa casaDestinoRei = tabuleiro.getCasa(posDestinoRei);
                movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestinoRei, this, null, true, false, null));
            }
        }
    }
}
