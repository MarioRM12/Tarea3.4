package org.iesvdm.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

public class ComercialDTO {
    private int id;
    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar en blanco")
    @Length(min = 1 , max = 30, message = "El máximo son 30 caracteres")
    private String nombre;
    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar en blanco")
    @Length(min = 1 , max = 30, message = "El máximo son 30 caracteres")
    private String apellido1;
    private String apellido2;
    @DecimalMin(value = "0.276", message = "Debe ser mayor a  0.276")
    @DecimalMax(value = "0.946", message = "Debe ser menor a  0.946")
    private BigDecimal comisión;
    @NotNull
    @NotBlank
    private int cantidadPedidos;

    private List<ComercialDTO> comerciales;


}