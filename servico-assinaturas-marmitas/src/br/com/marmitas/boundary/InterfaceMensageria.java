package br.com.marmitas.boundary;
import br.com.marmitas.domain.ResumoAssinatura;
public interface InterfaceMensageria { void enviarSMS(String celular,String codigo); void enviarConfirmacao(String email,String protocolo,ResumoAssinatura resumo); }
