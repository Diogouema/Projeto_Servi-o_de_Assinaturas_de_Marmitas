package br.com.marmitas.domain;
public class Endereco {
    private String enderecoEntrega,diaPreferido;
    public Endereco(String dadosEndereco,String diaPreferido){atualizarDados(dadosEndereco,diaPreferido);}
    public boolean validarPreenchimento(){return enderecoEntrega!=null&&!enderecoEntrega.isBlank()&&diaPreferido!=null&&!diaPreferido.isBlank();}
    public void atualizarDados(String dadosEndereco,String diaPreferido){this.enderecoEntrega=dadosEndereco;this.diaPreferido=diaPreferido;}
    public String getEnderecoEntrega(){return enderecoEntrega;} public String getDiaPreferido(){return diaPreferido;}
}
