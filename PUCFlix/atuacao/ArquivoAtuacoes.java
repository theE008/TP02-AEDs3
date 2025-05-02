//////////////////////////////////////////////////
// PACOTE

package atuacao;

//////////////////////////////////////////////////
// BIBLIOTECAS DO SISTEMA

import java.util.ArrayList;

//////////////////////////////////////////////////
// BIBLIOTECAS PRÓPRIAS

import episodios.*;
import aeds3.*;

//////////////////////////////////////////////////
// CLASSE ARQUIVO ATUAÇÕES EM SI

public class ArquivoAtuacoes extends Arquivo <Atuacao> 
{
    // VARIÁVEIS
    ArvoreBMais <ParNomeAtorId>    indiceNomeAtor;
    Arquivo     <Atuacao>         ArquivoAtuacoes;
    ArvoreBMais <ParIdId>                 indices;

    public ArquivoAtuacoes () throws Exception 
    {
        super ("Atuacao", Atuacao.class.getConstructor ());
        
        indiceNomeAtor = new ArvoreBMais <> 
        (
            ParNomeAtorId.class.getConstructor (), 
            5, "./dados/"+nomeEntidade+"/indiceNomeAtor.d.db"
        );
    }

    @Override public int create (Atuacao s) throws Exception 
    {
        int id = super.create (s);
        indiceNomeAtor.create (new ParNomeAtorId (s.getNome (), id));
        return id;
    }
 
    public Atuacao [] readNome (String nome) throws Exception 
    {
        if (nome.length ()==0)
            return null;
            
        ArrayList<ParNomeAtorId> ptis = indiceNomeAtor.read (new ParNomeAtorId (nome, -1));
        if (ptis.size ()>0) 
        {
            Atuacao[] Atuacaos = new Atuacao[ptis.size ()];
            int i=0;
            
            for (ParNomeAtorId pti: ptis) 
                Atuacaos[i++] = read (pti.getId ());
            return Atuacaos;
        }
        else 
            return null;
    }

    @Override
    public boolean delete (int id) throws Exception 
    {
        Atuacao s = read (id);   
        if (s!=null) 
        {
            if (super.delete (id))
                return indiceNomeAtor.delete (new ParNomeAtorId (s.getNome (), id));
        }
        return false;
    }

    public boolean delete (String nome, int id) throws Exception 
    {
        ArquivoEpisodios arqEpisodios = new ArquivoEpisodios ();
        
        Atuacao s = read (id); 
        
        if (s != null && s.getID () == id && s.getNome ().equals (nome)){

            System.out.println (s);
           
           
           Episodio[] ids = arqEpisodios.readEpisodiosAtuacao (id); 
            
            if (ids!= null)
            {
                boolean deletados = arqEpisodios.deleteEpisodioAtuacao (id);
                if (deletados)
                {
                    return delete (id);
                }
            } 

                return delete (id);
            
        }
        return false;
    }

    @Override public boolean update (Atuacao novaAtuacao) throws Exception 
    {
        Atuacao s = read (novaAtuacao.getID ());   
        if (s!=null) 
        {
            if (super.update (novaAtuacao)) 
            {
                if (!s.getNome ().equals (novaAtuacao.getNome ())) 
                {
                    indiceNomeAtor.delete (new ParNomeAtorId (s.getNome (), s.getID ()));
                    indiceNomeAtor.create (new ParNomeAtorId (novaAtuacao.getNome (), s.getID ()));
                }
                return true;
            }
        }
        return false;
    }


    public static boolean AtuacaoExiste (int id) throws Exception
    {
        ArquivoAtuacoes ArquivoAtuacoes = new ArquivoAtuacoes ();
        Atuacao s = ArquivoAtuacoes.read (id); 
        if (s != null) 
        {
            return true;
        }
        return false;
    }
}

//////////////////////////////////////////////////