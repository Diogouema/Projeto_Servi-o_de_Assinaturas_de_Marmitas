package br.com.marmitas.domain;
public class CartaoCredito {
    private final String dadosCartao;
    public CartaoCredito(String dadosCartao){this.dadosCartao=dadosCartao==null?"":dadosCartao.replaceAll("\\s","");}
    public boolean validarDados(){return dadosCartao.matches("\\d{16}");}
    public String obterReferenciaParaAutorizacao(){return validarDados()?"**** **** **** "+dadosCartao.substring(12):"INVALIDO";}
}
