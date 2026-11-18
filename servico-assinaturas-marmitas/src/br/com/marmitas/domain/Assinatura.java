package br.com.marmitas.domain;
import java.math.BigDecimal; import java.util.*;
public class Assinatura {
    private final Assinante assinante; private String protocolo; private StatusAssinatura status;
    private PlanoAssinatura plano; private PreferenciaAlimentar preferencia; private Endereco endereco;
    private Pagamento pagamento; private Pedido pedido;
    public Assinatura(Assinante assinante,StatusAssinatura status){this.assinante=assinante;this.status=status;}
    public void vincularPlano(PlanoAssinatura plano){this.plano=plano;}
    public void registrarPreferencia(PreferenciaAlimentar preferencia){this.preferencia=preferencia;}
    public void vincularEndereco(Endereco endereco){this.endereco=endereco;}
    public BigDecimal obterValorDoPlanoVinculado(){if(plano==null)throw new IllegalStateException("Plano não selecionado.");return plano.obterValor();}
    public Pagamento prepararPagamento(BigDecimal valor,CartaoCredito cartao){pagamento=new Pagamento(valor,cartao,"AGUARDANDO_AUTORIZACAO");return pagamento;}
    public Pedido gerarPedido(Map<PratoPrincipal,Integer> selecao){
        if(pagamento==null||!"AUTORIZADO".equals(pagamento.getStatus()))throw new IllegalStateException("Pagamento não autorizado.");
        Pedido novo=new Pedido(StatusPedido.APROVADO); selecao.forEach((p,q)->{if(q!=null&&q>0)novo.adicionarItem(p,q);});
        if(novo.getItens().isEmpty())throw new IllegalStateException("Pedido sem itens."); pedido=novo; return pedido;
    }
    public void ativar(){if(pedido==null||pagamento==null||!"AUTORIZADO".equals(pagamento.getStatus())||plano==null||preferencia==null||endereco==null)throw new IllegalStateException("Não é possível ativar: contratação incompleta.");status=StatusAssinatura.ATIVA;}
    public String gerarProtocolo(){if(status!=StatusAssinatura.ATIVA)throw new IllegalStateException("Assinatura ainda não está ativa.");if(protocolo==null)protocolo="MARM-"+UUID.randomUUID().toString().substring(0,8).toUpperCase();return protocolo;}
    public ResumoAssinatura obterResumoConfirmacao(){return new ResumoAssinatura(protocolo,assinante.getNome(),plano.getNome(),endereco.getEnderecoEntrega()+" / "+endereco.getDiaPreferido(),pagamento.getStatus(),pedido.getStatus()+" ("+pedido.getItens().size()+" itens)");}
    public Assinante getAssinante(){return assinante;} public StatusAssinatura getStatus(){return status;} public PlanoAssinatura getPlano(){return plano;}
    public PreferenciaAlimentar getPreferencia(){return preferencia;} public Endereco getEndereco(){return endereco;} public Pagamento getPagamento(){return pagamento;} public Pedido getPedido(){return pedido;} public String getProtocolo(){return protocolo;}
}
