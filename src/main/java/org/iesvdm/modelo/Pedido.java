package org.iesvdm.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private Integer id;
    private Double total;
    private Date fecha;
    private Comercial comercial;
    private Cliente cliente;

}
