/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez;
import java.util.Scanner;
import java.util.List;
import atividades.xadrez.Cor;
import atividades.xadrez.Jogo;
import atividades.xadrez.Peca;
import atividades.xadrez.TipoPeca;
import atividades.xadrez.Tabuleiro;
import atividades.xadrez.Jogada;


/**
 *
 * @author ruama
 */
// src/main/java/seu/pacote/Main.java


public class Main {

    public static void main(String[] args) {
        System.out.println("=== Simulador de Jogo de Xadrez ===");

        // 1. Inicializar o Jogo
        Jogo jogo = new Jogo();
        System.out.println("Jogo inicializado. Posições iniciais:");
        jogo.getTabuleiro().imprimirTabuleiro();

        Scanner scanner = new Scanner(System.in);

        // Loop principal do jogo
        while (jogo.getEstadoJogo() == EstadoJogo.EM_JOGO) {
            System.out.println("------------------------------------");
            System.out.println("Vez do " + jogo.getJogadorAtual().getCor() + ".");

            // Exemplo de como um usuário pode inserir um movimento (simplificado)
            System.out.print("Digite a casa de origem (ex: e2): ");
            String origemStr = scanner.nextLine().toLowerCase();
            System.out.print("Digite a casa de destino (ex: e4): ");
            String destinoStr = scanner.nextLine().toLowerCase();

            try {
                // Converter strings para objetos Posicao
                Posicao origemPos = new Posicao(origemStr.charAt(0), Character.getNumericValue(origemStr.charAt(1)));
                Posicao destinoPos = new Posicao(destinoStr.charAt(0), Character.getNumericValue(destinoStr.charAt(1)));

                // Obter as Casas do tabuleiro
                Casa casaOrigem = jogo.getTabuleiro().getCasa(origemPos);
                Casa casaDestino = jogo.getTabuleiro().getCasa(destinoPos);

                // Verificar se a casa de origem tem uma peça
                if (casaOrigem == null || casaOrigem.estaVazia()) {
                    System.out.println("ERRO: Casa de origem vazia ou inválida. Tente novamente.");
                    continue; // Pula para a próxima iteração do loop
                }

                Peca pecaMovida = casaOrigem.getPeca();

                // Verificar se a peça pertence ao jogador atual
                if (pecaMovida.getCor() != jogo.getJogadorAtual().getCor()) {
                    System.out.println("ERRO: Esta peça não pertence ao jogador " + jogo.getJogadorAtual().getCor() + ". Tente novamente.");
                    continue;
                }

                // --- Geração e Validação de Movimentos Legais (AQUI ESTÁ A CHAVE!) ---
                // No futuro, você precisará implementar de forma robusta o método calcularMovimentosLegais()
                // de cada peça. Por enquanto, faremos uma verificação muito simplificada.

                // A linha abaixo chama o método abstrato.
                // Como as implementações de calcularMovimentosLegais nas subclasses de Peca
                // ainda estão vazias, esta lista será vazia.
                List<Jogada> movimentosLegais = pecaMovida.calcularMovimentosLegais(jogo.getTabuleiro(), jogo);

                // Por enquanto, para simular um movimento, vamos criar uma Jogada diretamente.
                // A validação real de se a jogada é "legal" (além das básicas já no jogo.executarJogada)
                // será feita *dentro* de calcularMovimentosLegais de cada Peça.
                // Aqui estamos apenas assumindo que o usuário vai tentar uma jogada que "parece" um movimento,
                // e o Tabuleiro.executarMovimento vai atualizar as casas.
                // O Jogo.executarJogada já faz algumas validações básicas de posse e ocupação.

                Jogada novaJogada = new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, pecaMovida);

                // Tentar executar a jogada
                boolean sucesso = jogo.executarJogada(novaJogada);

                if (sucesso) {
                    System.out.println("Movimento executado com sucesso!");
                    jogo.getTabuleiro().imprimirTabuleiro();
                    // Lógica para verificar fim de jogo (xeque-mate, empate) viria aqui no futuro
                    // (dentro de jogo.executarJogada ou chamada após ela)
                } else {
                    System.out.println("Movimento inválido. Tente novamente.");
                    // jogo.executarJogada() já imprime a razão do erro, mas você pode refinar
                }

            } catch (IllegalArgumentException e) {
                System.out.println("ERRO na posição digitada: " + e.getMessage() + ". Formato esperado 'a1' a 'h8'.");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("------------------------------------");
        System.out.println("Fim de Jogo! Estado: " + jogo.getEstadoJogo());
        scanner.close();
    }
}