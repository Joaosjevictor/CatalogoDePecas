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

// O @Service avisa ao Spring Boot que esta classe contém as regras de negócio
// e que ele deve gerenciar ela automaticamente.
@Service
public class PecaService {

    private RiakClient client;
    // O Namespace é o nosso "Bucket" (Balde). É como se fosse o nome da tabela.
    private final Namespace bucketPecas = new Namespace("default", "pecas");

    // O @PostConstruct faz esse método rodar sozinho assim que o sistema liga.
    // É aqui que criamos a ponte entre o seu Java e o Docker onde o Riak está rodando.
    @PostConstruct
    public void iniciarConexao() throws UnknownHostException {
        // Apontamos para a porta 8087, que configuramos lá no docker-compose.yml
        RiakNode node = new RiakNode.Builder()
                .withRemoteAddress("127.0.0.1")
                .withRemotePort(8087)
                .build();

        RiakCluster cluster = new RiakCluster.Builder(node).build();
        cluster.start();
        this.client = new RiakClient(cluster);
        
        System.out.println("✅ Conexão com o Banco Riak KV estabelecida com sucesso!");
    }

    // O @PreDestroy desliga a conexão suavemente quando você para o sistema.
    @PreDestroy
    public void fecharConexao() {
        if (this.client != null) {
            this.client.shutdown();
        }
    }

    // ==========================================
    // MÉTODO DE SALVAR (CREATE / UPDATE)
    // ==========================================
    public Peca salvarPeca(Peca peca) throws Exception {
        // 1. Criamos a "Localização" exata no banco: Balde "pecas" + a Chave (SKU)
        Location localizacao = new Location(bucketPecas, peca.getSku());

        // 2. Preparamos a ordem de serviço para guardar o objeto Java lá dentro
        StoreValue guardarComando = new StoreValue.Builder(peca)
                .withLocation(localizacao)
                .build();

        // 3. Executamos a ordem no banco. (O cliente Riak transforma o Java em JSON sozinho!)
        client.execute(guardarComando);
        
        return peca;
    }

    // ==========================================
    // MÉTODO DE BUSCAR (READ)
    // ==========================================
    public Peca buscarPecaPorSku(String sku) throws Exception {
        // 1. Dizemos onde a peça está: Balde "pecas" + a Chave (SKU)
        Location localizacao = new Location(bucketPecas, sku);

        // 2. Preparamos a ordem para buscar o valor
        FetchValue buscarComando = new FetchValue.Builder(localizacao).build();

        // 3. Executamos a busca
        FetchValue.Response resposta = client.execute(buscarComando);

        // 4. Verificamos se a peça existe no banco. Se sim, retornamos o objeto Peca.
        if (resposta.hasValues()) {
            return resposta.getValue(Peca.class);
        } else {
            // Se não encontrou, retornamos nulo para o Controlador tratar o erro.
            return null; 
        }
    }
}