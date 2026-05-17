package com.catalogo.pecas.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Peca {

    private String sku;

    private String nomePeca;
    private String marca;
    private String categoria;
   
    private List<Aplicacao> aplicacoes;

}
