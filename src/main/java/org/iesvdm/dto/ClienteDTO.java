package org.iesvdm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesvdm.domain.Comercial;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String ciudad;
    private int categoria;
    private List<Comercial> comerciales;
    private int conteoUltimoTrimestre;
    private int conteoUltimoSemestre;
    private int conteoUltimoAnio;
    private int conteoUltimoLustro;
}