package br.com.marmitas.domain;
public class ItemPedido {
    private final PratoPrincipal prato; private final int quantidade;
    public ItemPedido(PratoPrincipal prato,int quantidade){if(quantidade<=0)throw new IllegalArgumentException("Quantidade deve ser positiva.");this.prato=prato;this.quantidade=quantidade;}
    public PratoPrincipal getPrato(){return prato;} public int getQuantidade(){return quantidade;}
}
