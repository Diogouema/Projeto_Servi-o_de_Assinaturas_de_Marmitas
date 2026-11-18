package br.com.marmitas.integration;
import br.com.marmitas.boundary.InterfaceOperadora; import br.com.marmitas.domain.ResultadoAutorizacao; import java.math.BigDecimal;
public class OperadoraCartaoCredito implements InterfaceOperadora {
    public ResultadoAutorizacao autorizar(BigDecimal valor,String referenciaCartao,int parcelamento){
        if(referenciaCartao.endsWith("0000"))return new ResultadoAutorizacao(false,"Transação recusada pela operadora simulada.");
        return new ResultadoAutorizacao(true,"Autorização simulada aprovada.");
    }
}
