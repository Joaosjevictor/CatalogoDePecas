package com.catalogo.pecas.config;

import com.catalogo.pecas.model.Aplicacao;
import com.catalogo.pecas.model.Peca;
import com.catalogo.pecas.service.PecaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CargaInicialDados implements CommandLineRunner {

    private final PecaService pecaService;

    public CargaInicialDados(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("⏳ Iniciando a carga de dados no Riak KV...");

        List<Peca> catalogo = Arrays.asList(
            // FREIOS
            new Peca("n1020", "Pastilha de Freio Dianteira", "Cobreq", "Freios", Arrays.asList(
                new Aplicacao("Volkswagen", "Gol", "1.0/1.6 G5", "2008-2012"),
                new Aplicacao("Volkswagen", "Voyage", "1.0/1.6 G5", "2009-2012"))),
            new Peca("hq2100", "Lona de Freio Traseira", "Fras-le", "Freios", Arrays.asList(
                new Aplicacao("Fiat", "Strada", "1.4 Fire", "2013-2020"))),
            new Peca("bd3450", "Disco de Freio Ventilado", "Fremax", "Freios", Arrays.asList(
                new Aplicacao("Chevrolet", "Onix", "1.0", "2013-2019"))),
            new Peca("hf66", "Fluido de Freio DOT 4", "Varga", "Freios", Arrays.asList(
                new Aplicacao("Universal", "Todos", "N/A", "Todos os anos"))),

            // IGNICAO E INJECAO
            new Peca("sp500", "Vela de Ignição Iridium", "Bosch", "Ignição", Arrays.asList(
                new Aplicacao("Fiat", "Palio", "1.0 Fire", "2005-2010"))),
            new Peca("bkr6e", "Vela de Ignição Standard", "NGK", "Ignição", Arrays.asList(
                new Aplicacao("Honda", "Civic", "1.8 16V", "2007-2011"))),
            new Peca("bi0012", "Bobina de Ignição", "Magneti Marelli", "Ignição", Arrays.asList(
                new Aplicacao("Volkswagen", "Fox", "1.6 8V", "2004-2014"))),
            new Peca("iwp065", "Bico Injetor", "Weber", "Injeção", Arrays.asList(
                new Aplicacao("Fiat", "Uno", "1.0 Mille", "2001-2008"))),

            // FILTROS
            new Peca("ph3569", "Filtro de Óleo", "Fram", "Filtros", Arrays.asList(
                new Aplicacao("Honda", "HR-V", "1.8 16V", "2015-2021"))),
            new Peca("psl55", "Filtro de Óleo", "Tecfil", "Filtros", Arrays.asList(
                new Aplicacao("Chevrolet", "Corsa", "1.0/1.4", "2002-2012"))),
            new Peca("fci1630", "Filtro de Combustível", "Wega", "Filtros", Arrays.asList(
                new Aplicacao("Ford", "Ka", "1.0 3Cil", "2015-2021"))),
            new Peca("arl6098", "Filtro de Ar do Motor", "Tecfil", "Filtros", Arrays.asList(
                new Aplicacao("Hyundai", "HB20", "1.0 12V", "2012-2019"))),
            new Peca("acp122", "Filtro de Cabine (Ar Condicionado)", "Mann", "Filtros", Arrays.asList(
                new Aplicacao("Toyota", "Corolla", "2.0 16V", "2015-2019"))),

            // SUSPENSAO E DIRECAO
            new Peca("sp800", "Amortecedor Dianteiro a Gás", "Monroe", "Suspensão", Arrays.asList(
                new Aplicacao("Chevrolet", "Prisma", "1.0/1.4", "2013-2019"))),
            new Peca("gp3270", "Amortecedor Traseiro", "Cofap", "Suspensão", Arrays.asList(
                new Aplicacao("Volkswagen", "Polo", "1.6 8V", "2003-2014"))),
            new Peca("n9900", "Pivô de Suspensão", "Nakata", "Suspensão", Arrays.asList(
                new Aplicacao("Ford", "Fiesta", "1.6", "2003-2014"))),
            new Peca("tb203", "Terminal de Direção", "Viemar", "Direção", Arrays.asList(
                new Aplicacao("Renault", "Sandero", "1.0/1.6", "2008-2014"))),

            // MOTOR E CORREIAS
            new Peca("ct1045", "Correia Dentada", "Contitech", "Motor", Arrays.asList(
                new Aplicacao("Volkswagen", "Up!", "1.0 12V", "2014-2020"))),
            new Peca("ny7711", "Tensor da Correia Dentada", "Nytron", "Motor", Arrays.asList(
                new Aplicacao("Fiat", "Siena", "1.4 Fire", "2008-2016"))),
            new Peca("vkpc8140", "Bomba D'água", "SKF", "Motor", Arrays.asList(
                new Aplicacao("Ford", "EcoSport", "1.6 Sigma", "2013-2017"))),
            new Peca("vt288", "Válvula Termostática", "MTE-Thomson", "Motor", Arrays.asList(
                new Aplicacao("Peugeot", "208", "1.5 8V", "2013-2016"))),

            // ELETRICA E ILUMINACAO
            new Peca("h7-55w", "Lâmpada H7 Super Branca", "Osram", "Iluminação", Arrays.asList(
                new Aplicacao("Universal", "Todos", "N/A", "Todos os anos"))),
            new Peca("b60d", "Bateria 60Ah Direita", "Moura", "Elétrica", Arrays.asList(
                new Aplicacao("Universal", "Passeio", "N/A", "Todos os anos"))),
            new Peca("md102", "Motor de Partida", "Valeo", "Elétrica", Arrays.asList(
                new Aplicacao("Renault", "Duster", "1.6 16V", "2012-2016")))
        );
        int contador = 0;
        for (Peca peca : catalogo) {
            try {
                pecaService.salvarPeca(peca);
                contador++;
            } catch (Exception e) {
                System.err.println("❌ Erro ao salvar a peça " + peca.getSku() + ": " + e.getMessage());
            }
        }
        
        System.out.println("✅ Carga finalizada! " + contador + " peças foram cadastradas com sucesso no banco.");
    }
}
