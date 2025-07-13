/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package atividades.xadrez;

/**
 *
 * @author ruama
 */
public class Jogada {
    private Jogador jogador;          // O jogador que está fazendo a jogada
    private Casa casaOrigem;          // A casa de onde a peça se moveu
    private Casa casaDestino;         // A casa para onde a peça se moveu
    private Peca pecaMovida;          // A peça que foi movida
    private Peca pecaCapturada;       // A peça que foi capturada (null se não houver captura)
    private boolean isRoque;          // Indica se a jogada é um roque
    private boolean isEnPassant;      // Indica se a jogada é um en passant
    private TipoPeca pecaPromovida;   // O tipo de peça para qual um peão foi promovido (null se não houver promoção)

    // Construtor mínimo para uma jogada simples
    public Jogada(Jogador jogador, Casa casaOrigem, Casa casaDestino, Peca pecaMovida) {
        this.jogador = jogador;
        this.casaOrigem = casaOrigem;
        this.casaDestino = casaDestino;
        this.pecaMovida = pecaMovida;
        this.pecaCapturada = null;
        this.isRoque = false;
        this.isEnPassant = false;
        this.pecaPromovida = null;
    }

    // Construtor para jogadas mais complexas
    public Jogada(Jogador jogador, Casa casaOrigem, Casa casaDestino, Peca pecaMovida,
                  Peca pecaCapturada, boolean isRoque, boolean isEnPassant, TipoPeca pecaPromovida) {
        this(jogador, casaOrigem, casaDestino, pecaMovida); // Chama o construtor mínimo
        this.pecaCapturada = pecaCapturada;
        this.isRoque = isRoque;
        this.isEnPassant = isEnPassant;
        this.pecaPromovida = pecaPromovida;
    }

    // --- Getters para todos os atributos ---
    public Jogador getJogador() {
        return jogador;
    }

    public Casa getCasaOrigem() {
        return casaOrigem;
    }

    public Casa getCasaDestino() {
        return casaDestino;
    }

    public Peca getPecaMovida() {
        return pecaMovida;
    }

    public Peca getPecaCapturada() {
        return pecaCapturada;
    }

    public boolean isRoque() {
        return isRoque;
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public TipoPeca getPecaPromovida() {
        return pecaPromovida;
    }

    // --- Métodos Setter (se você precisar modificar a jogada após criada, mas geralmente são imutáveis) ---
    // Exemplo:
    public void setPecaCapturada(Peca pecaCapturada) {
        this.pecaCapturada = pecaCapturada;
    }

    // Você pode adicionar outros setters se a jogada for construída em etapas.
    // No entanto, para fins de registro e validação, é comum que Jogada seja imutável após criada.

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(pecaMovida.getTipo()).append(" (").append(pecaMovida.getCor()).append(")");
        sb.append(" de ").append(casaOrigem.getPosicao());
        sb.append(" para ").append(casaDestino.getPosicao());

        if (pecaCapturada != null) {
            sb.append(", captura ").append(pecaCapturada.getTipo());
        }
        if (isRoque) {
            sb.append(", Roque!");
        }
        if (isEnPassant) {
            sb.append(", En Passant!");
        }
        if (pecaPromovida != null) {
            sb.append(", Promovido para ").append(pecaPromovida);
        }
        return sb.toString();
    }
}