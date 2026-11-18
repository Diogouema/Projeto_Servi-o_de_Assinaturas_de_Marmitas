package br.com.marmitas.domain;
import java.math.BigDecimal;
import br.com.marmitas.boundary.InterfaceOperadora;
public class Pagamento {
    private final BigDecimal valor; private String status; private final CartaoCredito cartao;
    public Pagamento(BigDecimal valor,CartaoCredito cartao,String status){this.valor=valor;this.cartao=cartao;this.status=status;}
    public boolean solicitarAutorizacao(InterfaceOperadora integracao,int parcelamento){
        ResultadoAutorizacao resultado=integracao.autorizar(valor,cartao.obterReferenciaParaAutorizacao(),parcelamento);
        registrarResultado(resultado); return resultado.autorizado();
    }
    public void registrarResultado(ResultadoAutorizacao resultado){status=resultado.autorizado()?"AUTORIZADO":"RECUSADO: "+resultado.mensagem();}
    public BigDecimal getValor(){return valor;} public String getStatus(){return status;}
}
