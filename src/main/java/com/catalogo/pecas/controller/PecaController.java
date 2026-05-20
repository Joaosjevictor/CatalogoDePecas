package com.catalogo.pecas.controller;

import com.catalogo.pecas.model.Peca;
import com.catalogo.pecas.service.PecaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pecas")
@CrossOrigin(origins = "*") 
public class PecaController {

    private final PecaService pecaService;
    public PecaController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Peca> buscarPeca(@PathVariable String sku) {
        try {
            Peca pecaEncontrada = pecaService.buscarPecaPorSku(sku.toLowerCase());

            if (pecaEncontrada != null) {
                return ResponseEntity.ok(pecaEncontrada);
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
