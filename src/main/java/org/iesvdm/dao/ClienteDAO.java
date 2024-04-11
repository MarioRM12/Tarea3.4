package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;

import org.iesvdm.domain.Cliente;
import org.iesvdm.domain.Comercial;

public interface ClienteDAO {

	public void create(Cliente cliente);
	
	public List<Cliente> getAll();
	public Optional<Cliente>  find(int id);
	
	public void update(Cliente cliente);
	
	public void delete(long id);

	List<Comercial> getAllByCliente(int id);

	List<Cliente> getAllOrd();

	List<Double> getAllSuma();

	int conteoUltimoTrimestre(Cliente cliente);

	int conteoUltimoSemestre(Cliente cliente);

	int conteoUltimoAnio(Cliente cliente);

	int conteoUltimoLustro(Cliente cliente);
	
}
