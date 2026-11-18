package br.com.marmitas.persistence;
import br.com.marmitas.domain.*; import java.io.*; import java.nio.charset.StandardCharsets; import java.nio.file.*;
public class CsvRepositorio {
    private final Path pasta=Path.of("dados");
    public void salvar(Assinatura a){
        try{Files.createDirectories(pasta); salvarAssinatura(a); salvarPedido(a);}
        catch(IOException e){throw new UncheckedIOException("Falha ao persistir os dados CSV.",e);}
    }
    private void salvarAssinatura(Assinatura a)throws IOException{
        Path f=pasta.resolve("assinaturas.csv"); boolean novo=!Files.exists(f);
        try(BufferedWriter w=Files.newBufferedWriter(f,StandardCharsets.UTF_8,StandardOpenOption.CREATE,StandardOpenOption.APPEND)){
            if(novo)w.write("protocolo;nome;email;celular;plano;preferencia;endereco;dia;status\n");
            w.write(csv(a.getProtocolo())+";"+csv(a.getAssinante().getNome())+";"+csv(a.getAssinante().getEmail())+";"+csv(a.getAssinante().getCelular())+";"+csv(a.getPlano().getNome())+";"+a.getPreferencia().getTipo()+";"+csv(a.getEndereco().getEnderecoEntrega())+";"+csv(a.getEndereco().getDiaPreferido())+";"+a.getStatus()+"\n");
        }
    }
    private void salvarPedido(Assinatura a)throws IOException{
        Path f=pasta.resolve("pedidos.csv"); boolean novo=!Files.exists(f);
        try(BufferedWriter w=Files.newBufferedWriter(f,StandardCharsets.UTF_8,StandardOpenOption.CREATE,StandardOpenOption.APPEND)){
            if(novo)w.write("protocolo;status;prato;tipo;quantidade;pagamento\n");
            for(ItemPedido i:a.getPedido().getItens())w.write(csv(a.getProtocolo())+";"+a.getPedido().getStatus()+";"+csv(i.getPrato().getNome())+";"+i.getPrato().getTipo()+";"+i.getQuantidade()+";"+csv(a.getPagamento().getStatus())+"\n");
        }
    }
    private String csv(String s){return "\""+(s==null?"":s.replace("\"","\"\""))+"\"";}
}
