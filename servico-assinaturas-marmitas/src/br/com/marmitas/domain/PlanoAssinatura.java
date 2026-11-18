package br.com.marmitas.domain;
import java.math.BigDecimal;
public class PlanoAssinatura {
    private final String nome, periodicidade; private final int quantidadeRefeicoes; private final BigDecimal valor;
    public PlanoAssinatura(String nome,int quantidadeRefeicoes,String periodicidade,BigDecimal valor){
        this.nome=nome;this.quantidadeRefeicoes=quantidadeRefeicoes;this.periodicidade=periodicidade;this.valor=valor;
    }
    public boolean validarQuantidade(int total){return total>0 && total<=quantidadeRefeicoes;}
    public BigDecimal obterValor(){return valor;} public int getQuantidadeRefeicoes(){return quantidadeRefeicoes;}
    public String getNome(){return nome;} public String getPeriodicidade(){return periodicidade;}
    @Override public String toString(){return nome+" — "+quantidadeRefeicoes+" refeições / "+periodicidade+" — R$ "+valor;}
}
