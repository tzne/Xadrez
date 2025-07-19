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

    /**
     * Retorna o jogador pela cor.
     * @param cor A cor do jogador.
     * @return O objeto Jogador correspondente à cor, ou null se não encontrado.
     */
    public Jogador getJogador(Cor cor) {
        return jogadores.stream()
                        .filter(j -> j.getCor() == cor)
                        .findFirst()
                        .orElse(null);
    }

    /**
     * Tenta executar uma jogada no jogo.
     * Este é o método principal que encapsula a lógica de regras e atualização do estado.
     *
     * @param jogadaProposta A jogada proposta pelo usuário.
     * @return true se a jogada foi válida e executada, false caso contrário.
     */
     public boolean executarJogada(Jogada jogadaProposta) {
        if (jogadaProposta.getJogador() != jogadorAtual) {
            System.out.println("Não é a vez do jogador " + jogadaProposta.getJogador().getCor() + ".");
            return false;
        }

        Peca pecaMovida = jogadaProposta.getPecaMovida();
        Casa casaOrigem = jogadaProposta.getCasaOrigem();
        Casa casaDestino = jogadaProposta.getCasaDestino();

        if (pecaMovida.getCor() != jogadorAtual.getCor()) {
            System.out.println("Você só pode mover suas próprias peças.");
            return false;
        }

        if (casaOrigem.getPeca() != pecaMovida) {
            System.out.println("A peça na casa de origem não corresponde à peça na jogada.");
            return false;
        }

        List<Jogada> movimentosLegais = pecaMovida.calcularMovimentosLegais(tabuleiro, this);
        boolean movimentoValido = false;
        Jogada jogadaReal = null;

        for (Jogada legal : movimentosLegais) {
            if (legal.getCasaDestino().equals(casaDestino)) {
                movimentoValido = true;
                jogadaReal = legal; 
                break;
            }
        }

        if (!movimentoValido) {
            System.out.println("Movimento inválido para a peça " + pecaMovida.getTipo() + ".");
            return false;
        }

        boolean movimentoSucesso = tabuleiro.executarMovimento(jogadaReal);

        if (movimentoSucesso) {
            historicoJogadas.add(jogadaReal);
            this.ultimaJogada = jogadaReal;

            if (jogadaReal.getPecaCapturada() != null) {
                Jogador adversario = getJogador(jogadorAtual.getCor() == Cor.BRANCA ? Cor.PRETA : Cor.BRANCA);
                adversario.removerPeca(jogadaReal.getPecaCapturada());
                System.out.println("Peça capturada: " + jogadaReal.getPecaCapturada().getTipo());
            }

            trocarJogadorAtual();
            return true;
        }
        return false;
    }


    /**
     * Troca o jogador atual.
     */
    private void trocarJogadorAtual() {
        if (jogadorAtual.getCor() == Cor.BRANCA) {
            jogadorAtual = getJogador(Cor.PRETA);
        } else {
            jogadorAtual = getJogador(Cor.BRANCA);
        }
    }
}