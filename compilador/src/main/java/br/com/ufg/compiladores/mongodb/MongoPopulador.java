package br.com.ufg.compiladores.mongodb;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.conversions.Bson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Rhuan Karlus
 * @since 10/06/2016
 */
public class MongoPopulador extends JFrame {
    private JPanel painelPrincipal;
    private JPanel painelSecundario;

    private JComboBox desvio;
    private JComboBox estadoAtual;
    private JComboBox acao;
    private JComboBox entrada;
    private JComboBox producao;

    private JButton inserirButton;

    public MongoPopulador() {
        super("Populador");
        this.setContentPane(painelPrincipal);

        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popularMongo();
            }
        });

        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void popularMongo() {
        final String estado = (String) estadoAtual.getSelectedItem();
        final String entrada = (String) this.entrada.getSelectedItem();
        final String acao = (String) this.acao.getSelectedItem();
        final String desvio = (String) this.desvio.getSelectedItem();
        final String producao = (String) this.producao.getSelectedItem();

        final Bson filtro = new BsonDocument("estado", new BsonString(estado));
        Bson documento = null;

        switch (acao) {
            case "shift":
                documento = new BsonDocument("$set",
                        new BsonDocument("entradas." + entrada,
                                new BsonDocument("acao", new BsonString("shift"))
                                        .append("desvio", new BsonString(desvio))
                        )
                );
                break;

            case "reduce":
                final String producaoNumero = producao.split(" -> ")[0].split(":")[0];
                final String producaoEsquerda = producao.split(" -> ")[0].split(":")[1].trim();
                final String quantidadeDeTokensProducaoDireita = producao.split(" -> ")[1].split("\\s+").length + "";

                documento = new BsonDocument("$set",
                        new BsonDocument("entradas." + entrada,
                                new BsonDocument("acao", new BsonString("reduce"))
                                        .append("producaoNumero", new BsonString(producaoNumero))
                                        .append("producaoEsquerda", new BsonString(producaoEsquerda))
                                        .append("quantidadeDeTokensProducaoDireita", new BsonString(quantidadeDeTokensProducaoDireita))
                        )
                );
                break;

            case "acc":
                documento = new BsonDocument("$set",
                        new BsonDocument("entradas." + entrada,
                                new BsonDocument("acao", new BsonString("acc"))
                        )
                );
                break;

            default:
                return;
        }

        TransicoesSintaticas.salvar(filtro, documento);

    }

    public static void main(String[] args) {
        final MongoPopulador mongoPopulador = new MongoPopulador();
    }

}
