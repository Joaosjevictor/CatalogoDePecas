package com.catalogo.pecas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aplicacao {

    private String montadora;
    private String veiculo;
    private String ano;
    private String motor;

}
 