package org.iesvdm.domain;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
//La anotación @Data de lombok proporcionará el código de: 
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
public class Cliente {

	private long id;

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 30, message = "El nombre no puede tener más de 30 caracteres")
	private String nombre;

	@NotBlank(message = "El primer apellido es obligatorio")
	@Size(max = 30, message = "El primer apellido no puede tener más de 30 caracteres")
	private String apellido1;

	private String apellido2;

	@NotBlank(message = "La ciudad es obligatoria")
	@Size(max = 50, message = "La ciudad no puede tener más de 50 caracteres")
	private String ciudad;

	@NotNull(message = "La categoría es obligatoria")
	@Min(value = 100, message = "La categoría debe ser mayor o igual a 100")
	@Max(value = 1000, message = "La categoría debe ser menor o igual a 1000")
	private int categoria;

	public Cliente() {
	}
}
