package com.catalogo.pecas.controller;

import com.catalogo.pecas.model.Peca;
import com.catalogo.pecas.service.PecaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// O @RestController avisa que esta classe vai responder requisições da internet (APIs)
@RestController
// Define o endereço base. Tudo que chegar em http://localhost:8080/api/pecas cai aqui
@RequestMapping("/api/pecas")
// O @CrossOrigin permite que o seu arquivo index.html converse com este Java sem ser bloqueado
@CrossOrigin(origins = "*") 
public class PecaController {

    private final PecaService pecaService;

    // Injetamos o nosso Serviço aqui
    public PecaController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    // ==========================================
    // ROTA DE BUSCA (GET)
    // Exemplo de chamada: GET http://localhost:8080/api/pecas/BOSCH-SP500
    // ==========================================
    @GetMapping("/{sku}")
    public ResponseEntity<Peca> buscarPeca(@PathVariable String sku) {
        try {
            // Pede para o Service buscar a peça no Riak KV
            Peca pecaEncontrada = pecaService.buscarPecaPorSku(sku.toLowerCase());

            if (pecaEncontrada != null) {
                // Se achou, devolve a peça e um status 200 (OK)
                return ResponseEntity.ok(pecaEncontrada);
            } else {
                // Se não achou (ex: digitou o código errado), devolve 404 (Not Found)
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            // Se o banco cair ou der erro interno, devolve 500 (Erro de Servidor)
            return ResponseEntity.internalServerError().build();
        }
    }
}