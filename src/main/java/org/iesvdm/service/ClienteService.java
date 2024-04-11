package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.domain.Cliente;
import org.iesvdm.domain.Comercial;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	private ClienteDAO clienteDAO;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteService(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	
	public List<Cliente> listAll() {
		
		return clienteDAO.getAll();
		
	}

	public Cliente one(Integer id) {
		Optional<Cliente> optCli = clienteDAO.find(id);
		if (optCli.isPresent())
			return optCli.get();
		else
			return null;
	}

	public List<Cliente> listAllOrdPorTotal(){
		return clienteDAO.getAllOrd();
	}
	public List<Double> sumasOrdenadas(){
		return clienteDAO.getAllSuma();
	}

	public void newcliente(Cliente cliente) {

		clienteDAO.create(cliente);

	}

	public void replaceCliente(Cliente cliente) {

		clienteDAO.update(cliente);

	}

	public void deleteCliente(int id) {

		clienteDAO.delete(id);

	}

	public List<Comercial> listadoComerciales(int id){
		return clienteDAO.getAllByCliente(id);
	}
	public int calcularConteoPedidosUltimoTrimestre(Cliente cliente) {
		return clienteDAO.conteoUltimoTrimestre(cliente);
	}

	public int calcularConteoPedidosUltimoSemestre(Cliente cliente) {
		return clienteDAO.conteoUltimoSemestre(cliente);
	}

	public int calcularConteoPedidosUltimoAnio(Cliente cliente) {
		return clienteDAO.conteoUltimoAnio(cliente);
	}

	public int calcularConteoPedidosUltimoLustro(Cliente cliente) {
		return clienteDAO.conteoUltimoLustro(cliente);
	}


}
