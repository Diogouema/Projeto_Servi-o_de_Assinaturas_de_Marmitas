package br.com.marmitas.domain;
public class PratoPrincipal {
    private final String nome, descricao; private final TipoMarmita tipo;
    public PratoPrincipal(String nome,String descricao,TipoMarmita tipo){this.nome=nome;this.descricao=descricao;this.tipo=tipo;}
    public DadosPrato obterDados(){return new DadosPrato(nome,descricao,tipo);}
    public boolean ehDoTipo(TipoMarmita tipo){return this.tipo==tipo;}
    public String getNome(){return nome;} public String getDescricao(){return descricao;} public TipoMarmita getTipo(){return tipo;}
    @Override public String toString(){return nome+" — "+descricao;}
}
