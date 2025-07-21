package atividades.xadrez;

public enum EstadoJogo {
    EM_JOGO,                // O jogo está em andamento
    XEQUEMATE,              // Um jogador foi colocado em xeque-mate
    EMPATE_AFOGAMENTO,      // Empate por afogamento
    EMPATE_MATERIAL,        // Empate por material insuficiente para dar mate
    EMPATE_REPETICAO,       // Empate por repetição de movimentos
    EMPATE_50_MOVIMENTOS    // Empate por 50 movimentos sem captura ou movimento de peão (não está no diagrama, mas comum)
}
