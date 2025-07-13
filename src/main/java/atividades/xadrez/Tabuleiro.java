/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez;

/**
 *
 * @author ruama
 */

// Importe a classe Jogada quando existir, por enquanto, apenas como placeholder
// import seu.pacote.Jogada;

public class Tabuleiro {
    private Casa[][] casas; // Array 8x8 de Casas

    // O tabuleiro de xadrez é 8x8
    public static final int TAMANHO = 8;

    public Tabuleiro() {
        casas = new Casa[TAMANHO][TAMANHO];
        inicializarCasas(); // Cria todas as 64 casas
    }

    // Método auxiliar para criar todas as casas do tabuleiro
    private void inicializarCasas() {
        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                // 'a' + coluna para obter o caractere da coluna (a, b, c...)
                // 8 - linha para obter o número da linha (1 a 8, pois 0 é a linha 8 e 7 é a linha 1)
                char charColuna = (char) ('a' + coluna);
                int numLinha = TAMANHO - linha;
                casas[linha][coluna] = new Casa(new Posicao(charColuna, numLinha));
            }
        }
    }

    /**
     * Retorna a Casa em uma determinada posição.
     *
     * @param posicao A posição da Casa desejada.
     * @return A Casa na posição especificada, ou null se a posição for inválida.
     */
    public Casa getCasa(Posicao posicao) {
        // Mapear Posicao (coluna char, linha int) para índices do array (linha int, coluna int)
        // Linha: 8 -> 0, 7 -> 1, ..., 1 -> 7 (TAMANHO - posicao.getLinha())
        // Coluna: 'a' -> 0, 'b' -> 1, ..., 'h' -> 7 (posicao.getColuna() - 'a')
        int arrayLinha = TAMANHO - posicao.getLinha();
        int arrayColuna = posicao.getColuna() - 'a';

        // Valida se os índices estão dentro dos limites do array
        if (arrayLinha >= 0 && arrayLinha < TAMANHO &&
            arrayColuna >= 0 && arrayColuna < TAMANHO) {
            return casas[arrayLinha][arrayColuna];
        }
        return null; // Posição fora do tabuleiro
    }

    /**
     * Retorna a Peça que está em uma determinada posição do tabuleiro.
     *
     * @param posicao A posição onde verificar a peça.
     * @return A Peça na posição especificada, ou null se a casa estiver vazia ou a posição for inválida.
     */
    public Peca getPecaEmPosicao(Posicao posicao) {
        Casa casa = getCasa(posicao);
        return (casa != null) ? casa.getPeca() : null;
    }

    /**
     * Executa um movimento no tabuleiro. Este método atualiza o estado das casas
     * envolvidas no movimento (origem e destino) e a peça envolvida.
     *
     * @param jogada O objeto Jogada que descreve o movimento a ser executado.
     * @return true se o movimento foi executado com sucesso, false caso contrário (ex: jogada inválida).
     */
    public boolean executarMovimento(Jogada jogada) {
        // Implementação inicial. Esta lógica será mais complexa e
        // precisará das classes Jogador e Jogo para validações completas.

        Casa casaOrigem = jogada.getCasaOrigem();
        Casa casaDestino = jogada.getCasaDestino();
        Peca pecaMovida = jogada.getPecaMovida();

        // Verifica se a peça realmente está na casa de origem
        if (casaOrigem.getPeca() != pecaMovida) {
            System.err.println("Erro: Peça na origem não corresponde à peça movida na jogada.");
            return false;
        }

        // Simula o movimento:
        // 1. Remove a peça da casa de origem
        casaOrigem.removerPeca();

        // 2. Coloca a peça na casa de destino
        // Se houver uma peça capturada, jogada.getPecaCapturada() deve ser definido antes deste ponto
        casaDestino.setPeca(pecaMovida);

        // Marca a peça como "já moveu"
        pecaMovida.setJaMoveu(true);

        // Lógicas adicionais para Roque, En Passant, Promoção seriam adicionadas aqui
        // (jogada.isRoque(), jogada.isEnPassant(), jogada.getPecaPromovida())

        System.out.println("Movimento executado: " + pecaMovida.getTipo() + " de " +
                           casaOrigem.getPosicao() + " para " + casaDestino.getPosicao());
        return true;
    }

    // Para fins de depuração: imprimir o tabuleiro
    public void imprimirTabuleiro() {
        System.out.println("\n  a b c d e f g h");
        System.out.println("  -----------------");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(TAMANHO - i + "|"); // Números das linhas (8 a 1)
            for (int j = 0; j < TAMANHO; j++) {
                Peca pecaNaCasa = casas[i][j].getPeca();
                if (pecaNaCasa == null) {
                    System.out.print(" ."); // Casa vazia
                } else {
                    // Exemplo: 'R' para Rei Branco, 'r' para Rei Preto
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
            System.out.println(" |" + (TAMANHO - i)); // Números das linhas (8 a 1)
        }
        System.out.println("  -----------------");
        System.out.println("  a b c d e f g h\n");
    }
}
