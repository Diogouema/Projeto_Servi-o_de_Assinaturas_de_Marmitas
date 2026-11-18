package br.com.marmitas.control;
import br.com.marmitas.boundary.*; import br.com.marmitas.collection.*; import br.com.marmitas.domain.*; import br.com.marmitas.persistence.CsvRepositorio;
import java.time.Duration; import java.time.LocalDateTime; import java.util.*;
public class ControladorAssinatura {
    private TelaAssinatura tela; private Assinante assinante; private Assinatura assinatura; private CodigoConfirmacao codigo;
    private boolean celularConfirmado=false,selecaoConfirmada=false,configuracaoValida=false,autorizado=false;
    private final CatalogoPlanos catalogoPlanos; private final Cardapio cardapio; private final InterfaceMensageria mensageria; private final InterfaceOperadora integracao; private final CsvRepositorio repositorio;
    private PlanoAssinatura planoSelecionado; private TipoMarmita tipoMarmita; private Endereco enderecoEmEdicao; private Map<PratoPrincipal,Integer> selecao=new LinkedHashMap<>(); private String protocolo; private ResumoAssinatura resumo;
    public ControladorAssinatura(CatalogoPlanos catalogo,Cardapio cardapio,InterfaceMensageria mensageria,InterfaceOperadora integracao,CsvRepositorio repositorio){this.catalogoPlanos=catalogo;this.cardapio=cardapio;this.mensageria=mensageria;this.integracao=integracao;this.repositorio=repositorio;}
    public void definirTela(TelaAssinatura tela){this.tela=tela;}
    public void iniciarContratacao(){celularConfirmado=false;selecaoConfirmada=false;configuracaoValida=false;autorizado=false;tela.apresentarIdentificacao();}
    public Assinante identificarAssinante(String celular,String nome,String email){if(vazio(celular)||vazio(nome)||vazio(email)||!email.contains("@"))throw new IllegalArgumentException("Informe nome, celular e e-mail válido.");assinante=new Assinante(celular,nome,email);assinatura=new Assinatura(assinante,StatusAssinatura.AGUARDANDO_PAGAMENTO);gerarEEnviarCodigo();return assinante;}
    private void gerarEEnviarCodigo(){codigo=new CodigoConfirmacao(Duration.ofMinutes(5));assinante.associarCodigoConfirmacao(codigo);mensageria.enviarSMS(assinante.getCelular(),codigo.obterCodigo());}
    public void reenviarCodigo(){if(assinante==null)throw new IllegalStateException("Informe seus dados primeiro.");gerarEEnviarCodigo();}
    public boolean confirmarCelular(String codigoInformado){celularConfirmado=codigo!=null&&codigo.validar(codigoInformado,LocalDateTime.now());return celularConfirmado;}
    public List<PlanoAssinatura> obterPlanos(){if(!celularConfirmado)throw new IllegalStateException("Confirme o celular primeiro.");return catalogoPlanos.obterPlanosDisponiveis();}
    public void configurarPlano(PlanoAssinatura plano,TipoMarmita tipo){if(!celularConfirmado)throw new IllegalStateException("Celular não confirmado.");if(!catalogoPlanos.obterPlanosDisponiveis().contains(plano))throw new IllegalArgumentException("Plano inválido.");planoSelecionado=plano;tipoMarmita=tipo;assinatura.vincularPlano(plano);assinatura.registrarPreferencia(new PreferenciaAlimentar(tipo));selecao.clear();selecaoConfirmada=false;configuracaoValida=false;}
    public List<PratoPrincipal> obterPratos(){return cardapio.obterPratosPorTipo(tipoMarmita);}
    public boolean validarSelecao(Map<PratoPrincipal,Integer> nova){if(!cardapio.validarPratos(nova,tipoMarmita))return false;int total=nova.values().stream().mapToInt(Integer::intValue).sum();if(!catalogoPlanos.validarQuantidade(planoSelecionado,total))return false;guardarSelecaoTemporaria(nova);return true;}
    private void guardarSelecaoTemporaria(Map<PratoPrincipal,Integer> nova){selecao=new LinkedHashMap<>(nova);selecaoConfirmada=true;}
    public boolean registrarEntrega(String dadosEndereco,String diaPreferido){enderecoEmEdicao=new Endereco(dadosEndereco,diaPreferido);configuracaoValida=enderecoEmEdicao.validarPreenchimento();if(configuracaoValida)assinatura.vincularEndereco(enderecoEmEdicao);return configuracaoValida;}
    public boolean corrigirEntrega(String dadosEndereco,String diaPreferido){return registrarEntrega(dadosEndereco,diaPreferido);}
    public boolean pagar(String dadosCartao,int parcelamento){if(!selecaoConfirmada||!configuracaoValida)throw new IllegalStateException("Conclua a seleção e a entrega antes do pagamento.");CartaoCredito cartao=new CartaoCredito(dadosCartao);if(!cartao.validarDados()){autorizado=false;return false;}var pagamento=assinatura.prepararPagamento(assinatura.obterValorDoPlanoVinculado(),cartao);autorizado=pagamento.solicitarAutorizacao(integracao,parcelamento);if(!autorizado)return false;assinatura.gerarPedido(selecao);assinatura.ativar();protocolo=assinatura.gerarProtocolo();resumo=assinatura.obterResumoConfirmacao();repositorio.salvar(assinatura);mensageria.enviarConfirmacao(assinante.getEmail(),protocolo,resumo);return true;}
    public Assinatura getAssinatura(){return assinatura;} public boolean isCelularConfirmado(){return celularConfirmado;} public String getCodigoAtualParaTeste(){return codigo==null?null:codigo.obterCodigo();}
    private boolean vazio(String s){return s==null||s.isBlank();}
}
