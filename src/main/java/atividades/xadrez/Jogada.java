package atividades.xadrez;

public class Jogada {
    private Jogador jogador;
    private Casa casaOrigem;
    private Casa casaDestino;
    private Peca pecaMovida;
    private Peca pecaCapturada;
    private boolean isRoque;
    private boolean isEnPassant;
    private TipoPeca pecaPromovida;

   
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


    public Jogada(Jogador jogador, Casa casaOrigem, Casa casaDestino, Peca pecaMovida,
                  Peca pecaCapturada, boolean isRoque, boolean isEnPassant, TipoPeca pecaPromovida) {
        this(jogador, casaOrigem, casaDestino, pecaMovida);
        this.pecaCapturada = pecaCapturada;
        this.isRoque = isRoque;
        this.isEnPassant = isEnPassant;
        this.pecaPromovida = pecaPromovida;
    }

   
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

    public void setPecaCapturada(Peca pecaCapturada) {
        this.pecaCapturada = pecaCapturada;
    }

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
