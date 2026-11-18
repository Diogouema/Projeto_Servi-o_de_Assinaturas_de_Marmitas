package br.com.marmitas.boundary;
import br.com.marmitas.domain.ResultadoAutorizacao; import java.math.BigDecimal;
public interface InterfaceOperadora { ResultadoAutorizacao autorizar(BigDecimal valor,String referenciaCartao,int parcelamento); }
