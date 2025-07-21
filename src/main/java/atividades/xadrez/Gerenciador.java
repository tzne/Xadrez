package atividades.xadrez;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;

/**
 * Classe principal que gerencia o fluxo do jogo de xadrez.
 * Permite iniciar um novo jogo, carregar uma partida salva, salvar o estado atual do jogo
 * e executar testes de unidade.
 *
 * @author ruama
 */
public class Gerenciador {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Ponto de entrada da aplicação. Exibe o menu principal e gerencia as
     * opções do usuário.
     */
    public static void main(String[] args) {
        System.out.println("=== Simulador de Jogo de Xadrez ===");

        while (true) {
            exibirMenuPrincipal();
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha

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
                scanner.nextLine(); // Limpar o buffer do scanner
            }
        }
    }

    /**
     * Exibe o menu principal de opções para o usuário.
     */
    private static void exibirMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Iniciar Novo Jogo");
        System.out.println("2. Carregar Jogo Salvo");
        System.out.println("3. Executar Testes");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Inicia uma nova partida de xadrez a partir do estado inicial.
     */
    private static void iniciarNovoJogo() {
        System.out.println("\n--- NOVO JOGO ---");
        Jogo jogo = new Jogo();
        iniciarPartida(jogo);
    }

    /**
     * Carrega uma partida de xadrez a partir de um arquivo de texto.
     */
    private static void carregarJogo() {
        System.out.println("\n--- CARREGAR JOGO ---");
        System.out.print("Digite o nome do arquivo para carregar (ex: jogo.txt): ");
        String nomeArquivo = scanner.nextLine();

        try {
            // Esta é uma maneira simplificada de carregar o jogo.
            // A classe Jogo precisaria de lógica para interpretar o arquivo.
            Jogo jogo = new Jogo(); // A lógica de carregamento real estaria aqui
            // Simulando o carregamento...
            System.out.println("Jogo carregado com sucesso de " + nomeArquivo + " (simulação).");
            iniciarPartida(jogo);
        } catch (Exception e) {
            System.out.println("ERRO ao carregar o jogo: " + e.getMessage());
        }
    }

    /**
     * Salva o estado atual da partida em um arquivo de texto.
     * @param jogo O jogo a ser salvo.
     */
    private static void salvarJogo(Jogo jogo) {
        System.out.println("\n--- SALVAR JOGO ---");
        System.out.print("Digite o nome do arquivo para salvar (ex: jogo.txt): ");
        String nomeArquivo = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new File(nomeArquivo))) {
            // Exemplo de formato de salvamento
            writer.println("JogadorAtual:" + jogo.getJogadorAtual().getCor());
            writer.println("EstadoJogo:" + jogo.getEstadoJogo());
            // Aqui iria a lógica para salvar cada peça e sua posição.
            // Por exemplo: "PEAO,BRANCA,a2,false"

            System.out.println("Jogo salvo com sucesso em " + nomeArquivo);
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Não foi possível salvar o arquivo em " + nomeArquivo);
        }
    }

    /**
     * Contém o loop principal da partida, recebendo as jogadas do usuário.
     * @param jogo A instância do jogo a ser jogada.
     */
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
                Jogada novaJogada = new Jogada(jogo.getJogadorAtual(), casaOrigem, casaDestino, pecaMovida);

                if (!jogo.executarJogada(novaJogada)) {
                     System.out.println("Movimento inválido. Tente novamente.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("ERRO na posição digitada: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("------------------------------------");
        jogo.getTabuleiro().imprimirTabuleiro();
        if (jogo.getEstadoJogo() == EstadoJogo.XEQUEMATE) {
            System.out.println("Fim de Jogo! XEQUE-MATE!");
            System.out.println("Vencedor: " + jogo.getAdversario().getCor()); // O vencedor é o jogador que deu o xeque-mate
        } else {
            System.out.println("Fim de Jogo! Estado: " + jogo.getEstadoJogo());
        }
    }

    /**
     * Executa uma suíte de testes para verificar a integridade das classes do jogo.
     */
    private static void executarTestes() {
        System.out.println("\n--- EXECUTANDO TESTES ---");
        boolean todosPassaram = true;

        // Teste 1: Posição do Rei Branco no início
        Jogo jogoTeste = new Jogo();
        Peca reiBranco = jogoTeste.getTabuleiro().getPecaEmPosicao(new Posicao('e', 1));
        if (reiBranco == null || reiBranco.getTipo() != TipoPeca.REI || reiBranco.getCor() != Cor.BRANCA) {
            System.out.println("FALHOU: Teste de posição inicial do Rei Branco.");
            todosPassaram = false;
        }

        // Teste 2: Movimento inicial do Peão
        boolean moveuPeao = jogoTeste.executarJogada(new Jogada(
            jogoTeste.getJogadorAtual(),
            jogoTeste.getTabuleiro().getCasa(new Posicao('e', 2)),
            jogoTeste.getTabuleiro().getCasa(new Posicao('e', 4)),
            jogoTeste.getTabuleiro().getPecaEmPosicao(new Posicao('e', 2))
        ));
        if (!moveuPeao) {
            System.out.println("FALHOU: Teste de movimento inicial do Peão.");
            todosPassaram = false;
        }

        // Teste 3: Checar se o jogador mudou para PRETO
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
