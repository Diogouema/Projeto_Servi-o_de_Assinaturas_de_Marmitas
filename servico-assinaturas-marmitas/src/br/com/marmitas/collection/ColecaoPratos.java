package br.com.marmitas.collection;
import br.com.marmitas.domain.*; import java.util.*;
public class ColecaoPratos {
    // Cardápio fictício para demonstração; substituir pelo cardápio oficial do grupo.
    private final List<PratoPrincipal> elementos=List.of(
      new PratoPrincipal("Frango grelhado", "Frango, arroz integral e legumes",TipoMarmita.TRADICIONAL),
      new PratoPrincipal("Carne de panela", "Carne bovina, arroz e feijão",TipoMarmita.TRADICIONAL),
      new PratoPrincipal("Lasanha de berinjela", "Berinjela, molho de tomate e queijo vegetal",TipoMarmita.VEGETARIANA),
      new PratoPrincipal("Grão-de-bico ao curry", "Grão-de-bico, legumes e arroz",TipoMarmita.VEGETARIANA),
      new PratoPrincipal("Frango com purê", "Frango, purê sem leite e brócolis",TipoMarmita.SEM_LACTOSE),
      new PratoPrincipal("Picadinho", "Carne, arroz e legumes sem ingredientes lácteos",TipoMarmita.SEM_LACTOSE));
    public List<PratoPrincipal> selecionarPorTipo(TipoMarmita tipo){return elementos.stream().filter(p->p.ehDoTipo(tipo)).toList();}
    public DadosPrato obterDados(PratoPrincipal prato){return prato.obterDados();}
}
