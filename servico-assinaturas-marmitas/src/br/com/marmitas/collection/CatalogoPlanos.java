package br.com.marmitas.collection;
import br.com.marmitas.domain.PlanoAssinatura; import java.math.BigDecimal; import java.util.*;
public class CatalogoPlanos {
    // Preços e planos demonstrativos: os diagramas UML não especificam estes valores.
    private final List<PlanoAssinatura> planos=List.of(
      new PlanoAssinatura("Individual",5,"semanal",new BigDecimal("89.90")),
      new PlanoAssinatura("Duplo",10,"semanal",new BigDecimal("159.90")),
      new PlanoAssinatura("Família",20,"semanal",new BigDecimal("289.90")));
    public List<PlanoAssinatura> obterPlanosDisponiveis(){return planos;}
    public boolean validarQuantidade(PlanoAssinatura plano,int total){return plano!=null&&plano.validarQuantidade(total);}
}
