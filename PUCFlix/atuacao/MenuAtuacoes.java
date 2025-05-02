/*
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
import atuacao.Atuacao;
import atores.*;
import episodios.*;
import atuacoes.*;

//////////////////////////////////////////////////
// CLASSE MENUS

public class MenuAtuacoes 
{
    ArquivoAtuacoes arqAtuacoes = new ArquivoAtuacoes ();
    ArquivoAtores arqAtores = new ArquivoAtores ();
    private static Scanner console = new Scanner (System.in);
    MenuEpisodios menuEpisodio = new MenuEpisodios (); 

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
                    buscarAtuacao();
                    break;
                case 3:
                    alterarAtuacao();
                    break;
                case 4:
                    excluirAtuacao();
                    break;
                case 5:
                    mostrarEpAtuacao();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void incluirAtuacao (int id_serie) 
    {
        System.out.println ("\nInclusão de Atuações");

        String nome;

        LocalDate ano = null;
        int anoAtual = LocalDate.now ().getYear ();

        do 
        {
            System.out.print ("Nome do Ator (min. 4 letras): ");
            nome = console.nextLine ();
        } while (nome.length () < 4);
        
        System.out.print("\nConfirma a inclusão da Atuações? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') 
        {
            try 
            {
                Ator ator = new Ator (nome);
                int id_ator = arqAtores.create (ator);
                Atuacao a = new Atuacao (id_ator, id_Atuacao);
                arqAtuacoes.create(a);
                System.out.println("Ator incluído com sucesso.");
            } catch (Exception e) 
            {
                System.out.println("Erro ao incluir Atuações.");
            }
        }
    }

    
    public void buscarAtuacao () 
    {
        System.out.println("\nBusca de Atuações");

        System.out.print("Nome do Ator: ");
        String nome = console.nextLine();

        System.out.println();
        try 
        {
            Atuacao [] atuacoes = arqAtuacoes.readNome (nome);
            if (atuacoes != null && atuacoes.length > 0) 
            {
                System.out.println("Atores encontrados: ");
                for (Atuacao s : atuacoes) 
                {
                    mostrarAtuacao (s);
                }
            } else 
            {
                System.out.println("Nenhum Ator encontrado com esse nome.");
            }
            
        } catch (Exception e) 
        {
            System.out.println("Erro ao buscar Atuações.");
        }
    }


    public void alterarAtuacao() throws Exception 
    {
        System.out.println("\nAlteração de Atuações");

        System.out.print("Nome do Ator: ");
        String nome = console.nextLine();
        System.out.println();

        try 
        {
            Atuacao [] Atuacao = arqAtuacoes.readNome (nome);
            if (Atuacao != null) 
            {
                
                for (int i=0; i < Atuacao.length; i++) 
                {
                    System.out.println("\t[" + i + "]");
                    mostrarAtuacao (Atuacao[i]);
                }

                System.out.print("Digite o número do Ator a ser atualizado: ");
                int num = console.nextInt();
                console.nextLine();

                
                if (num >= 0 && Atuacao[num] != null) 
                {
                    System.out.print("Novo nome (ou Enter para manter): ");
                    String novoNome = console.nextLine();
                    if (!novoNome.isEmpty()) 
                    {
                        Atuacao[num].setNome(novoNome);
                    }

                    System.out.print("Novo ano de lançamento (ou Enter para manter): ");
                    String ano = console.nextLine();
                    if (!ano.isEmpty()) 
                    {
                        LocalDate anoS = LocalDate.parse(ano + "-01-01"); 
                        Atuacao[num].setAnoLancamento(anoS);
                    }

                    System.out.print("Nova sinopse (ou Enter para manter): ");
                    String novaSinopse = console.nextLine();
                    if (!novaSinopse.isEmpty()) 
                    {
                        Atuacao[num].setSinopse(novaSinopse);
                    }

                    System.out.print("Novo streaming (ou Enter para manter): ");
                    String novoStreaming = console.nextLine();
                    if (!novoStreaming.isEmpty()) 
                    {
                        Atuacao[num].setStreaming(novoStreaming);
                    }

                    System.out.print("Novo genero (ou Enter para manter): ");
                    String novogenero = console.nextLine();
                    if (!novoStreaming.isEmpty()) 
                    {
                        Atuacao[num].setGenero(novogenero);
                    }

                    System.out.print("Nova classificação indicada (ou Enter para manter): ");
                    short novoclassificacaoIndicativa = console.nextShort();
                    Atuacao[num].setClassificacaoIndicativa(novoclassificacaoIndicativa);
        
                    System.out.print("\nConfirma as alterações? (S/N) ");
                    char resp = console.nextLine().charAt(0);

                    if (resp == 'S' || resp == 's') 
                    {
                        boolean alterado = arqAtuacoes.update(Atuacao[num]);
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
                    System.out.println("Não há Atuacao associada a esse número.");
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

    
    public void excluirAtuacao() throws Exception 
    {
        System.out.println("\nExclusão de Atuações");
        
        System.out.print("Nome da Atuações: ");
        String nome = console.nextLine();
        System.out.println();

        try 
        {
            Atuacao[] Atuacao = arqAtuacoes.readNome(nome);
            if (Atuacao != null && Atuacao.length > 0) 
            {
                for (int i=0; i < Atuacao.length; i++) 
                {
                    System.out.println("\t[" + i + "]");
                    mostrarAtuacao(Atuacao[i]);
                }

                System.out.print("Digite o número da Atuações a ser excluída: ");
                int num = console.nextInt();
                console.nextLine();

            
                if (num >= 0 && Atuacao[num] != null) 
                {
                    Episodio[] episodios = arqAtores.readEpisodiosAtuacao(Atuacao[num].getID());
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
                    
                    boolean excluido = arqAtuacoes.delete(Atuacao[num].getID());
                    if (excluido) 
                    {
                        System.out.println("Atuações excluída com sucesso!.");
                    } else 
                    {
                        System.out.println("Erro ao excluir a Atuações.");
                    }
                } else 
                {
                    System.out.println("Não há Atuacao associada a esse número.");
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

    public void mostrarEpAtuacao() 
    {
        System.out.println("\nBusca de episódio:");
        System.out.print("De qual Atuações deseja buscar o episódio? (Nome da Atuações): ");
        
        String nomeAtuacaoVinculada = console.nextLine();
        System.out.println();
        boolean dadosCorretos = false;
        
        do 
        {
            try 
            {
                Atuacao[] atuacoes = arqAtuacoes.readNome(nomeAtuacaoVinculada);
                
                if (atuacoes != null && atuacoes.length > 0) 
                {
                    System.out.println("Atuaçõess encontradas:");
                    for (int i = 0; i < atuacoes.length; i++) 
                    {
                        System.out.print("[" + i + "] ");
                        mostrarAtuacao(atuacoes[i]);
                    }
                    
                    System.out.print("\nDigite o número da Atuações escolhida: ");
                    if (console.hasNextInt()) 
                    {
                        int num = console.nextInt();
                        console.nextLine();
                        
                        if (num < 0 || num >= atuacoes.length) 
                        {
                            System.err.println("Número inválido!");
                        } else 
                        {
                            System.out.println("Episódios da Atuações " + atuacoes[num].getNome() + ":");
                            Episodio[] episodios = arqAtores.readEpisodiosAtuacao(atuacoes[num].getID());
                            
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

    public static void mostrarAtuacao (Atuacao Atuacao)
    {
        System.out.println ( "| > \""         + Atuacao.getNome ()          + "\" " + Atuacao.getGenero () + " " + Atuacao.getClassificacaoIndicativaColorida ()       + "\n" +
                   "| "             + Atuacao.getSinopse ()            + "\n" +
                   "| Lançado em: " + Atuacao.getAnoLancamento().toString () + " - no streaming: " + Atuacao.getStreaming()+ " - ID: " +  Atuacao.getID ());
    }

    public void povoar () throws Exception 
    {
        arqAtuacoes.create(new Atuacao("Fullmetal Alchemist: Brotherhood", LocalDate.of(2009, 4, 5), "Dois irmãos usam alquimia para tentar recuperar o que perderam, enfrentando consequências sombrias.", "Crunchyroll", "Ação/Fantasia", (short) 14));
        arqAtuacoes.create(new Atuacao("Stranger Things", LocalDate.of(2017, 1, 10), "Crianças enfrentam forças sobrenaturais em uma pequena cidade.", "Netflix", "Sobrenatural/Mistério", (short) 14));
        arqAtuacoes.create(new Atuacao("Shingeki no Kyojin (Attack on Titan)", LocalDate.of(2013, 4, 6), "Humanos lutam pela sobrevivência contra gigantes devoradores em um mundo brutal.", "Crunchyroll", "Ação/Fantasia", (short) 16));
        arqAtuacoes.create(new Atuacao("Kimetsu no Yaiba (Demon Slayer)", LocalDate.of(2019, 4, 6), "Tanjiro se torna caçador de demônios para salvar sua irmã e vingar sua família.", "Crunchyroll", "Ação/Sobrenatural", (short) 14));
        arqAtuacoes.create(new Atuacao("Boku no Hero Academia (My Hero Academia)", LocalDate.of(2016, 4, 3), "Em um mundo de heróis com superpoderes, um garoto sem poderes sonha em se tornar símbolo da paz.", "Crunchyroll", "Ação/Super-heróis", (short) 12));
        arqAtuacoes.create(new Atuacao("Steins;Gate", LocalDate.of(2011, 4, 6), "Cientistas acidentais descobrem uma maneira de enviar mensagens ao passado, afetando o futuro.", "Funimation", "Ficção Científica/Thriller", (short) 14));
        arqAtuacoes.create(new Atuacao("Tokyo Revengers", LocalDate.of(2021, 4, 11), "Um jovem volta no tempo para impedir a morte de sua ex-namorada e mudar o rumo da sua vida.", "Crunchyroll", "Ação/Drama", (short) 16));
        arqAtuacoes.create(new Atuacao("Jujutsu Kaisen", LocalDate.of(2020, 10, 3), "Estudantes enfrentam maldições perigosas para proteger a humanidade.", "Crunchyroll", "Ação/Sobrenatural", (short) 16));
        arqAtuacoes.create(new Atuacao("Psycho-Pass", LocalDate.of(2012, 10, 12), "Em um futuro distópico, a justiça é decidida por um sistema que mede a mente humana.", "Crunchyroll", "Ficção Científica/Policial", (short) 18));
        arqAtuacoes.create(new Atuacao("Black Mirror", LocalDate.of(2015, 10, 25), "Cada episódio apresenta uma história distópica sobre o impacto da tecnologia.", "Netflix", "Ficção Científica/Drama", (short) 18));
    }

}
*/

//////////////////////////////////////////////////