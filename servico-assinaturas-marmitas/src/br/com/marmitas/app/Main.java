package br.com.marmitas.app;

import br.com.marmitas.boundary.TelaAssinatura;
import br.com.marmitas.control.ControladorAssinatura;
import br.com.marmitas.integration.ServicoMensageria;
import br.com.marmitas.integration.OperadoraCartaoCredito;
import br.com.marmitas.persistence.CsvRepositorio;
import br.com.marmitas.collection.CatalogoPlanos;
import br.com.marmitas.collection.ColecaoPratos;
import br.com.marmitas.domain.*;
import br.com.marmitas.boundary.InterfaceMensageria;
import br.com.marmitas.boundary.InterfaceOperadora;

public class Main {
    public static void main(String[] args) {
        InterfaceMensageria mensageria = new ServicoMensageria();
        InterfaceOperadora operadora = new OperadoraCartaoCredito();
        var controlador = new ControladorAssinatura(
                new CatalogoPlanos(), new Cardapio(new ColecaoPratos()),
                mensageria, operadora, new CsvRepositorio());
        TelaAssinatura tela = new TelaAssinatura(controlador);
        controlador.definirTela(tela);
        tela.iniciarAssinatura();
    }
}
