/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez;

/**
 *
 * @author ruama
*/
public enum EstadoJogo {
    EM_JOGO,         // O jogo está em andamento
    XEQUEMATE,       // Um jogador foi colocado em xeque-mate
    EMPATE_AFOGAMENTO, // Empate por afogamento (Stalemate)
    EMPATE_MATERIAL,   // Empate por material insuficiente para dar mate (ex: Rei e Bispo vs Rei)
    EMPATE_REPETICAO,  // Empate por repetição de movimentos (não está no diagrama, mas comum)
    EMPATE_50_MOVIMENTOS // Empate por 50 movimentos sem captura ou movimento de peão (não está no diagrama, mas comum)
}
