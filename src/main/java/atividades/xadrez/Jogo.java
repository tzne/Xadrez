package atividades.xadrez;
import atividades.xadrez.pecas.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Jogo {
    private Tabuleiro tabuleiro;
    private List<Jogador> jogadores;
    private Jogador jogadorAtual;
    private EstadoJogo estadoJogo;
    private List<Jogada> historicoJogadas;
    private Jogada ultimaJogada;

    /**
     * Construtor padrão para iniciar um novo jogo com as peças na posição inicial.
     */
    public Jogo() {
        this.tabuleiro = new Tabuleiro();
        this.jogadores = new ArrayList<>();
        this.historicoJogadas = new ArrayList<>();
        this.estadoJogo = EstadoJogo.EM_JOGO;
        this.ultimaJogada = null;
        inicializarJogadores();
        posicionarPecasIniciais();
        this.jogadorAtual = getJogador(Cor.BRANCA); // Brancas começam
    }

    /**
     * Construtor para carregar um jogo. Inicializa o tabuleiro e os jogadores,
     * mas não posiciona as peças, deixando o tabuleiro vazio para ser preenchido
     * com os dados de um arquivo.
     * @param carregarJogo Um booleano para diferenciar este construtor.
     */
    public Jogo(boolean carregarJogo) {
        this.tabuleiro = new Tabuleiro();
        this.jogadores = new ArrayList<>();
        this.historicoJogadas = new ArrayList<>();
        // O estado e o jogador atual serão definidos durante o carregamento.
        inicializarJogadores();
    }


    private void inicializarJogadores() {
        jogadores.add(new Jogador(Cor.BRANCA));
        jogadores.add(new Jogador(Cor.PRETA));
    }

    public void setJogadorAtual(Jogador jogador) {
        this.jogadorAtual = jogador;
    }

    /**
     * Define o estado atual do jogo. Usado ao carregar um jogo.
     */
    public void setEstadoJogo(EstadoJogo estado) {
        this.estadoJogo = estado;
    }

    private void posicionarPecasIniciais() {
       
        Jogador branco = getJogador(Cor.BRANCA);
        Jogador preto = getJogador(Cor.PRETA);

        // --- Peças Brancas ---
       
        for (char col = 'a'; col <= 'h'; col++) {
            Peca peao = new Peao(Cor.BRANCA);
            tabuleiro.getCasa(new Posicao(col, 2)).setPeca(peao);
            branco.adicionarPeca(peao);
        }
      
        Peca torreB1 = new Torre(Cor.BRANCA); Peca torreB2 = new Torre(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('a', 1)).setPeca(torreB1); branco.adicionarPeca(torreB1);
        tabuleiro.getCasa(new Posicao('h', 1)).setPeca(torreB2); branco.adicionarPeca(torreB2);
        
        Peca cavaloB1 = new Cavalo(Cor.BRANCA); Peca cavaloB2 = new Cavalo(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('b', 1)).setPeca(cavaloB1); branco.adicionarPeca(cavaloB1);
        tabuleiro.getCasa(new Posicao('g', 1)).setPeca(cavaloB2); branco.adicionarPeca(cavaloB2);
    
        Peca bispoB1 = new Bispo(Cor.BRANCA); Peca bispoB2 = new Bispo(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('c', 1)).setPeca(bispoB1); branco.adicionarPeca(bispoB1);
        tabuleiro.getCasa(new Posicao('f', 1)).setPeca(bispoB2); branco.adicionarPeca(bispoB2);
       
        Peca damaB = new Rainha(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('d', 1)).setPeca(damaB); branco.adicionarPeca(damaB);
        // Rei
        Peca reiB = new Rei(Cor.BRANCA);
        tabuleiro.getCasa(new Posicao('e', 1)).setPeca(reiB); branco.adicionarPeca(reiB);


        // --- Peças Pretas ---

        for (char col = 'a'; col <= 'h'; col++) {
            Peca peao = new Peao(Cor.PRETA);
            tabuleiro.getCasa(new Posicao(col, 7)).setPeca(peao);
            preto.adicionarPeca(peao);
        }
   
        Peca torreP1 = new Torre(Cor.PRETA); Peca torreP2 = new Torre(Cor.PRETA);
        tabuleiro.getCasa(new Posicao('a', 8)).setPeca(torreP1); preto.adicionarPeca(torreP1);
        tabuleiro.getCasa(new Posicao('h', 8)).setPeca(torreP2); preto.adicionarPeca(torreP2);
        
        Peca cavaloP1 = new Cavalo(Cor.PRETA); Peca cavaloP2 = new Cavalo(Cor.PRETA);
        tabuleiro.getCasa(new Posicao('b', 8)).setPeca(cavaloP1); preto.adicionarPeca(cavaloP1);
        tabuleiro.getCasa(new Posicao('g', 8)).setPeca(cavaloP2); preto.adicionarPeca(cavaloP2);
       
        Peca bispoP1 = new Bispo(Cor.PRETA); Peca bispoP2 = new Bispo(Cor.PRETA);
        tabuleiro.getCasa(new Posicao('c', 8)).setPeca(bispoP1); preto.adicionarPeca(bispoP1);
        tabuleiro.getCasa(new Posicao('f', 8)).setPeca(bispoP2); preto.adicionarPeca(bispoP2);
        
        Peca damaP = new Rainha(Cor.PRETA);
        tabuleiro.getCasa(new Posicao('d', 8)).setPeca(damaP); preto.adicionarPeca(damaP);
       
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

    public Jogador getJogador(Cor cor) {
        return jogadores.stream()
                        .filter(j -> j.getCor() == cor)
                        .findFirst()
                        .orElse(null);
    }

    
     // Encontra a casa de uma peça específica no tabuleiro.
    private Casa findCasaDaPeca(Peca peca) {
        for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
            for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                char c = (char) ('a' + j);
                int l = Tabuleiro.TAMANHO - i;
                Casa casa = this.tabuleiro.getCasa(new Posicao(c, l));
                if (casa.getPeca() == peca) {
                    return casa;
                }
            }
        }
        return null;
    }


    /**
     * Verifica se uma determinada casa está sob ataque por qualquer peça da cor atacante.
     */
    public boolean isCasaEmAtaque(Posicao posicaoAlvo, Cor corAtacante) {
        Jogador atacante = getJogador(corAtacante);
        for (Peca peca : atacante.getPecas()) {
            Casa casaDaPeca = findCasaDaPeca(peca);
            if (casaDaPeca == null) continue;

 
            if (peca.getTipo() == TipoPeca.PEAO) {
                int direcao = (peca.getCor() == Cor.BRANCA) ? 1 : -1;
                Posicao posOrigem = casaDaPeca.getPosicao();
                
   
                for (int modColuna : new int[]{-1, 1}) {
                    char novaColunaChar = (char)(posOrigem.getColuna() + modColuna);
                    if (novaColunaChar >= 'a' && novaColunaChar <= 'h') {
                        Posicao posDestinoCaptura = new Posicao(novaColunaChar, posOrigem.getLinha() + direcao);
                        if (posDestinoCaptura.equals(posicaoAlvo)) {
                            return true;
                        }
                    }
                }
            } 
         
            else if (peca.getTipo() == TipoPeca.REI) {
                Posicao posRei = casaDaPeca.getPosicao();
                int diffLinha = Math.abs(posRei.getLinha() - posicaoAlvo.getLinha());
                int diffColuna = Math.abs(posRei.getColuna() - posicaoAlvo.getColuna());
                if (diffLinha <= 1 && diffColuna <= 1) {
                    return true;
                }
            } 
          
            else {
                List<Jogada> movimentos = peca.calcularMovimentosLegais(this.tabuleiro, this);
                for (Jogada jogada : movimentos) {
                    if (jogada.getCasaDestino().getPosicao().equals(posicaoAlvo)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    

   
    private Posicao findPosicaoRei(Cor corRei) {
        Peca rei = getJogador(corRei).getPecas().stream()
            .filter(p -> p.getTipo() == TipoPeca.REI)
            .findFirst().orElse(null);
        
        if (rei != null) {
            Casa casaDoRei = findCasaDaPeca(rei);
            if (casaDoRei != null) {
                return casaDoRei.getPosicao();
            }
        }
        return null; 
    }

    public boolean isReiEmXeque(Cor corRei) {
        Posicao posRei = findPosicaoRei(corRei);
        if (posRei == null) {
            return false; // Rei não está no tabuleiro
        }
        Cor corOponente = (corRei == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
        return isCasaEmAtaque(posRei, corOponente);
    }
    
   
    private List<Jogada> getTodosMovimentosSeguros(Jogador jogador) {
        List<Jogada> movimentosSeguros = new ArrayList<>();
        
        for (Peca peca : jogador.getPecas()) {
            List<Jogada> movimentosDaPeca = peca.calcularMovimentosLegais(tabuleiro, this);
            for (Jogada jogada : movimentosDaPeca) {
               
                Peca pecaCapturada = tabuleiro.executarMovimentoSimulado(jogada);
                
                
                if (!isReiEmXeque(jogador.getCor())) {
                    movimentosSeguros.add(jogada);
                }
                
                
                tabuleiro.desfazerMovimentoSimulado(jogada, pecaCapturada);
            }
        }
        return movimentosSeguros;
    }


     public boolean executarJogada(Jogada jogadaProposta) {
        Peca pecaMovida = jogadaProposta.getPecaMovida();
        
      
        if (jogadaProposta.getJogador() != jogadorAtual) {
            System.out.println("Não é a vez do jogador " + jogadaProposta.getJogador().getCor() + ".");
            return false;
        }
        if (pecaMovida.getCor() != jogadorAtual.getCor()) {
            System.out.println("Você só pode mover suas próprias peças.");
            return false;
        }

        
        List<Jogada> movimentosValidos = getTodosMovimentosSeguros(jogadorAtual);
        
        Jogada jogadaReal = null;
        for (Jogada valida : movimentosValidos) {
            if (valida.getPecaMovida() == pecaMovida && valida.getCasaDestino().equals(jogadaProposta.getCasaDestino())) {
                jogadaReal = valida;
                break;
            }
        }

        if (jogadaReal == null) {
            if (isReiEmXeque(jogadorAtual.getCor())) {
                System.out.println("Movimento inválido. Você deve sair do xeque.");
            } else {
                System.out.println("Movimento inválido para a peça " + pecaMovida.getTipo() + ".");
            }
            return false;
        }

        boolean movimentoSucesso = tabuleiro.executarMovimento(jogadaReal);

        if (movimentoSucesso) {
            historicoJogadas.add(jogadaReal);
            this.ultimaJogada = jogadaReal;

            if (jogadaReal.getPecaCapturada() != null) {
                Jogador adversario = getAdversario();
                adversario.removerPeca(jogadaReal.getPecaCapturada());
            }

            
            Jogador adversario = getAdversario();
            if (isReiEmXeque(adversario.getCor())) {
                List<Jogada> movimentosDeFuga = getTodosMovimentosSeguros(adversario);
                if (movimentosDeFuga.isEmpty()) {
                    estadoJogo = EstadoJogo.XEQUEMATE;
                } else {
                    System.out.println("XEQUE!");
                }
            }
            
            trocarJogadorAtual();
            return true;
        }
        return false;
    }
    
    public Jogador getAdversario() {
        return (jogadorAtual.getCor() == Cor.BRANCA) ? getJogador(Cor.PRETA) : getJogador(Cor.BRANCA);
    }

    private void trocarJogadorAtual() {
        jogadorAtual = getAdversario();
    }
}
