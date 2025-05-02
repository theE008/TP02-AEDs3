//////////////////////////////////////////////////
// PACOTE

package atuacao;

//////////////////////////////////////////////////
// BIBLIOTECAS DO SISTEMA

import java.time.LocalDate;
import java.util.Scanner;

//////////////////////////////////////////////////
// BIBLIOTECAS PRÓPRIAS

import aeds3.*;
import episodios.*;
import series.*;

//////////////////////////////////////////////////
// CLASSE MENUS

public class MenuAtuacoes 
{
    ArquivoAtuacoes arqAtuacoes = new ArquivoAtuacoes ();
    ArquivoAtores arqAtores = new ArquivoAtores ();
    private static Scanner console = new Scanner(System.in);
    MenuEpisodios menuEpisodio = new MenuEpisodios(); 

    public MenuAtuacoes () throws Exception 
    {
        arqAtuacoes = new ArquivoAtuacoes ();
        arqAtores = new ArquivoAtores ();
    }

    public void menu(String servico) throws Exception 
    {
        int opcao;
        do 
        {
            System.out.println("\n\n" + servico);
            System.out.println("-----------");
            System.out.println("> Início > Atores");
            System.out.println("\n1) Incluir");
            System.out.println("2) Buscar");
            System.out.println("3) Alterar");
            System.out.println("4) Excluir");
            System.out.println("5) Mostrar todos os atores de uma série");
            System.out.println("6) Mostrar todas as séries de um ator");
            System.out.println("0) Retornar ao menu anterior");

            System.out.print("\nOpção: ");
            try 
            {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) 
            {
                case 1:
                    incluirAtuacao();
                    break;
                case 2:
                    buscarSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 5:
                    mostrarEpSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void incluirAtuacao() 
    {
        System.out.println("\nInclusão de Atuações");

        // DAQUI PRA BAIXO, NADA FEITO

        String nome, sinopse, streaming, genero;
        short classificacaoIndicativa;
        LocalDate ano = null;
        int anoAtual = LocalDate.now().getYear();

        do 
        {
            System.out.print("Nome da Atuações (min. 4 letras): ");
            nome = console.nextLine();
        } while (nome.length() < 4);
        do 
        {
            System.out.print("Gênero da Atuações (min. 4 letras): ");
            genero = console.nextLine();
        } while (genero.length() < 4);

        System.out.print("Classificação indicativa da Atuações: ");
        classificacaoIndicativa = console.nextShort();

        boolean anoValido = false;
        do 
        {
            System.out.print("Ano de lançamento (entre 1900 e " + anoAtual + "): ");
            try 
            {
                int anoDigitado = Integer.parseInt(console.nextLine());
                if (anoDigitado >= 1900 && anoDigitado <= anoAtual) 
                {
                    ano = LocalDate.of(anoDigitado, 1, 1);
                    anoValido = true;
                } else 
                {
                    System.err.println("Ano inválido! Insira um ano entre 1900 e " + anoAtual + ".");
                }
            } catch (NumberFormatException e) 
            {
                System.err.println("Ano inválido! Insira um valor numérico.");
            }
        } while (!anoValido);

        
        do 
        {
            System.out.print("Sinopse (min. 10 letras): ");
            sinopse = console.nextLine();
        } while (sinopse.length() < 10);

        
        do 
        {
            System.out.print("Streaming (min. 3 letras): ");
            streaming = console.nextLine();
        } while (streaming.length() < 3);

        System.out.print("\nConfirma a inclusão da Atuações? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') 
        {
            try 
            {
                Serie s = new Serie(nome, ano, sinopse, streaming, genero, classificacaoIndicativa);
                arqAtuacoes.create(s);
                System.out.println("Atuações incluída com sucesso.");
            } catch (Exception e) 
            {
                System.out.println("Erro ao incluir Atuações.");
            }
        }
    }

    
    public void buscarSerie() 
    {
        System.out.println("\nBusca de Atuações");

        System.out.print("Nome da Atuações: ");
        String nome = console.nextLine();

        System.out.println();
        try 
        {
            Serie[] series = arqAtuacoes.readNome(nome);
            if (series != null && series.length > 0) 
            {
                System.out.println("Atuaçõess encontradas:");
                for (Serie s : series) 
                {
                    mostrarSerie(s);
                }
            } else 
            {
                System.out.println("Nenhuma Atuações encontrada com esse nome.");
            }
            
        } catch (Exception e) 
        {
            System.out.println("Erro ao buscar Atuações.");
        }
    }


    public void alterarSerie() throws Exception 
    {
        System.out.println("\nAlteração de Atuações");

        System.out.print("Nome da Atuações: ");
        String nome = console.nextLine();
        System.out.println();

        try 
        {
            Serie[] serie = arqAtuacoes.readNome(nome);
            if (serie != null) 
            {
                
                for (int i=0; i < serie.length; i++) 
                {
                    System.out.println("\t[" + i + "]");
                    mostrarSerie(serie[i]);
                }

                System.out.print("Digite o número da Atuações a ser atualizada: ");
                int num = console.nextInt();
                console.nextLine();

                
                if (num >= 0 && serie[num] != null) 
                {
                    System.out.print("Novo nome (ou Enter para manter): ");
                    String novoNome = console.nextLine();
                    if (!novoNome.isEmpty()) 
                    {
                        serie[num].setNome(novoNome);
                    }

                    System.out.print("Novo ano de lançamento (ou Enter para manter): ");
                    String ano = console.nextLine();
                    if (!ano.isEmpty()) 
                    {
                        LocalDate anoS = LocalDate.parse(ano + "-01-01"); 
                        serie[num].setAnoLancamento(anoS);
                    }

                    System.out.print("Nova sinopse (ou Enter para manter): ");
                    String novaSinopse = console.nextLine();
                    if (!novaSinopse.isEmpty()) 
                    {
                        serie[num].setSinopse(novaSinopse);
                    }

                    System.out.print("Novo streaming (ou Enter para manter): ");
                    String novoStreaming = console.nextLine();
                    if (!novoStreaming.isEmpty()) 
                    {
                        serie[num].setStreaming(novoStreaming);
                    }

                    System.out.print("Novo genero (ou Enter para manter): ");
                    String novogenero = console.nextLine();
                    if (!novoStreaming.isEmpty()) 
                    {
                        serie[num].setGenero(novogenero);
                    }

                    System.out.print("Nova classificação indicada (ou Enter para manter): ");
                    short novoclassificacaoIndicativa = console.nextShort();
                    serie[num].setClassificacaoIndicativa(novoclassificacaoIndicativa);
        
                    System.out.print("\nConfirma as alterações? (S/N) ");
                    char resp = console.nextLine().charAt(0);

                    if (resp == 'S' || resp == 's') 
                    {
                        boolean alterado = arqAtuacoes.update(serie[num]);
                        if (alterado) 
                        {
                            System.out.println("Atuações alterada com sucesso!");
                        } else 
                        {
                            System.out.println("Erro ao alterar a Atuações.");
                        }
                    } else 
                    {
                        System.out.println("Alterações canceladas.");
                    }
                } else 
                {
                    System.out.println("Não há serie associada a esse número.");
                }
            } else 
            {
                System.out.println("Atuações não encontrada.");
            }
        } catch (Exception e) 
        {
            System.out.println("Erro ao alterar Atuações.");
        }
    }

    
    public void excluirSerie() throws Exception 
    {
        System.out.println("\nExclusão de Atuações");
        
        System.out.print("Nome da Atuações: ");
        String nome = console.nextLine();
        System.out.println();

        try 
        {
            Serie[] serie = arqAtuacoes.readNome(nome);
            if (serie != null && serie.length > 0) 
            {
                for (int i=0; i < serie.length; i++) 
                {
                    System.out.println("\t[" + i + "]");
                    mostrarSerie(serie[i]);
                }

                System.out.print("Digite o número da Atuações a ser excluída: ");
                int num = console.nextInt();
                console.nextLine();

            
                if (num >= 0 && serie[num] != null) 
                {
                    Episodio[] episodios = arqAtores.readEpisodiosSerie(serie[num].getID());
                    if (episodios != null) 
                    {
                        System.out.print("Essa Atuações possui episódios vinculados, deseja excluir mesmo assim? (S/N) ");
                    } else 
                    {
                        System.out.print("Tem certeza que deseja excluir essa Atuações? (S/N) ");
                    }
                    char resposta = console.nextLine().charAt(0);
                    if (resposta != 'S' && resposta != 's') 
                    {
                        System.out.println("A Atuações não foi excluída.");
                        return;
                    }
                    
                    boolean excluido = arqAtuacoes.delete(serie[num].getID());
                    if (excluido) 
                    {
                        System.out.println("Atuações excluída com sucesso!.");
                    } else 
                    {
                        System.out.println("Erro ao excluir a Atuações.");
                    }
                } else 
                {
                    System.out.println("Não há serie associada a esse número.");
                }
            } else 
            {
                System.out.println("Atuações não encontrada.");
            }
        } catch (Exception e) 
        {
            System.out.println("Erro ao excluir Atuações.");
        }
    }

    public void mostrarEpSerie() 
    {
        System.out.println("\nBusca de episódio:");
        System.out.print("De qual Atuações deseja buscar o episódio? (Nome da Atuações): ");
        
        String nomeSerieVinculada = console.nextLine();
        System.out.println();
        boolean dadosCorretos = false;
        
        do 
        {
            try 
            {
                Serie[] series = arqAtuacoes.readNome(nomeSerieVinculada);
                
                if (series != null && series.length > 0) 
                {
                    System.out.println("Atuaçõess encontradas:");
                    for (int i = 0; i < series.length; i++) 
                    {
                        System.out.print("[" + i + "] ");
                        mostrarSerie(series[i]);
                    }
                    
                    System.out.print("\nDigite o número da Atuações escolhida: ");
                    if (console.hasNextInt()) 
                    {
                        int num = console.nextInt();
                        console.nextLine();
                        
                        if (num < 0 || num >= series.length) 
                        {
                            System.err.println("Número inválido!");
                        } else 
                        {
                            System.out.println("Episódios da Atuações " + series[num].getNome() + ":");
                            Episodio[] episodios = arqAtores.readEpisodiosSerie(series[num].getID());
                            
                            if (episodios != null && episodios.length > 0) 
                            {
                                int temporadaAtual = -1;
                                for (Episodio ep : episodios) {
                                    if (ep.getTemporada() != temporadaAtual) 
                                    {
                                        temporadaAtual = ep.getTemporada();
                                        System.out.println("\nTemporada " + temporadaAtual + ":");
                                    }
                                    menuEpisodio.mostraEpisodio(ep);
                                }
                            } else 
                            {
                                System.out.println("Nenhum episódio encontrado para esta Atuações.");
                            }
                            dadosCorretos = true;
                        }
                    } else 
                    {
                        System.err.println("Entrada inválida! Digite um número válido.");
                        console.nextLine(); // Limpar buffer
                    }
                } else 
                {
                    System.out.println("Nenhuma Atuações encontrada com esse nome.");
                    dadosCorretos = true;
                }
            } catch (Exception e) 
            {
                System.out.println("Erro ao buscar a Atuações: " + e.getMessage());
                dadosCorretos = true;
            }
        } while (!dadosCorretos);
    }

    public static void mostrarSerie (Serie serie)
    {
        System.out.println ( "| > \""         + serie.getNome ()          + "\" " + serie.getGenero () + " " + serie.getClassificacaoIndicativaColorida ()       + "\n" +
                   "| "             + serie.getSinopse ()            + "\n" +
                   "| Lançado em: " + serie.getAnoLancamento().toString () + " - no streaming: " + serie.getStreaming()+ " - ID: " +  serie.getID ());
    }

    public void povoar () throws Exception 
    {
        arqAtuacoes.create(new Serie("Fullmetal Alchemist: Brotherhood", LocalDate.of(2009, 4, 5), "Dois irmãos usam alquimia para tentar recuperar o que perderam, enfrentando consequências sombrias.", "Crunchyroll", "Ação/Fantasia", (short) 14));
        arqAtuacoes.create(new Serie("Stranger Things", LocalDate.of(2017, 1, 10), "Crianças enfrentam forças sobrenaturais em uma pequena cidade.", "Netflix", "Sobrenatural/Mistério", (short) 14));
        arqAtuacoes.create(new Serie("Shingeki no Kyojin (Attack on Titan)", LocalDate.of(2013, 4, 6), "Humanos lutam pela sobrevivência contra gigantes devoradores em um mundo brutal.", "Crunchyroll", "Ação/Fantasia", (short) 16));
        arqAtuacoes.create(new Serie("Kimetsu no Yaiba (Demon Slayer)", LocalDate.of(2019, 4, 6), "Tanjiro se torna caçador de demônios para salvar sua irmã e vingar sua família.", "Crunchyroll", "Ação/Sobrenatural", (short) 14));
        arqAtuacoes.create(new Serie("Boku no Hero Academia (My Hero Academia)", LocalDate.of(2016, 4, 3), "Em um mundo de heróis com superpoderes, um garoto sem poderes sonha em se tornar símbolo da paz.", "Crunchyroll", "Ação/Super-heróis", (short) 12));
        arqAtuacoes.create(new Serie("Steins;Gate", LocalDate.of(2011, 4, 6), "Cientistas acidentais descobrem uma maneira de enviar mensagens ao passado, afetando o futuro.", "Funimation", "Ficção Científica/Thriller", (short) 14));
        arqAtuacoes.create(new Serie("Tokyo Revengers", LocalDate.of(2021, 4, 11), "Um jovem volta no tempo para impedir a morte de sua ex-namorada e mudar o rumo da sua vida.", "Crunchyroll", "Ação/Drama", (short) 16));
        arqAtuacoes.create(new Serie("Jujutsu Kaisen", LocalDate.of(2020, 10, 3), "Estudantes enfrentam maldições perigosas para proteger a humanidade.", "Crunchyroll", "Ação/Sobrenatural", (short) 16));
        arqAtuacoes.create(new Serie("Psycho-Pass", LocalDate.of(2012, 10, 12), "Em um futuro distópico, a justiça é decidida por um sistema que mede a mente humana.", "Crunchyroll", "Ficção Científica/Policial", (short) 18));
        arqAtuacoes.create(new Serie("Black Mirror", LocalDate.of(2015, 10, 25), "Cada episódio apresenta uma história distópica sobre o impacto da tecnologia.", "Netflix", "Ficção Científica/Drama", (short) 18));
    }

}

//////////////////////////////////////////////////