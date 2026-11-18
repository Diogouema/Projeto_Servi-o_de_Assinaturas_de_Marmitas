package br.com.marmitas.domain;
public class Assinante {
    private final String celular, nome, email;
    private CodigoConfirmacao codigo;
    public Assinante(String celular, String nome, String email) {
        this.celular=celular; this.nome=nome; this.email=email;
    }
    public void associarCodigoConfirmacao(CodigoConfirmacao codigo) { this.codigo=codigo; }
    public String getCelular(){return celular;} public String getNome(){return nome;}
    public String getEmail(){return email;} public CodigoConfirmacao getCodigo(){return codigo;}
}
