package atividades.xadrez;

import atividades.xadrez.pecas.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;

// --- IMPORTAÇÕES CORRIGIDAS E ADICIONADAS ---
import atividades.xadrez.Jogo;
import atividades.xadrez.Cor;
import atividades.xadrez.EstadoJogo;
import atividades.xadrez.TipoPeca;
import atividades.xadrez.Posicao;
import atividades.xadrez.Casa;
import atividades.xadrez.Peca;
import atividades.xadrez.Jogada;


/**
 * Classe principal que gerencia o fluxo do jogo de xadrez.
 * Permite iniciar um novo jogo, carregar uma partida salva, salvar o estado atual do jogo
 * e executar testes de unidade.
 *
 * @author ruama
 */
public class Gerenciador {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Simulador de Jogo de Xadrez ===");

        while (true) {
            exibirMenuPrincipal();
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine(); 

                switch (escolha) {
                    case 1:
                        iniciarNovoJogo();
                        break;
                    case 2:
                        carregarJogo();
                        break;
                    case 3:
                        executarTestes();
                        break;
                    case 4:
                        System.out.println("Obrigado por jogar. Até mais!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção entre 1 e 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); 
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Iniciar Novo Jogo");
        System.out.println("2. Carregar Jogo Salvo");
        System.out.println("3. Executar Testes");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void iniciarNovoJogo() {
        System.out.println("\n--- NOVO JOGO ---");
        Jogo jogo = new Jogo();
        iniciarPartida(jogo);
    }

    private static void carregarJogo() {
        System.out.println("\n--- CARREGAR JOGO ---");
        System.out.print("Digite o nome do arquivo para carregar (ex: jogo.txt): ");
        String nomeArquivo = scanner.nextLine();
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            System.out.println("ERRO: O arquivo '" + nomeArquivo + "' não foi encontrado.");
            return;
        }

        try (Scanner fileScanner = new Scanner(arquivo)) {
            Jogo jogo = new Jogo(true); 

            if (fileScanner.hasNextLine()) {
                String linhaJogador = fileScanner.nextLine();
                Cor corJogador = Cor.valueOf(linhaJogador.split(":")[1]);
                jogo.setJogadorAtual(jogo.getJogador(corJogador));
            }
            if (fileScanner.hasNextLine()) {
                String linhaEstado = fileScanner.nextLine();
                EstadoJogo estado = EstadoJogo.valueOf(linhaEstado.split(":")[1]);
                jogo.setEstadoJogo(estado);
            }

            while (fileScanner.hasNextLine()) {
                String linhaPeca = fileScanner.nextLine();
                String[] partes = linhaPeca.split(",");

                TipoPeca tipo = TipoPeca.valueOf(partes[0]);
                Cor cor = Cor.valueOf(partes[1]);
                Posicao pos = new Posicao(partes[2].charAt(0), Integer.parseInt(partes[2].substring(1)));
                boolean jaMoveu = Boolean.parseBoolean(partes[3]);

                Peca peca = criarPeca(tipo, cor);
                peca.setJaMoveu(jaMoveu);

                jogo.getTabuleiro().getCasa(pos).setPeca(peca);
                jogo.getJogador(cor).adicionarPeca(peca);
            }

            System.out.println("Jogo carregado com sucesso de '" + nomeArquivo + "'.");
            iniciarPartida(jogo);

        } catch (FileNotFoundException e) {
            System.out.println("ERRO: O arquivo '" + nomeArquivo + "' não foi encontrado.");
        } catch (Exception e) {
            System.out.println("ERRO ao carregar o jogo: Ocorreu um problema ao ler o arquivo.");
            e.printStackTrace();
        }
    }

    private static Peca criarPeca(TipoPeca tipo, Cor cor) {
        switch (tipo) {
            case REI: return new Rei(cor);
            case RAINHA: return new Rainha(cor);
            case TORRE: return new Torre(cor);
            case BISPO: return new Bispo(cor);
            case CAVALO: return new Cavalo(cor);
            case PEAO: return new Peao(cor);
            default: throw new IllegalArgumentException("Tipo de peça desconhecido: " + tipo);
        }
    }

    private static void salvarJogo(Jogo jogo) {
        System.out.println("\n--- SALVAR JOGO ---");
        System.out.print("Digite o nome do arquivo para salvar (ex: jogo.txt): ");
        String nomeArquivo = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new File(nomeArquivo))) {
            writer.println("JogadorAtual:" + jogo.getJogadorAtual().getCor());
            writer.println("EstadoJogo:" + jogo.getEstadoJogo());

            for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
                for (int j = 0; j < Tabuleiro.TAMANHO; j++) {
                    char c = (char) ('a' + j);
                    int l = Tabuleiro.TAMANHO - i;
                    Posicao pos = new Posicao(c, l);
                    Peca peca = jogo.getTabuleiro().getPecaEmPosicao(pos);

                    if (peca != null) {
                        writer.println(
                            peca.getTipo() + "," +
                            peca.getCor() + "," +
                            pos.toString() + "," +
                            peca.jaMoveu()
                        );
                    }
                }
            }
            System.out.println("Jogo salvo com sucesso em '" + nomeArquivo + "'.");
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Não foi possível salvar o arquivo em '" + nomeArquivo + "'.");
        }
    }
    
    private static void iniciarPartida(Jogo jogo) {
        while (jogo.getEstadoJogo() == EstadoJogo.EM_JOGO) {
            jogo.getTabuleiro().imprimirTabuleiro();
            System.out.println("------------------------------------");
            System.out.println("Vez do " + jogo.getJogadorAtual().getCor() + ".");
            if (jogo.isReiEmXeque(jogo.getJogadorAtual().getCor())) {
                System.out.println("ATENÇÃO: Seu rei está em XEQUE!");
            }

            System.out.println("Digite a jogada (ex: e2 e4), ou 'salvar' para salvar e sair.");
            System.out.print("Sua jogada: ");
            String entrada = scanner.nextLine().toLowerCase();

            if ("salvar".equals(entrada)) {
                salvarJogo(jogo);
                break;
            }

            String[] partes = entrada.split(" ");
            if (partes.length != 2) {
                System.out.println("ERRO: Formato de jogada inválido. Use 'origem destino', por exemplo: a2 a4.");
                continue;
            }

            String origemStr = partes[0];
            String destinoStr = partes[1];

            try {
                if (origemStr.length() != 2 || destinoStr.length() != 2) {
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
                Peca pecaCapturada = casaDestino.getPeca();
                Jogada novaJogada;
                
                int rankPromocao = (pecaMovida.getCor() == Cor.BRANCA) ? 8 : 1;
                boolean isPromotion = (pecaMovida.getTipo() == TipoPeca.PEAO && destinoPos.getLinha() == rankPromocao);

                if (isPromotion) {
                    TipoPeca pecaPromovida = solicitarPecaPromocao();
                    novaJogada = new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, pecaMovida, pecaCapturada, false, false, pecaPromovida);
                } else {
                    novaJogada = new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, pecaMovida, pecaCapturada, false, false, null);
                }

                if (!jogo.executarJogada(novaJogada)) {
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
            // O vencedor é o jogador que deu o xeque-mate, ou seja, o jogador atual no momento do xeque-mate
            System.out.println("Vencedor: " + jogo.getJogadorAtual().getCor());
        } else {
             System.out.println("Fim de Jogo! Estado: " + jogo.getEstadoJogo());
        }
    }

    private static TipoPeca solicitarPecaPromocao() {
        while (true) {
            System.out.print("PROMOÇÃO! Escolha a peça (Q - Rainha, R - Torre, B - Bispo, N - Cavalo): ");
            String escolha = scanner.nextLine().toUpperCase();
            switch (escolha) {
                case "Q": return TipoPeca.RAINHA;
                case "R": return TipoPeca.TORRE;
                case "B": return TipoPeca.BISPO;
                case "N": return TipoPeca.CAVALO;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void executarTestes() {
        System.out.println("\n--- EXECUTANDO TESTES ---");
        boolean todosPassaram = true;
        Jogo jogoTeste = new Jogo();
        Peca reiBranco = jogoTeste.getTabuleiro().getPecaEmPosicao(new Posicao('e', 1));
        if (reiBranco == null || reiBranco.getTipo() != TipoPeca.REI || reiBranco.getCor() != Cor.BRANCA) {
            System.out.println("FALHOU: Teste de posição inicial do Rei Branco.");
            todosPassaram = false;
        }
        
        Jogada jogadaTeste = new Jogada(
            jogoTeste.getJogadorAtual(),
            jogoTeste.getTabuleiro().getCasa(new Posicao('e', 2)),
            jogoTeste.getTabuleiro().getCasa(new Posicao('e', 4)),
            jogoTeste.getTabuleiro().getPecaEmPosicao(new Posicao('e', 2))
        );
        
        boolean moveuPeao = jogoTeste.executarJogada(jogadaTeste);
        
        if (!moveuPeao) {
            System.out.println("FALHOU: Teste de movimento inicial do Peão.");
            todosPassaram = false;
        }

        if (jogoTeste.getJogadorAtual().getCor() != Cor.PRETA) {
            System.out.println("FALHOU: Teste de troca de jogador.");
            todosPassaram = false;
        }
        
        if (todosPassaram) {
            System.out.println("SUCESSO: Todos os testes foram concluídos com êxito.");
        } else {
            System.out.println("FALHA: Um ou mais testes não passaram.");
        }
        System.out.println("------------------------");
    }
}
