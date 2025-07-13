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
            Peca peao = new Peao(Cor.BRANCA); // Você criará a classe Peao depois
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
            Peca peao = new Peao(Cor.PRETA); // Você criará a classe Peao depois
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

        // Note: As classes Peao, Torre, Cavalo, Bispo, Dama, Rei precisam existir.
        // Por enquanto, as chamadas a 'new Peao(Cor.BRANCA)' etc. resultarão em erro de compilação
        // até que você crie essas classes que estendem 'Peca'.
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
     * @param jogada A jogada proposta.
     * @return true se a jogada foi válida e executada, false caso contrário.
     */
    public boolean executarJogada(Jogada jogada) {
        // 1. Validação básica da jogada (quem está movendo, se é sua vez, etc.)
        if (jogada.getJogador() != jogadorAtual) {
            System.out.println("Não é a vez do jogador " + jogada.getJogador().getCor() + ".");
            return false;
        }

        Peca pecaMovida = jogada.getPecaMovida();
        Casa casaOrigem = jogada.getCasaOrigem();
        Casa casaDestino = jogada.getCasaDestino();

        // 2. Validação se a peça pertence ao jogador atual
        if (pecaMovida.getCor() != jogadorAtual.getCor()) {
            System.out.println("Você só pode mover suas próprias peças.");
            return false;
        }

        // 3. Validação se a peça está realmente na casa de origem
        if (casaOrigem.getPeca() != pecaMovida) {
            System.out.println("A peça na casa de origem não corresponde à peça na jogada.");
            return false;
        }

        // 4. Verificar se a casa de destino contém uma peça do mesmo jogador
        if (!casaDestino.estaVazia() && casaDestino.getPeca().getCor() == jogadorAtual.getCor()) {
            System.out.println("Não é possível mover para uma casa ocupada por uma peça da sua cor.");
            return false;
        }

        // 5. Verificar se a jogada é legal para o tipo de peça (aqui chamaria o método da Peca)
        // ESSA É A PARTE CRÍTICA QUE VAI REQUERER MUITA LÓGICA NAS SUBCLASSES DE PECA.
        // Por enquanto, vamos assumir que se chegou aqui, a peça "sabe" que pode mover.
        // A validação de "movimentos legais" real será feita dentro de Peca.calcularMovimentosLegais.
        // Aqui, precisaríamos comparar a 'jogada' recebida com a lista de movimentos legais gerada pela peça.

        // Uma implementação mais robusta faria:
        // List<Jogada> movimentosLegais = pecaMovida.calcularMovimentosLegais(tabuleiro, this);
        // if (!movimentosLegais.contains(jogada)) {
        //     System.out.println("Movimento inválido para esta peça.");
        //     return false;
        // }
        // Para que .contains(jogada) funcione, você precisaria implementar equals() e hashCode() em Jogada,
        // o que pode ser complicado dada a complexidade de Jogada.
        // Uma alternativa é gerar as jogadas e tentar encontrar uma que corresponda aos parâmetros chave da jogada recebida.

        // Simulação do movimento no tabuleiro (chamando o método do Tabuleiro)
        boolean movimentoSucesso = tabuleiro.executarMovimento(jogada);

        if (movimentoSucesso) {
            historicoJogadas.add(jogada);
            this.ultimaJogada = jogada; // Atualiza a última jogada

            // Lógica para capturas: se uma peça foi capturada, removê-la do jogador adversário
            if (jogada.getPecaCapturada() != null) {
                Jogador adversario = getJogador(jogadorAtual.getCor() == Cor.BRANCA ? Cor.PRETA : Cor.BRANCA);
                adversario.removerPeca(jogada.getPecaCapturada());
                System.out.println("Peça capturada: " + jogada.getPecaCapturada().getTipo());
            }

            // TODO: Lógica para verificar Xeque, Xeque-mate, Empate após a jogada
            // Isso é complexo e envolve:
            // 1. Verificar se o próprio rei do jogador atual ficou em xeque após o movimento (movimento ilegal).
            // 2. Verificar se o rei do adversário está em xeque.
            // 3. Se estiver em xeque, verificar se é xeque-mate.
            // 4. Verificar condições de empate.
            // setEstadoJogo(EstadoJogo.XEQUEMATE); // Exemplo

            // Trocar o jogador atual
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

    // Métodos utilitários adicionais podem ser necessários aqui, como:
    // - isReiEmXeque(Cor cor)
    // - getTodasPecas(Cor cor)
    // - verificarXequeMate()
    // - verificarEmpate()

    /**
     * Exemplo de como usar o jogo (Main para teste temporário)
     */
    public static void main(String[] args) {
        System.out.println("Iniciando jogo de xadrez...");
        Jogo jogo = new Jogo();
        jogo.getTabuleiro().imprimirTabuleiro(); // Imprime o tabuleiro inicial

        // Exemplo de uma jogada de Peão (se Peao já estiver implementado)
        // Isso é apenas um exemplo básico e não tem validação completa!
        // Requer que a classe Peao exista
        try {
            Posicao origemPeao = new Posicao('e', 2);
            Posicao destinoPeao = new Posicao('e', 4);
            Casa casaOrigem = jogo.getTabuleiro().getCasa(origemPeao);
            Casa casaDestino = jogo.getTabuleiro().getCasa(destinoPeao);
            Peca peaoMovido = casaOrigem.getPeca();

            if (peaoMovido != null && peaoMovido.getTipo() == TipoPeca.PEAO && peaoMovido.getCor() == Cor.BRANCA) {
                System.out.println("\nTentando mover Peão de e2 para e4...");
                Jogada jogadaTeste = new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, peaoMovido);
                boolean sucesso = jogo.executarJogada(jogadaTeste);
                if (sucesso) {
                    System.out.println("Movimento do peão bem-sucedido.");
                    jogo.getTabuleiro().imprimirTabuleiro();
                } else {
                    System.out.println("Movimento do peão falhou.");
                }
            } else {
                System.out.println("Não há um peão branco em e2 para mover.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de posição: " + e.getMessage());
        }

        // Você pode continuar adicionando mais jogadas e testando o fluxo.
        // Lembre-se que a lógica completa de movimentos e xeque/mate é complexa
        // e será implementada nas subclasses de Peca e em métodos de Jogo.
    }
}