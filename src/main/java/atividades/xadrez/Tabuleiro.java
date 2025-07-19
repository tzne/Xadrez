/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez;

/**
 *
 * @author ruama
 */

public class Tabuleiro {
    private Casa[][] casas; // Array 8x8 de Casas
    public static final int TAMANHO = 8;

    public Tabuleiro() {
        casas = new Casa[TAMANHO][TAMANHO];
        inicializarCasas(); 
    }

    private void inicializarCasas() {
        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                char charColuna = (char) ('a' + coluna);
                int numLinha = TAMANHO - linha;
                casas[linha][coluna] = new Casa(new Posicao(charColuna, numLinha));
            }
        }
    }

    public Casa getCasa(Posicao posicao) {
        if (posicao == null) return null;
        int arrayLinha = TAMANHO - posicao.getLinha();
        int arrayColuna = posicao.getColuna() - 'a';

        if (arrayLinha >= 0 && arrayLinha < TAMANHO &&
            arrayColuna >= 0 && arrayColuna < TAMANHO) {
            return casas[arrayLinha][arrayColuna];
        }
        return null;
    }

    public Peca getPecaEmPosicao(Posicao posicao) {
        Casa casa = getCasa(posicao);
        return (casa != null) ? casa.getPeca() : null;
    }
    
    /**
     * Executa um movimento simulado, retornando a peça capturada (se houver).
     */
    public Peca executarMovimentoSimulado(Jogada jogada) {
        Casa casaOrigem = jogada.getCasaOrigem();
        Casa casaDestino = jogada.getCasaDestino();
        Peca pecaMovida = jogada.getPecaMovida();
        Peca pecaCapturada = casaDestino.getPeca();

        casaDestino.setPeca(pecaMovida);
        casaOrigem.removerPeca();
        return pecaCapturada;
    }
    
    /**
     * Desfaz um movimento simulado.
     */
    public void desfazerMovimentoSimulado(Jogada jogada, Peca pecaCapturada) {
        Casa casaOrigem = jogada.getCasaOrigem();
        Casa casaDestino = jogada.getCasaDestino();
        Peca pecaMovida = jogada.getPecaMovida();

        casaOrigem.setPeca(pecaMovida);
        casaDestino.setPeca(pecaCapturada); // Recoloca a peça capturada
    }


    /**
     * Executa um movimento real no tabuleiro.
     */
    public boolean executarMovimento(Jogada jogada) {
        Casa casaOrigem = jogada.getCasaOrigem();
        Casa casaDestino = jogada.getCasaDestino();
        Peca pecaMovida = jogada.getPecaMovida();

        if (casaOrigem.getPeca() != pecaMovida) {
            System.err.println("Erro: Peça na origem não corresponde à peça movida na jogada.");
            return false;
        }

        // Lógica de En Passant
        if (jogada.isEnPassant()) {
             // Move o peão que captura
            casaOrigem.removerPeca();
            casaDestino.setPeca(pecaMovida);

            // Remove o peão capturado, que está na mesma coluna do destino, mas em linha diferente
            int linhaPeaoCapturado = casaOrigem.getPosicao().getLinha();
            char colunaPeaoCapturado = casaDestino.getPosicao().getColuna();
            Posicao posPeaoCapturado = new Posicao(colunaPeaoCapturado, linhaPeaoCapturado);
            getCasa(posPeaoCapturado).removerPeca();
        }
        // Lógica de Roque
        else if (jogada.isRoque()) {
             executarRoque(jogada);
        } 
        // Movimento Padrão
        else {
            casaOrigem.removerPeca();
            casaDestino.setPeca(pecaMovida);
        }

        pecaMovida.setJaMoveu(true);
        return true;
    }
    
    private void executarRoque(Jogada jogada) {
        Casa casaOrigemRei = jogada.getCasaOrigem();
        Casa casaDestinoRei = jogada.getCasaDestino();
        Peca rei = jogada.getPecaMovida();
        
        // Move o Rei
        casaOrigemRei.removerPeca();
        casaDestinoRei.setPeca(rei);
        
        // Move a Torre
        int linha = casaOrigemRei.getPosicao().getLinha();
        boolean roquePequeno = casaDestinoRei.getPosicao().getColuna() == 'g';
        
        Posicao posOrigemTorre = new Posicao(roquePequeno ? 'h' : 'a', linha);
        Posicao posDestinoTorre = new Posicao(roquePequeno ? 'f' : 'd', linha);
        
        Casa casaOrigemTorre = getCasa(posOrigemTorre);
        Casa casaDestinoTorre = getCasa(posDestinoTorre);
        Peca torre = casaOrigemTorre.getPeca();
        
        casaOrigemTorre.removerPeca();
        casaDestinoTorre.setPeca(torre);
        torre.setJaMoveu(true);
    }

    public void imprimirTabuleiro() {
        System.out.println("\n  a b c d e f g h");
        System.out.println("  -----------------");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(TAMANHO - i + "|"); 
            for (int j = 0; j < TAMANHO; j++) {
                Peca pecaNaCasa = casas[i][j].getPeca();
                if (pecaNaCasa == null) {
                    System.out.print(" ."); 
                } else {
                    char simbolo = ' ';
                    switch (pecaNaCasa.getTipo()) {
                        case REI: simbolo = 'K'; break;
                        case RAINHA: simbolo = 'Q'; break;
                        case TORRE: simbolo = 'R'; break;
                        case BISPO: simbolo = 'B'; break;
                        case CAVALO: simbolo = 'N'; break;
                        case PEAO: simbolo = 'P'; break;
                    }
                    if (pecaNaCasa.getCor() == Cor.PRETA) {
                        simbolo = Character.toLowerCase(simbolo);
                    }
                    System.out.print(" " + simbolo);
                }
            }
            System.out.println(" |" + (TAMANHO - i)); 
        }
        System.out.println("  -----------------");
        System.out.println("  a b c d e f g h\n");
    }
}