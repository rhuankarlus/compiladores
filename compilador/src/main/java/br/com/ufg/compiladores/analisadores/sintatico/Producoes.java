package br.com.ufg.compiladores.analisadores.sintatico;

/**
 * Created by rhuan on 11/06/2016.
 */
public enum Producoes {

    UM("1", "P' -> P"),
    DOIS("2", "P -> inicio V A"),
    TRES("3", "V -> varinicio LV"),
    QUATRO("4", "LV -> D LV"),
    CINCO("5", "LV -> varfim;"),
    SEIS("6", "D -> id TIPO;"),
    SETE("7", "TIPO -> int"),
    OITO("8", "TIPO -> real"),
    NOVE("9", "TIPO -> lit"),
    DEZ("10", "A -> ES A"),
    ONZE("11", "ES -> leia id;"),
    DOZE("12", "ES -> escreva ARG;"),
    TREZE("13", "ARG -> literal"),
    QUATORZE("14", "ARG -> num"),
    QUINZE("15", "ARG -> id"),
    DEZESSEIS("16", "A -> CMD A"),
    DEZESSETE("17", "CMD -> id rcb LD;"),
    DEZOITO("18", "LD -> OPRD opm OPRD"),
    DEZENOVE("19", "LD -> OPRD"),
    VINTE("20", "OPRD -> id"),
    VINTE_E_UM("21", "OPRD -> num"),
    VINTE_E_DOIS("22", "A -> COND A"),
    VINTE_E_TRES("23", "COND -> CABEÇALHO CORPO"),
    VINTE_E_QUATRO("24", "CABECALHO -> se (EXP_R) entao"),
    VINTE_E_CINCO("25", "EXP_R -> OPRD opr OPRD"),
    VINTE_E_SEIS("26", "CORPO -> ES CORPO"),
    VINTE_E_SETE("27", "CORPO -> CMD CORPO"),
    VINTE_E_OITO("28", "CORPO -> COND CORPO"),
    VINTE_E_NOVE("29", "CORPO -> fimse"),
    TRINTA("30", "A -> fim");

    private String numero;
    private String producao;

    Producoes(String numero, String producao) {
        this.numero = numero;
        this.producao = producao;
    }

    public String getProducao() {
        return producao;
    }

    public static Producoes fromNumero(String numero) {
        if (numero != null && !numero.trim().equals("")) {
            for (Producoes producao : Producoes.values()) {
                if (producao.numero.equals(numero)) return producao;
            }
        }
        return null;
    }

}
