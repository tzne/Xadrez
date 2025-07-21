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


public class Gerenciador {

    public static void main(String[] args) {
        System.out.println("=== Simulador de Jogo de Xadrez ===");

        // 1. Inicializar o Jogo
        Jogo jogo = new Jogo();
        Scanner scanner = new Scanner(System.in);

        // Loop principal do jogo
        while (jogo.getEstadoJogo() == EstadoJogo.EM_JOGO) {
            jogo.getTabuleiro().imprimirTabuleiro();
            System.out.println("------------------------------------");
            System.out.println("Vez do " + jogo.getJogadorAtual().getCor() + ".");

            System.out.print("Digite a casa de origem (ex: e2): ");
            String origemStr = scanner.nextLine().toLowerCase();
            System.out.print("Digite a casa de destino (ex: e4): ");
            String destinoStr = scanner.nextLine().toLowerCase();

            try {
                // Validar o formato da entrada
                if(origemStr.length() != 2 || destinoStr.length() != 2) {
                     System.out.println("ERRO: Formato de posição inválido. Use duas letras (ex: a1).");
                     continue;
                }
                
                Posicao origemPos = new Posicao(origemStr.charAt(0), Character.getNumericValue(origemStr.charAt(1)));
                Posicao destinoPos = new Posicao(destinoStr.charAt(0), Character.getNumericValue(destinoStr.charAt(1)));

                Casa casaOrigem = jogo.getTabuleiro().getCasa(origemPos);
                Casa casaDestino = jogo.getTabuleiro().getCasa(destinoPos);

                if (casaOrigem == null || casaDestino == null) {
                    System.out.println("ERRO: Posição fora do tabuleiro. Tente novamente.");
                    continue;
                }
                
                if (casaOrigem.estaVazia()) {
                    System.out.println("ERRO: Casa de origem vazia. Tente novamente.");
                    continue; 
                }

                Peca pecaMovida = casaOrigem.getPeca();
                
                Jogada novaJogada = new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, pecaMovida);

                boolean sucesso = jogo.executarJogada(novaJogada);

                if (sucesso) {
                    System.out.println("Movimento executado com sucesso!");
                } else {
                    System.out.println("Tente novamente.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("ERRO na posição digitada: " + e.getMessage() + ". Formato esperado 'a1' a 'h8'.");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("------------------------------------");
        jogo.getTabuleiro().imprimirTabuleiro();
        if(jogo.getEstadoJogo() == EstadoJogo.XEQUEMATE) {
            System.out.println("Fim de Jogo! XEQUE-MATE!");
            System.out.println("Vencedor: " + jogo.getJogadorAtual().getCor());
        } else {
             System.out.println("Fim de Jogo! Estado: " + jogo.getEstadoJogo());
        }
        scanner.close();
    }
}