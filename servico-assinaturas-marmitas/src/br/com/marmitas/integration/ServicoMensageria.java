package br.com.marmitas.integration;
import br.com.marmitas.boundary.InterfaceMensageria; import br.com.marmitas.domain.ResumoAssinatura;
public class ServicoMensageria implements InterfaceMensageria {
    public void enviarSMS(String celular,String codigo){System.out.println("\n[SIMULAÇÃO SMS] Código enviado para "+celular+": "+codigo+" (válido por 5 minutos)");}
    public void enviarConfirmacao(String email,String protocolo,ResumoAssinatura resumo){System.out.println("[SIMULAÇÃO E-MAIL] Confirmação preparada para "+email+" — protocolo "+protocolo);}
}
