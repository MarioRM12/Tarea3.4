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

	@Min(value=0, message = "{msg.valid.min}")
	private long id;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Max(value = 30, message = "{msg.valid.max}")
	private String nombre;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Max(value = 30, message = "{msg.valid.max}")
	private String apellido1;

	private String apellido2;

	@NotBlank(message = "{msg.valid.not.blank}")
	@Max(value = 50, message = "{msg.valid.max}")
	private String ciudad;

	@NotNull(message = "{msg.valid.not.null}")
	@Min(value = 100, message = "{msg.valid.min}")
	@Max(value = 1000, message = "{msg.valid.max}")
	private int categoria;

	public Cliente() {
	}
}
