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
public class Peao extends Peca {

    public Peao(Cor cor) {
        super(cor, TipoPeca.PEAO);
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
        if (casaOrigem == null) return movimentos;
        
        Posicao posOrigem = casaOrigem.getPosicao();
        int direcao = (this.getCor() == Cor.BRANCA) ? 1 : -1;
        int promotionRank = (this.getCor() == Cor.BRANCA) ? 8 : 1;

        // 1. Movimento simples para frente
        Posicao posDestinoFrente1 = new Posicao(posOrigem.getColuna(), posOrigem.getLinha() + direcao);
        if (tabuleiro.getCasa(posDestinoFrente1) != null && tabuleiro.getCasa(posDestinoFrente1).estaVazia()) {
            if (posDestinoFrente1.getLinha() == promotionRank) {
                // É uma jogada de promoção
                movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, tabuleiro.getCasa(posDestinoFrente1), this, null, false, false, TipoPeca.RAINHA));
                movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, tabuleiro.getCasa(posDestinoFrente1), this, null, false, false, TipoPeca.TORRE));
                movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, tabuleiro.getCasa(posDestinoFrente1), this, null, false, false, TipoPeca.BISPO));
                movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, tabuleiro.getCasa(posDestinoFrente1), this, null, false, false, TipoPeca.CAVALO));
            } else {
                movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, tabuleiro.getCasa(posDestinoFrente1), this));
            }
            
            // 2. Movimento duplo no primeiro lance
            if (!this.jaMoveu()) {
                Posicao posDestinoFrente2 = new Posicao(posOrigem.getColuna(), posOrigem.getLinha() + 2 * direcao);
                if (tabuleiro.getCasa(posDestinoFrente2) != null && tabuleiro.getCasa(posDestinoFrente2).estaVazia()) {
                    movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, tabuleiro.getCasa(posDestinoFrente2), this));
                }
            }
        }
        
        // 3. Captura na diagonal
        int[] colunasLaterais = {-1, 1};
        for (int modColuna : colunasLaterais) {
            char novaColunaChar = (char)(posOrigem.getColuna() + modColuna);
            if (novaColunaChar >= 'a' && novaColunaChar <= 'h') {
                Posicao posDestinoCaptura = new Posicao(novaColunaChar, posOrigem.getLinha() + direcao);
                Casa casaDestino = tabuleiro.getCasa(posDestinoCaptura);
                if (casaDestino != null && !casaDestino.estaVazia() && casaDestino.getPeca().getCor() != this.getCor()) {
                    if (posDestinoCaptura.getLinha() == promotionRank) {
                        // Promoção com captura
                        movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this, casaDestino.getPeca(), false, false, TipoPeca.RAINHA));
                        movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this, casaDestino.getPeca(), false, false, TipoPeca.TORRE));
                        movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this, casaDestino.getPeca(), false, false, TipoPeca.BISPO));
                        movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this, casaDestino.getPeca(), false, false, TipoPeca.CAVALO));
                    } else {
                        movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this, casaDestino.getPeca(), false, false, null));
                    }
                }
            }
        }

        // 4. Implementação do En Passant
        Jogada ultimaJogada = jogo.getUltimaJogada();
        if (ultimaJogada != null &&
            ultimaJogada.getPecaMovida().getTipo() == TipoPeca.PEAO &&
            Math.abs(ultimaJogada.getCasaOrigem().getPosicao().getLinha() - ultimaJogada.getCasaDestino().getPosicao().getLinha()) == 2 &&
            posOrigem.getLinha() == ultimaJogada.getCasaDestino().getPosicao().getLinha() &&
            Math.abs(posOrigem.getColuna() - ultimaJogada.getCasaDestino().getPosicao().getColuna()) == 1) { 

            Peca pecaCapturada = ultimaJogada.getPecaMovida();
            Posicao posDestinoEnPassant = new Posicao(ultimaJogada.getCasaDestino().getPosicao().getColuna(), posOrigem.getLinha() + direcao);
            Casa casaDestino = tabuleiro.getCasa(posDestinoEnPassant);
            movimentos.add(new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, this, pecaCapturada, false, true, null));
        }

        return movimentos;
    }
}
