package br.com.marmitas.domain;
import java.util.*;
public class Pedido {
    private final StatusPedido status; private final List<ItemPedido> itens=new ArrayList<>();
    public Pedido(StatusPedido status){this.status=status;}
    public ItemPedido adicionarItem(PratoPrincipal prato,int quantidade){ItemPedido item=new ItemPedido(prato,quantidade);itens.add(item);return item;}
    public StatusPedido getStatus(){return status;} public List<ItemPedido> getItens(){return Collections.unmodifiableList(itens);}
}
