package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.domain.Cliente;
import org.iesvdm.domain.Comercial;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {
	
	private ClienteService clienteService;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping("/clientes") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
				
		return "clientes";
		
	}

	@GetMapping("/cliente/{id}")
	public String detalle(Model model, @PathVariable Integer id ) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		List<Comercial> comerciales = clienteService.listadoComerciales(id);

		// Mapear la lista de comerciales a una lista de ComercialDTO
		int conteotrimestre =clienteService.calcularConteoPedidosUltimoTrimestre(cliente);

		model.addAttribute("conteoUltimoTrimestre", conteotrimestre);

		int conteoSemestre=clienteService.calcularConteoPedidosUltimoSemestre(cliente);

		model.addAttribute("conteoUltimoSemestre", conteoSemestre);

		int conteoAnio =clienteService.calcularConteoPedidosUltimoAnio(cliente);

		model.addAttribute("conteoUltimoAnio", conteoAnio);
		int conteoLustro =clienteService.calcularConteoPedidosUltimoLustro(cliente);

		model.addAttribute("conteoUltimoLustro", conteoLustro);

		// Agregar la lista de comerciales al modelo
		model.addAttribute("comerciales", comerciales);

		return "detalle-cliente";

	}
	@GetMapping("/clientes/crear")
	public String crear(Model model) {

		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);

		return "crear-cliente";

	}

	@PostMapping("/clientes/crear")
	public RedirectView submitCrear(@ModelAttribute("cliente") Cliente cliente) {

		clienteService.newcliente(cliente);

		return new RedirectView("/clientes") ;

	}


	@GetMapping("/clientes/editar/{id}")
	public String editar(Model model, @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		return "editar-cliente";

	}


	@PostMapping("/clientes/editar/{id}")
	public RedirectView submitEditar(@ModelAttribute("cliente") Cliente cliente) {

		clienteService.replaceCliente(cliente);

		return new RedirectView("/clientes");
	}

	@PostMapping("/clientes/borrar/{id}")
	public RedirectView submitBorrar(@PathVariable Integer id) {

		clienteService.deleteCliente(id);

		return new RedirectView("/clientes");
	}
	

}
