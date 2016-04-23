package br.com.ufg.compiladores.estados;

import br.com.ufg.compiladores.config.Constants;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class EstadoFactory {

    public static final Object LOCK = new Object();
    public static final Logger LOG = Logger.getLogger(EstadoFactory.class);

    public static EstadoFactory estadoFactory;

    public List<Estado> estados;

    private EstadoFactory() {
        estados = new ArrayList<>(Constants.NUMERO_DE_ESTADOS_PADRAO);
    }

    public static EstadoFactory getInstancia() {
        synchronized (LOCK) {
            if (estadoFactory == null) {
                estadoFactory = new EstadoFactory();
                estadoFactory.montarTabelaDeTransicao();
            }
        }
        return estadoFactory;
    }

    /**
     * A montagem da tabela de transição é feita populando objetos {@code Estado}
     */
    private void montarTabelaDeTransicao() {

    }

}
