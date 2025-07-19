/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package atividades.xadrez;
import atividades.xadrez.pecas.*;
/**
 *
 * @author ruama
 */
// src/main/java/seu/pacote/Jogo.java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Jogo {
    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private Jogador jogadorAtual; // O jogador que deve fazer o próximo movimento
    private EstadoJogo estadoJogo;
    private List<Jogada> historicoJogadas; // Para registrar todos os movimentos
    private Jogada ultimaJogada; // A última jogada feita (útil para en passant, roque, etc.)

    public Jogo() {
        this.tabuleiro = new Tabuleiro();
        this.jogadores = new ArrayList<>();
        this.historicoJogadas = new ArrayList<>();
        this.estadoJogo = EstadoJogo.EM_JOGO; // O jogo começa em andamento
        this.ultimaJogada = null; // Nenhuma jogada ainda
        inicializarJogadores();
        posicionarPecasIniciais();
        this.jogadorAtual = getJogador(Cor.BRANCA); // Brancas começam
    }

    // Método auxiliar para criar os jogadores
    private void inicializarJogadores() {
        jogadores.add(new Jogador(Cor.BRANCA));
        jogadores.add(new Jogador(Cor.PRETA));
    }

    // Método auxiliar para posicionar as peças no início do jogo
    private void posicionarPecasIniciais() {
        // Obter os jogadores por cor
        Jogador branco = getJogador(Cor.BRANCA);
        Jogador preto = getJogador(Cor.PRETA);

        // --- Peças Brancas ---
        // Peões
        for (char col = 'a'; col <= 'h'; col++) {
            Peca peao = new Peao(Cor.BRANCA);
            tabuleiro.getCasa(new Posicao(col, 2)).setPeca(peao);
            branco.adicionarPeca(peao);
        }
        // Torres
        Peca torreB1 = new Torre(Cor.BRANCA); Peca torreB2 = new Torre(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('a', 1)).setPeca(torreB1); branco.adicionarPeca(torreB1);
        tabuleiro.getCasa(new Posicao('h', 1)).setPeca(torreB2); branco.adicionarPeca(torreB2);
        // Cavalos
        Peca cavaloB1 = new Cavalo(Cor.BRANCA); Peca cavaloB2 = new Cavalo(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('b', 1)).setPeca(cavaloB1); branco.adicionarPeca(cavaloB1);
        tabuleiro.getCasa(new Posicao('g', 1)).setPeca(cavaloB2); branco.adicionarPeca(cavaloB2);
        // Bispos
        Peca bispoB1 = new Bispo(Cor.BRANCA); Peca bispoB2 = new Bispo(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('c', 1)).setPeca(bispoB1); branco.adicionarPeca(bispoB1);
        tabuleiro.getCasa(new Posicao('f', 1)).setPeca(bispoB2); branco.adicionarPeca(bispoB2);
        // Dama
        Peca damaB = new Rainha(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('d', 1)).setPeca(damaB); branco.adicionarPeca(damaB);
        // Rei
        Peca reiB = new Rei(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('e', 1)).setPeca(reiB); branco.adicionarPeca(reiB);


        // --- Peças Pretas ---
        // Peões
        for (char col = 'a'; col <= 'h'; col++) {
            Peca peao = new Peao(Cor.PRETA);
            tabuleiro.getCasa(new Posicao(col, 7)).setPeca(peao);
            preto.adicionarPeca(peao);
        }
        // Torres
        Peca torreP1 = new Torre(Cor.PRETA); Peca torreP2 = new Torre(Cor.PRETA);
        tabuleiro.getCasa(new Posicao('a', 8)).setPeca(torreP1); preto.adicionarPeca(torreP1);
        tabuleiro.getCasa(new Posicao('h', 8)).setPeca(torreP2); preto.adicionarPeca(torreP2);
        // Cavalos
        Peca cavaloP1 = new Cavalo(Cor.PRETA); Peca cavaloP2 = new Cavalo(Cor.PRETA);
        tabuleiro.getCasa(new Posicao('b', 8)).setPeca(cavaloP1); preto.adicionarPeca(cavaloP1);
        tabuleiro.getCasa(new Posicao('g', 8)).setPeca(cavaloP2); preto.adicionarPeca(cavaloP2);
        // Bispos
        Peca bispoP1 = new Bispo(Cor.PRETA); Peca bispoP2 = new Bispo(Cor.PRETA);
        tabuleiro.getCasa(new Posicao('c', 8)).setPeca(bispoP1); preto.adicionarPeca(bispoP1);
        tabuleiro.getCasa(new Posicao('f', 8)).setPeca(bispoP2); preto.adicionarPeca(bispoP2);
        // Dama
        Peca damaP = new Rainha(Cor.PRETA);
        tabuleiro.getCasa(new Posicao('d', 8)).setPeca(damaP); preto.adicionarPeca(damaP);
        // Rei
        Peca reiP = new Rei(Cor.PRETA);
        tabuleiro.getCasa(new Posicao('e', 8)).setPeca(reiP); preto.adicionarPeca(reiP);

    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public Jogador getJogadorAtual() {
        return jogadorAtual;
    }

    public EstadoJogo getEstadoJogo() {
        return estadoJogo;
    }

    public List<Jogada> getHistoricoJogadas() {
        return historicoJogadas;
    }

    public Jogada getUltimaJogada() {
        return ultimaJogada;
    }

    public Jogador getJogador(Cor cor) {
        return jogadores.stream()
                        .filter(j -> j.getCor() == cor)
                        .findFirst()
                        .orElse(null);
    }

     /**
     * Encontra a casa de uma peça específica no tabuleiro.
     */
    private Casa findCasaDaPeca(Peca peca) {
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                char c = (char) ('a' + j);
                int l = Tabuleiro.TAMANHO - i;
                Casa casa = this.tabuleiro.getCasa(new Posicao(c, l));
                if (casa.getPeca() == peca) {
                    return casa;
                }
            }
        }
        return null;
    }


    /**
     * Verifica se uma determinada casa está sob ataque por qualquer peça da cor atacante.
     * Esta é a versão corrigida para evitar StackOverflow.
     */
    public boolean isCasaEmAtaque(Posicao posicaoAlvo, Cor corAtacante) {
        Jogador atacante = getJogador(corAtacante);
        for (Peca peca : atacante.getPecas()) {
            Casa casaDaPeca = findCasaDaPeca(peca);
            if (casaDaPeca == null) continue;

            // Lógica de ataque do Peão (somente diagonal)
            if (peca.getTipo() == TipoPeca.PEAO) {
                int direcao = (peca.getCor() == Cor.BRANCA) ? 1 : -1;
                Posicao posOrigem = casaDaPeca.getPosicao();
                
                // Checa as duas capturas diagonais
                for (int modColuna : new int[]{-1, 1}) {
                    char novaColunaChar = (char)(posOrigem.getColuna() + modColuna);
                    if (novaColunaChar >= 'a' && novaColunaChar <= 'h') {
                        Posicao posDestinoCaptura = new Posicao(novaColunaChar, posOrigem.getLinha() + direcao);
                        if (posDestinoCaptura.equals(posicaoAlvo)) {
                            return true;
                        }
                    }
                }
            } 
            // Lógica de ataque do Rei (somente adjacentes) - EVITA RECURSÃO
            else if (peca.getTipo() == TipoPeca.REI) {
                Posicao posRei = casaDaPeca.getPosicao();
                int diffLinha = Math.abs(posRei.getLinha() - posicaoAlvo.getLinha());
                int diffColuna = Math.abs(posRei.getColuna() - posicaoAlvo.getColuna());
                if (diffLinha <= 1 && diffColuna <= 1) {
                    return true;
                }
            } 
            // Lógica para outras peças
            else {
                List<Jogada> movimentos = peca.calcularMovimentosLegais(this.tabuleiro, this);
                for (Jogada jogada : movimentos) {
                    if (jogada.getCasaDestino().getPosicao().equals(posicaoAlvo)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Encontra a posição do Rei de uma determinada cor.
     */
    private Posicao findPosicaoRei(Cor corRei) {
        Peca rei = getJogador(corRei).getPecas().stream()
            .filter(p -> p.getTipo() == TipoPeca.REI)
            .findFirst().orElse(null);
        
        if (rei != null) {
            Casa casaDoRei = findCasaDaPeca(rei);
            if (casaDoRei != null) {
                return casaDoRei.getPosicao();
            }
        }
        return null; // Não deve acontecer em um jogo normal
    }

    /**
     * Verifica se o Rei de uma determinada cor está em xeque.
     */
    public boolean isReiEmXeque(Cor corRei) {
        Posicao posRei = findPosicaoRei(corRei);
        if (posRei == null) {
            return false; // Rei não está no tabuleiro
        }
        Cor corOponente = (corRei == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
        return isCasaEmAtaque(posRei, corOponente);
    }
    
    /**
     * Gera todos os movimentos legais para um jogador, considerando a regra de não poder terminar em xeque.
     */
    private List<Jogada> getTodosMovimentosSeguros(Jogador jogador) {
        List<Jogada> movimentosSeguros = new ArrayList<>();
        
        for (Peca peca : jogador.getPecas()) {
            List<Jogada> movimentosDaPeca = peca.calcularMovimentosLegais(tabuleiro, this);
            for (Jogada jogada : movimentosDaPeca) {
                // Simula a jogada
                Peca pecaCapturada = tabuleiro.executarMovimentoSimulado(jogada);
                
                // Se o rei não estiver em xeque após a jogada, ela é segura
                if (!isReiEmXeque(jogador.getCor())) {
                    movimentosSeguros.add(jogada);
                }
                
                // Desfaz a jogada simulada
                tabuleiro.desfazerMovimentoSimulado(jogada, pecaCapturada);
            }
        }
        return movimentosSeguros;
    }


    /**
     * Tenta executar uma jogada no jogo.
     */
     public boolean executarJogada(Jogada jogadaProposta) {
        Peca pecaMovida = jogadaProposta.getPecaMovida();
        
        // Validações básicas
        if (jogadaProposta.getJogador() != jogadorAtual) {
            System.out.println("Não é a vez do jogador " + jogadaProposta.getJogador().getCor() + ".");
            return false;
        }
        if (pecaMovida.getCor() != jogadorAtual.getCor()) {
            System.out.println("Você só pode mover suas próprias peças.");
            return false;
        }

        // Gera todos os movimentos que tiram o jogador de um possível xeque
        List<Jogada> movimentosValidos = getTodosMovimentosSeguros(jogadorAtual);
        
        Jogada jogadaReal = null;
        for (Jogada valida : movimentosValidos) {
            if (valida.getPecaMovida() == pecaMovida && valida.getCasaDestino().equals(jogadaProposta.getCasaDestino())) {
                jogadaReal = valida;
                break;
            }
        }

        if (jogadaReal == null) {
            if (isReiEmXeque(jogadorAtual.getCor())) {
                System.out.println("Movimento inválido. Você deve sair do xeque.");
            } else {
                System.out.println("Movimento inválido para a peça " + pecaMovida.getTipo() + ".");
            }
            return false;
        }

        boolean movimentoSucesso = tabuleiro.executarMovimento(jogadaReal);

        if (movimentoSucesso) {
            historicoJogadas.add(jogadaReal);
            this.ultimaJogada = jogadaReal;

            if (jogadaReal.getPecaCapturada() != null) {
                Jogador adversario = getAdversario();
                adversario.removerPeca(jogadaReal.getPecaCapturada());
            }

            // Após a jogada, verifica o estado do adversário
            Jogador adversario = getAdversario();
            if (isReiEmXeque(adversario.getCor())) {
                List<Jogada> movimentosDeFuga = getTodosMovimentosSeguros(adversario);
                if (movimentosDeFuga.isEmpty()) {
                    estadoJogo = EstadoJogo.XEQUEMATE;
                } else {
                    System.out.println("XEQUE!");
                }
            }
            
            trocarJogadorAtual();
            return true;
        }
        return false;
    }
    
    private Jogador getAdversario() {
        return (jogadorAtual.getCor() == Cor.BRANCA) ? getJogador(Cor.PRETA) : getJogador(Cor.BRANCA);
    }

    private void trocarJogadorAtual() {
        jogadorAtual = getAdversario();
    }
}