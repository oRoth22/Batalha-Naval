package com.mycompany.batalhanaval;

import java.util.Arrays; // importa os arrays
import java.util.Random;
import java.util.Scanner;

public class BatalhaNaval {
    private int[][] tabuleiro;
    private int tamanhoTabuleiro;
    private int[][] navios;
    private int numNavios;
    private boolean jogadorVsJogador;
    public Scanner scanner = new Scanner(System.in);
    public BatalhaNaval(int tamanhoTabuleiro, boolean versus) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.tabuleiro = new int[tamanhoTabuleiro][tamanhoTabuleiro];
        this.numNavios = 10;
        this.navios = new int[][]{{4}, {3, 3}, {2, 2, 2}, {1, 1, 1, 1}};
        this.jogadorVsJogador = versus;

        inicializarTabuleiro();
        posicionarNavios();
    }

    private void inicializarTabuleiro() {
        for (int[] linha : tabuleiro) { //for de uma linha
            Arrays.fill(linha, 0); //função que preenche um array com um determinado valor
        }
    }

    private void posicionarNavios() {

        if (jogadorVsJogador) {
            Scanner scanner = new Scanner(System.in);
            int op = 0;
            
           while(op != 1 && op != 2){
                System.out.println("Deseja alocar os barcos de forma manual ou automatica");
            System.out.println("1 - manual");
            System.out.println("2 - automatico");
            
            op = scanner.nextInt();
            
            if(op == 1){
                posicionarNaviosJogador();
            }
            else if(op == 2){
                posicionarNaviosComputador();
            }
           }
        }else{
            Scanner scanner = new Scanner(System.in);
            int op = 0;
            
           while(op != 1 && op != 2){
                System.out.println("Deseja alocar os barcos de forma manual ou automatica");
            System.out.println("1 - manual");
            System.out.println("2 - automatico");
            
            op = scanner.nextInt();
            
            if(op == 1){
                posicionarNaviosJogador();
            }
            else if(op == 2){
                posicionarNaviosComputador();
            }
           }
        }
        
    }

    private void posicionarNaviosJogador() { //alocar navios manual
        Scanner scanner = new Scanner(System.in);

        for (int[] tamanhoNavios : navios) {
            for (int i = 0; i < tamanhoNavios.length; i++) {
                
                exibirTabuleiro();
                System.out.println("Posicionando navio de tamanho " + tamanhoNavios[i]);

                System.out.print("Informe a linha inicial: ");
                int linhaInicial = scanner.nextInt();

                System.out.print("Informe a coluna inicial: ");
                int colunaInicial = scanner.nextInt();

                System.out.print("Informe a direção (0 para horizontal, 1 para vertical): ");
                int direcao = scanner.nextInt();
                

                boolean posicaoValida = verificarPosicaoValida(linhaInicial, colunaInicial, tamanhoNavios[i], direcao);
                if (!posicaoValida) {
                    System.out.println("Posição inválida! Tente novamente.");
                    i--;
                    break;//continue;
                }

                preencherPosicaoNavio(linhaInicial, colunaInicial, tamanhoNavios[i], direcao);
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        
    }

    private void posicionarNaviosComputador() { //alocar navios automatico
        Random random = new Random();

        for (int[] tamanhoNavios : navios) {
            for (int i = 0; i < tamanhoNavios.length; i++) {
                int linha, coluna, direcao;
                boolean posicaoValida;

                do {
                    linha = random.nextInt(tamanhoTabuleiro);
                    coluna = random.nextInt(tamanhoTabuleiro);
                    direcao = random.nextInt(2); // 0 para horizontal, 1 para vertical

                    posicaoValida = verificarPosicaoValida(linha, coluna, tamanhoNavios[i], direcao);
                } while (!posicaoValida);

                preencherPosicaoNavio(linha, coluna, tamanhoNavios[i], direcao);
            }
        }
    }

    private boolean verificarPosicaoValida(int linha, int coluna, int tamanhoNavio, int direcao) {
        if (direcao == 0) { // horizontal
            if (coluna + tamanhoNavio > tamanhoTabuleiro) {
                return false;
            }

            for (int i = 0; i < tamanhoNavio; i++) {
                if (tabuleiro[linha][coluna + i] != 0) {
                    return false;
                }
            }
        } else { // vertical
            if (linha + tamanhoNavio > tamanhoTabuleiro) {
                return false;
            }

            for (int i = 0; i < tamanhoNavio; i++) {
                if (tabuleiro[linha + i][coluna] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private void preencherPosicaoNavio(int linha, int coluna, int tamanhoNavio, int direcao) {
        if (direcao == 0) { // horizontal
            for (int i = 0; i < tamanhoNavio; i++) {
                tabuleiro[linha][coluna + i] = 1;
            }
        } else { // vertical
            for (int i = 0; i < tamanhoNavio; i++) {
                tabuleiro[linha + i][coluna] = 1;
            }
        }
    }

    public void jogar() {
   
        if (jogadorVsJogador) {
            jogarJogadorVsJogador();
        } else {
            jogarJogadorVsComputador();
        }
    }

    private void jogarJogadorVsJogador() {

        //scanner.nextLine();
        int jogadorAtual = 1;

        while (true) {
            System.out.println("Jogador " + jogadorAtual + ", é sua vez!");
            exibirTabuleiro();

            System.out.print("Informe a linha: ");
            int linha = scanner.nextInt();

            System.out.print("Informe a coluna: ");
            int coluna = scanner.nextInt();

            if (linha < 0 || linha >= tamanhoTabuleiro || coluna < 0 || coluna >= tamanhoTabuleiro) {
                System.out.println("Posição inválida! Tente novamente.");
                continue;
            }

            if (tabuleiro[linha][coluna] == 1) {
                System.out.println("Parabéns! Você acertou um navio!");
                tabuleiro[linha][coluna] = jogadorAtual + 1;
            } else if (tabuleiro[linha][coluna] >= 2) {
                System.out.println("Você já acertou essa posição antes. Tente novamente.");
            } else {
                System.out.println("Você errou. Tente novamente.");
            }

            if (verificarFimJogo()) {
                System.out.println("Parabéns, Jogador " + jogadorAtual + "! Você afundou todos os navios. Fim de jogo!");
                break;
            }

            jogadorAtual = (jogadorAtual == 1) ? 2 : 1; //if de uma linha 
        }

        scanner.close(); //encerra a instância do Scanner, liberando quaisquer recursos associados a ela
    }

    private void jogarJogadorVsComputador() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int jogadorAtual = 1;

        while (true) {
            if (jogadorAtual == 1) {
                System.out.println("Sua vez!");
                exibirTabuleiro();

                System.out.print("Informe a linha: ");
                int linha = scanner.nextInt();

                System.out.print("Informe a coluna: ");
                int coluna = scanner.nextInt();

                if (linha < 0 || linha >= tamanhoTabuleiro || coluna < 0 || coluna >= tamanhoTabuleiro) {
                    System.out.println("Posição inválida! Tente novamente.");
                    continue;
                }

                if (tabuleiro[linha][coluna] == 1) {
                    System.out.println("Parabéns! Você acertou um navio!");
                    tabuleiro[linha][coluna] = 2;
                } else if (tabuleiro[linha][coluna] >= 2) {
                    System.out.println("Você já acertou essa posição antes. Tente novamente.");
                } else {
                    System.out.println("Você errou. Tente novamente.");
                }

                if (verificarFimJogo()) {
                    System.out.println("Parabéns! Você afundou todos os navios. Fim de jogo!");
                    break;
                }
            } else {
                System.out.println("Vez do computador!");
                int linha = random.nextInt(tamanhoTabuleiro);
                int coluna = random.nextInt(tamanhoTabuleiro);

                if (tabuleiro[linha][coluna] == 1) {
                    System.out.println("O computador acertou um navio!");
                    tabuleiro[linha][coluna] = 3;
                } else if (tabuleiro[linha][coluna] == 0) {
                    System.out.println("O computador errou.");
                    tabuleiro[linha][coluna] = 4;
                }

                if (verificarFimJogo()) {
                    System.out.println("O computador afundou todos os navios. Fim de jogo!");
                    break;
                }
            }

            jogadorAtual = (jogadorAtual == 1) ? 2 : 1;
        }
        
        scanner.close();
    }

    private boolean verificarFimJogo() {
        for (int[] linha : tabuleiro) {
            for (int elemento : linha) {
                if (elemento == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void exibirTabuleiro() {
        System.out.println("Tabuleiro:");

        for (int[] linha : tabuleiro) {
            for (int elemento : linha) {
                System.out.print(elemento + " ");
            }
            System.out.println();
        }
    }

    public void iniciarJogoJogadorVsJogador() {
        jogadorVsJogador = true;
        
        jogar();
    }

    public void iniciarJogoJogadorVsComputador() {
        jogadorVsJogador = false;
        jogar();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BatalhaNaval jogo;

        System.out.println("Bem-vindo ao Jogo de Batalha Naval!");
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Jogador vs. Jogador");
        System.out.println("2 - Jogador vs. Computador");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                jogo = new BatalhaNaval(10, true);
                jogo.iniciarJogoJogadorVsJogador();
                break;
            case 2:
                jogo = new BatalhaNaval(10,false);
                jogo.iniciarJogoJogadorVsComputador();
                break;
            default:
                System.out.println("Opção inválida. O jogo será encerrado.");
                break;
        }

        scanner.nextLine();
    }
}