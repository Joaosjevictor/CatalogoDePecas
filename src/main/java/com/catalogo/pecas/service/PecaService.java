package com.catalogo.pecas.service;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.RiakNode;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.catalogo.pecas.model.Peca;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.net.UnknownHostException;

@Service
public class PecaService {

    private RiakClient client;
    private final Namespace bucketPecas = new Namespace("default", "pecas");

    @PostConstruct
    public void iniciarConexao() throws UnknownHostException {
        RiakNode node = new RiakNode.Builder()
                .withRemoteAddress("127.0.0.1")
                .withRemotePort(8087)
                .build();

        RiakCluster cluster = new RiakCluster.Builder(node).build();
        cluster.start();
        this.client = new RiakClient(cluster);
        
        System.out.println("✅ Conexão com o Banco Riak KV estabelecida com sucesso!");
    }

    @PreDestroy
    public void fecharConexao() {
        if (this.client != null) {
            this.client.shutdown();
        }
    }

    public Peca salvarPeca(Peca peca) throws Exception {
        Location localizacao = new Location(bucketPecas, peca.getSku());
        StoreValue guardarComando = new StoreValue.Builder(peca)
                .withLocation(localizacao)
                .build();

        client.execute(guardarComando);
        
        return peca;
    }

    public Peca buscarPecaPorSku(String sku) throws Exception {
        Location localizacao = new Location(bucketPecas, sku);
        FetchValue buscarComando = new FetchValue.Builder(localizacao).build();
        FetchValue.Response resposta = client.execute(buscarComando);
        
        if (resposta.hasValues()) {
            return resposta.getValue(Peca.class);
        } else {
            return null; 
        }
    }
}
