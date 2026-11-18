package br.com.marmitas.boundary;
import br.com.marmitas.control.ControladorAssinatura; import br.com.marmitas.domain.*; import java.util.*;
public class TelaAssinatura {
    private final ControladorAssinatura controle; private final Scanner entrada=new Scanner(System.in);
    public TelaAssinatura(ControladorAssinatura controle){this.controle=controle;}
    public void iniciarAssinatura(){
        System.out.println("\n=== SERVIÇO DE ASSINATURAS DE MARMITAS ===");
        while(true){try{controle.iniciarContratacao();solicitarCelularNomeEmail();if(!confirmarCelular())continue;if(!configurarPlanoCardapioEntrega())continue;apresentarEtapaPagamento();if(finalizarPagamento())return;}catch(IllegalArgumentException|IllegalStateException e){System.out.println("Erro: "+e.getMessage());}if(!simNao("Deseja tentar novamente? (s/n): ")){encerrarContratacaoSemAtivar();return;}}
    }
    public void apresentarIdentificacao(){System.out.println("\n[I02] Identificação do cliente");}
    public void solicitarCelularNomeEmail(){String celular=ler("Celular (com DDD): ");String nome=ler("Nome: ");String email=ler("E-mail: ");controle.identificarAssinante(celular,nome,email);apresentarValidacaoSMS();}
    public void apresentarValidacaoSMS(){System.out.println("Digite o código recebido por SMS.");}
    private boolean confirmarCelular(){while(true){String c=ler("Código SMS (ou R para reenviar): ");if(c.equalsIgnoreCase("R")){reenviarCodigo();continue;}if(controle.confirmarCelular(c)){liberarSelecaoDePlano();return true;}informarErroCodigo();if(!simNao("Tentar novamente? (s/n): "))return false;}}
    public void reenviarCodigo(){controle.reenviarCodigo();System.out.println("Novo código enviado.");}
    public void informarErroCodigo(){System.out.println("Código incorreto ou expirado.");}
    public void liberarSelecaoDePlano(){System.out.println("Celular confirmado.");}
    private boolean configurarPlanoCardapioEntrega(){System.out.println("\n[I03] Planos disponíveis:");List<PlanoAssinatura> planos=controle.obterPlanos();apresentarPlanos(planos);for(int i=0;i<planos.size();i++)System.out.println((i+1)+") "+planos.get(i));int op=lerInt("Escolha o plano: ");if(op<1||op>planos.size())throw new IllegalArgumentException("Opção de plano inválida.");TipoMarmita tipo=escolherTipo();controle.configurarPlano(planos.get(op-1),tipo);List<PratoPrincipal> pratos=controle.obterPratos();System.out.println("\nCardápio "+tipo+":");for(int i=0;i<pratos.size();i++)apresentarPrato(pratos.get(i),pratos.get(i).getNome(),pratos.get(i).getDescricao());Map<PratoPrincipal,Integer> mapa=new LinkedHashMap<>();while(true){System.out.println("Informe prato e quantidade. Digite 0 para terminar.");int p=lerInt("Número do prato: ");if(p==0)break;if(p<1||p>pratos.size()){apresentarErroSelecao();continue;}int qtd=lerInt("Quantidade: ");if(qtd>0)mapa.put(pratos.get(p-1),qtd);else apresentarErroSelecao();}if(!controle.validarSelecao(mapa)){apresentarErroSelecao();return false;}apresentarCarrinho(mapa);if(!simNao("Confirmar seleção? (s/n): "))return false;String endereco=ler("Endereço completo de entrega: ");String dia=ler("Dia preferido para entrega: ");if(!controle.registrarEntrega(endereco,dia)){solicitarCorrecaoEndereco();endereco=ler("Endereço completo: ");dia=ler("Dia preferido: ");if(!controle.corrigirEntrega(endereco,dia)){manterNaEtapaDeEntrega();return false;}}liberarPagamento();return true;}
    private TipoMarmita escolherTipo(){System.out.println("Preferência alimentar: 1) Tradicional 2) Vegetariana 3) Sem lactose");return switch(lerInt("Opção: ")){case 1->TipoMarmita.TRADICIONAL;case 2->TipoMarmita.VEGETARIANA;case 3->TipoMarmita.SEM_LACTOSE;default->throw new IllegalArgumentException("Preferência inválida.");};}
    public void apresentarPlanos(List<PlanoAssinatura> planos){}
    public void apresentarPrato(PratoPrincipal prato,String nome,String descricao){System.out.println(" - "+nome+": "+descricao);}
    public void apresentarCarrinho(Map<PratoPrincipal,Integer> selecao){System.out.println("Carrinho:");selecao.forEach((p,q)->System.out.println(" - "+p.getNome()+" x "+q));}
    public void apresentarErroSelecao(){System.out.println("Seleção inválida: escolha ao menos um prato compatível e respeite o limite do plano.");}
    public void revisarMarmitasSelecionadas(){} public void solicitarCorrecaoDeQuantidadesOuPratos(){}
    public void confirmarEntrega(String dadosEndereco,String diaPreferido){} public void liberarPagamento(){System.out.println("Entrega validada. Pagamento liberado.");}
    public void apresentarEtapaPagamento(){System.out.println("\n[I04] Etapa de pagamento");}
    public void manterNaEtapaDeEntrega(){System.out.println("Dados de entrega inválidos; contratação não avançou.");}
    public void solicitarCorrecaoEndereco(){System.out.println("Endereço e dia são obrigatórios.");}
    public void corrigirEntrega(String dadosEndereco,String diaPreferido){}
    private boolean finalizarPagamento(){var a=controle.getAssinatura();System.out.println("Plano: "+a.getPlano()+" | Total: R$ "+a.getPlano().obterValor());solicitarCartaoEParcelamento();String cartao=ler("Número do cartão (16 dígitos, simulado): ");int parcelas=lerInt("Parcelas (1 a 12): ");if(parcelas<1||parcelas>12)throw new IllegalArgumentException("Parcelamento deve ser de 1 a 12.");confirmarPagamento(cartao,parcelas);boolean ok=controle.pagar(cartao,parcelas);if(ok){apresentarSucesso(a.getProtocolo(),a.obterResumoConfirmacao());return true;}if(cartao.replaceAll("\\s","").matches("\\d{16}")){apresentarFalhaPagamento();}else corrigirDadosCartao();return false;}
    public void apresentarResumoFinanceiro(java.math.BigDecimal valor,PlanoAssinatura plano){System.out.println("Valor: R$ "+valor+" — "+plano.getNome());}
    public void solicitarCartaoEParcelamento(){System.out.println("Informe os dados do cartão para autorização simulada.");}
    public void confirmarPagamento(String dadosCartao,Integer parcelamento){}
    public void apresentarProcessamento(){System.out.println("Processando pagamento...");}
    public void corrigirDadosCartao(){System.out.println("Dados do cartão inválidos. Não informe CVV neste protótipo.");}
    public void apresentarFalhaPagamento(){System.out.println("Pagamento não autorizado. A assinatura permanece aguardando pagamento e nenhum pedido foi criado.");}
    public void apresentarSucesso(String protocolo,ResumoAssinatura r){System.out.println("\n<><><> ASSINATURA ATIVADA <><><>");System.out.println("Protocolo: "+protocolo);System.out.println("Cliente: "+r.assinante());System.out.println("Plano: "+r.plano());System.out.println("Entrega: "+r.entrega());System.out.println("Pagamento: "+r.pagamento());System.out.println("Pedido: "+r.pedido());System.out.println("Dados salvos em dados/assinaturas.csv e dados/pedidos.csv");}
    public void encerrarContratacaoSemAtivar(){System.out.println("Contratação encerrada sem ativar a assinatura.");} public void encerrarContratacaoSemCriarAssinatura(){encerrarContratacaoSemAtivar();} public void irParaMinhaConta(){} public void apresentarConta(){}
    private String ler(String p){System.out.print(p);return entrada.nextLine().trim();}
    private int lerInt(String p){try{return Integer.parseInt(ler(p));}catch(NumberFormatException e){throw new IllegalArgumentException("Digite um número válido.");}}
    private boolean simNao(String p){return ler(p).equalsIgnoreCase("s");}
}
