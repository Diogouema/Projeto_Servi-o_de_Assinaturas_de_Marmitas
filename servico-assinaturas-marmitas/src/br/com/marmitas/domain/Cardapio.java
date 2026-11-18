package br.com.marmitas.domain;
import br.com.marmitas.collection.ColecaoPratos; import java.util.*;
public class Cardapio {
    private final ColecaoPratos pratos; public Cardapio(ColecaoPratos pratos){this.pratos=pratos;}
    public List<PratoPrincipal> obterPratosPorTipo(TipoMarmita tipo){return pratos.selecionarPorTipo(tipo);}
    public boolean validarPratos(Map<PratoPrincipal,Integer> selecao,TipoMarmita tipo){return selecao!=null&&!selecao.isEmpty()&&selecao.entrySet().stream().allMatch(e->e.getKey()!=null&&e.getKey().ehDoTipo(tipo)&&e.getValue()!=null&&e.getValue()>0);}
}
