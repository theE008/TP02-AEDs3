package atuacoes;

import java.util.Scanner;

import atores.Ator;
import atores.*;
import series.*;


public class MenuAtuacao {

    ArquivoAtor arqAtores = new ArquivoAtor();
    ArquivoAtuacao arqAtuacao = new ArquivoAtuacao();
    
    ArquivoSeries arqSeries = new ArquivoSeries();

    private static Scanner console = new Scanner(System.in);

    public MenuAtuacao() throws Exception {
        arqSeries = new ArquivoSeries();
        arqAtores = new ArquivoAtor();
    }

  public void menu(String servico) throws Exception {
    int opcao;
    do {
        System.out.println("\n\n" + servico);
        System.out.println("-----------");
        System.out.println("> Início > Atores");
        System.out.println("1) Buscar");
        System.out.println("2) Alterar");
        System.out.println("3) Excluir");
        System.out.println("4) Mostrar todos as series de um ator");
        System.out.println("0) Retornar ao menu anterior");

        System.out.print("\nOpção: ");
        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            opcao = -1;
        }

        switch (opcao) {
            case 1:
                buscarAtor();
                break;
            case 2:
                alterarAtor();
                break;
            case 3:
                excluirAtor();
                break;
            case 4:
                mostrarSeriesDoAtores();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    } while (opcao != 0);
}


    public void incluirAtores(int idSerie) throws Exception {
        System.out.println("\nInclusão de Atores");
        System.out.println();

        String nome = "";

        // Nome
        do {
            System.out.print("Nome do Ator (min. 2 letras): ");
            nome = console.nextLine();
        } while (nome.length() < 2);
        
        System.out.print("\nConfirma a inclusão do Ator na série? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                Ator at = new Ator(nome);
                int idAtor = arqAtores.create(at);
                Atuacao Atuacao = new Atuacao(idAtor, idSerie);
                arqAtuacao.create(Atuacao);
                System.out.println("Ator incluído com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro ao incluir Ator. " + e.getMessage());
            }
        }
    }

    public void buscarAtor() {
        System.out.println("\nBusca de Atores: ");
        boolean dadosCorretos = false;
        System.out.println();
        do{
            try {
                System.out.print("Digite o nome do ator: ");
                String nomeAtor = console.nextLine();
                Ator[] atores = arqAtores.readNome(nomeAtor);
                if(atores != null && atores.length > 0){
                    for (Ator at : atores) {
                        mostraAtor(at);
                        dadosCorretos = true;
                    }
                }else{
                    System.out.println("Nenhum ator encontrado.");
                    dadosCorretos = true;
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar o ator: " + e.getMessage());
            }

        }while(!dadosCorretos);
    }

    //Atualizar Ator pelo nome
    public void alterarAtor() throws Exception {
        System.out.println("\nAlteração de Atores");

        System.out.print("Nome de ator: ");
        String nome = console.nextLine();
        System.out.println();

        try {
            Ator[] atores = arqAtores.readNome(nome);
            if (atores != null) {
                
                for (int i=0; i < atores.length; i++) {
                    System.out.println("\t[" + i + "]");
                    mostraAtor(atores[i]);
                }

                System.out.print("Digite o número do ator a ser atualizado: ");
                int num = console.nextInt();
                console.nextLine();

                //testar se o numero digitado e' valido
                if (num >= 0 && atores[num] != null) {

                    //------------- Dados a serem atualizados ----------------//
                    System.out.print("Novo nome (ou Enter para manter): ");
                    String novoNome = console.nextLine();
                    if (!novoNome.isEmpty()) {
                        atores[num].setNome(novoNome);
                    }

                    System.out.print("\nConfirma as alterações? (S/N) ");
                    char resp = console.nextLine().charAt(0);

                    if (resp == 'S' || resp == 's') {
                        boolean alterado = arqAtores.update(atores[num]);
                        if (alterado) {
                            System.out.println("Ator alterada com sucesso.");
                        } else {
                            System.out.println("Erro ao alterar a Ator.");
                        }
                    } else {
                        System.out.println("Alterações canceladas.");
                    }
                } else {
                    System.out.println("Não há Ator associada a esse número.");
                }
            } else {
                System.out.println("Ator não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar Ator.");
        }
    }

    //Excluir Ator pelo nome
    public void excluirAtor() throws Exception {
        System.out.println("\nExclusão de Ator");
        
        System.out.print("Nome da Ator: ");
        String nome = console.nextLine();
        System.out.println();

        try {
            Ator[] atores = arqAtores.readNome(nome);
            if (atores != null && atores.length > 0) {
                for (int i=0; i < atores.length; i++) {
                    System.out.println("\t[" + i + "]");
                    mostraAtor(atores[i]);
                }

                System.out.print("Digite o número do atores a ser excluído: ");
                int num = console.nextInt();
                console.nextLine();
                if(num < 0 || num > atores.length || atores[num] == null){
                    System.err.println("Número inválido!");
                    return;
                }

                System.out.print("Tem certeza que deseja excluir esse ator? (S/N) ");
                char resposta = console.nextLine().charAt(0);
                if (resposta != 'S' && resposta != 's') {
                    System.out.println("O ator não foi excluída.");
                    return;
                } 

                boolean excluido = arqAtores.delete(atores[num].getID());
                if (excluido) {
                    System.out.println("ator excluída com sucesso.");
                } else {
                    System.out.println("Erro ao ator.");
                }

            } else {
                System.out.println("Ator não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir ator.");
        }
    }






    public void mostrarSeriesDoAtores(){
        System.out.println("\nBusca de Series de um ator:");
        System.out.print("De qual ator deseja buscar as Series? (Nome do ator): ");
        
        String nomeAtorVinculado = console.nextLine();
        System.out.println();
        boolean dadosCorretos = false;
        
        do {
            try {
                Ator[] atores = arqAtores.readNome(nomeAtorVinculado);
                
                if (atores != null && atores.length > 0) {
                    System.out.println("Séries encontradas:");
                    for (int i = 0; i < atores.length; i++) {
                        System.out.print("[" + i + "] ");
                        mostraAtor(atores[i]);
                    }
                    
                    System.out.print("\nDigite o número do ator escolhida: ");
                    if (console.hasNextInt()) {
                        int num = console.nextInt();
                        console.nextLine(); // Limpar buffer
                        
                        if (num < 0 || num >= atores.length || atores[num] == null) {
                            System.err.println("Número inválido!");
                        } else {
                            System.out.println("\nSeries do ator " + atores[num].getNome() + ":");
                            Serie[] series = arqAtores.readSerieDoAtor(atores[num].getID());
                            
                            if (series != null && series.length > 0) {
                                for (Serie se : series) {
                                    System.out.println();
                                    mostraSerie(se);

                                    Atuacao[] Atuacao = arqAtuacao.read(atores[num].getID(), se.getID());
                                    if (Atuacao != null && Atuacao.length > 0) {
                                        System.out.println("Fazendo o papel de: ");
                                        for(Atuacao at : Atuacao) {
                                            mostraAtuacao(at);
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Nenhum série encontrado para esta ator.");
                            }
                            dadosCorretos = true;
                        }
                    } else {
                        System.err.println("Entrada inválida! Digite um número válido.");
                        console.nextLine(); // Limpar buffer
                    }
                } else {
                    System.out.println("Nenhum série encontrada para esse ator.");
                    dadosCorretos = true;
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar séries de um ator: " + e.getMessage());
                dadosCorretos = true;
            }
        } while (!dadosCorretos);
    }


    //Mostrar Ator
    public void mostraAtor(Ator ator) {
        if (ator != null) {
            System.out.println("----------------------");
            System.out.printf("Nome....: %s%n", ator.getNome());
            System.out.println("----------------------");
        }

    }

    //Mostrar Papel
    public void mostraAtuacao(Atuacao Atuacao) {
        if (Atuacao != null) {
            System.out.println("----------------------");
            System.out.println("----------------------");
        }

    }

    //Mostrar Série
    public void mostraSerie(Serie serie) {
        if (serie != null) {
            System.out.println("----------------------");
            System.out.printf("Nome....: %s%n", serie.getNome());
            System.out.printf("Ano lançamento: %d%n", serie.getAnoLancamento().getYear());
            System.out.printf("Sinopse....: %s%n", serie.getSinopse());
            System.out.printf("Streaming.....: %s%n", serie.getStreaming());
            System.out.printf("Gênero.....: %s%n", serie.getGenero());
            System.out.println("----------------------");
        }
    }


    public void povoar() throws Exception {

        // fazer dps

        System.out.println ("Fazer dps");

    }
}
