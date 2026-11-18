package br.com.marmitas.domain;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
public class CodigoConfirmacao {
    private String codigo; private int tentativas; private LocalDateTime expiraEm;
    private static final SecureRandom RANDOM = new SecureRandom();
    public CodigoConfirmacao(Duration validade) { gerarNovoCodigo(validade); }
    public String gerarNovoCodigo(Duration validade) {
        codigo=String.format("%06d", RANDOM.nextInt(1_000_000));
        expiraEm=LocalDateTime.now().plus(validade); tentativas=0; return codigo;
    }
    public boolean validar(String informado, LocalDateTime agora) {
        boolean ok = codigo != null && codigo.equals(informado) && agora.isBefore(expiraEm);
        if (!ok) registrarTentativaSemSucesso(); return ok;
    }
    public void registrarTentativaSemSucesso(){tentativas++;}
    public String obterCodigo(){return codigo;}
    public int getTentativas(){return tentativas;}
    public LocalDateTime getExpiraEm(){return expiraEm;}
}
