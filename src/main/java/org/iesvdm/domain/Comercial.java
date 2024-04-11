package org.iesvdm.domain;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Comercial {

	private int id;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Max(value = 30, message = "{msg.valid.max}")
	private String nombre;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Max(value = 30, message = "{msg.valid.max}")
	private String apellido1;

	private String apellido2;

	@DecimalMin(value = "0.276", inclusive = true, message = "La comisión mínima es 0.276")
	@DecimalMax(value = "0.946", inclusive = true, message = "La comisión máxima es 0.946")
	private Float comision;

	public Comercial() {
	}
}

