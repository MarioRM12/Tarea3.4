package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesvdm.domain.Cliente;
import org.iesvdm.domain.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
//Un Repository es un componente y a su vez un estereotipo de Spring 
//que forma parte de la ‘capa de persistencia’.
@Repository
public class ClienteDAOImpl implements ClienteDAO {

	 //Plantilla jdbc inyectada automáticamente por el framework Spring, gracias a la anotación @Autowired.
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	
	/**
	 * Inserta en base de datos el nuevo Cliente, actualizando el id en el bean Cliente.
	 */
	@Override	
	public synchronized void create(Cliente cliente) {
		
							//Desde java15+ se tiene la triple quote """ para bloques de texto como cadenas.
		String sqlInsert = """
							INSERT INTO cliente (nombre, apellido1, apellido2, ciudad, categoría) 
							VALUES  (     ?,         ?,         ?,       ?,         ?)
						   """;
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Con recuperación de id generado
		int rows = jdbcTemplate.update(
				connection -> {
					PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
					int idx = 1;
					ps.setString(idx++, cliente.getNombre());
					ps.setString(idx++, cliente.getApellido1());
					ps.setString(idx++, cliente.getApellido2());
					ps.setString(idx++, cliente.getCiudad());
					ps.setInt(idx, cliente.getCategoria());
					return ps;
				},keyHolder);
		
		cliente.setId(keyHolder.getKey().intValue());
		
		//Sin recuperación de id generado
//		int rows = jdbcTemplate.update(sqlInsert,
//							cliente.getNombre(),
//							cliente.getApellido1(),
//							cliente.getApellido2(),
//							cliente.getCiudad(),
//							cliente.getCategoria()
//					);

		log.info("Insertados {} registros.", rows);
	}

	/**
	 * Devuelve lista con todos loa Clientes.
	 */
	@Override
	public List<Cliente> getAll() {
		
		List<Cliente> listCli = jdbcTemplate.query(
                "SELECT * FROM cliente",
                (rs, rowNum) -> new Cliente(rs.getInt("id"),
                						 	rs.getString("nombre"),
                						 	rs.getString("apellido1"),
                						 	rs.getString("apellido2"),
                						 	rs.getString("ciudad"),
                						 	rs.getInt("categoría")
                						 	)
        );
		
		log.info("Devueltos {} registros.", listCli.size());
		
        return listCli;
        
	}

	/**
	 * Devuelve Optional de Cliente con el ID dado.
	 */
	@Override
	public Optional<Cliente> find(int id) {
		
		Cliente cli =  jdbcTemplate
				.queryForObject("SELECT * FROM cliente WHERE id = ?"
								, (rs, rowNum) -> new Cliente(rs.getInt("id"),
            						 						rs.getString("nombre"),
            						 						rs.getString("apellido1"),
            						 						rs.getString("apellido2"),
            						 						rs.getString("ciudad"),
            						 						rs.getInt("categoría")) 
								, id);
		
		if (cli != null) {
			return Optional.of(cli);}
		else { 
			log.info("Cliente no encontrado.");
			return Optional.empty(); }
        
	}
	/**
	 * Actualiza Cliente con campos del bean Cliente según ID del mismo.
	 */
	@Override
	public void update(Cliente cliente) {
		
		int rows = jdbcTemplate.update("""
										UPDATE cliente SET 
														nombre = ?, 
														apellido1 = ?, 
														apellido2 = ?,
														ciudad = ?,
														categoría = ?  
												WHERE id = ?
										""", cliente.getNombre()
										, cliente.getApellido1()
										, cliente.getApellido2()
										, cliente.getCiudad()
										, cliente.getCategoria()
										, cliente.getId());
		
		log.info("Update de Cliente con {} registros actualizados.", rows);
    
	}

	/**
	 * Borra Cliente con ID proporcionado.
	 */
	@Override
	public void delete(long id) {
		
		int rows = jdbcTemplate.update("DELETE FROM cliente WHERE id = ?", id);
		
		log.info("Delete de Cliente con {} registros eliminados.", rows);		
		
	}

	@Override
	public List<Cliente> getAllOrd() {
		List<Cliente> listCli = jdbcTemplate.query(
				"""
						SELECT c.*, COALESCE(SUM(p.total), 0) AS total
						FROM cliente c
						LEFT JOIN pedido p ON c.id = p.id_cliente
						GROUP BY c.id
						ORDER BY total DESC;
						""",
				(rs, rowNum) -> new Cliente(rs.getInt("id"),rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("ciudad"), rs.getInt("categoría"))
		);
		return listCli;
	}
	@Override
	public List<Double> getAllSuma(){
		List<Double> listsumas = jdbcTemplate.query(
				"""
                        SELECT id_cliente, SUM(totaL) AS total
                        FROM pedido
                        GROUP BY id_cliente
                        ORDER BY total DESC;
                        """,
				(rs, rowNum) -> (rs.getDouble("total"))
		);

		return listsumas;
	}

	@Override
	public List<Comercial> getAllByCliente(int id) {
		String sql = """
          SELECT DISTINCT c.*
          FROM cliente cl
          LEFT JOIN pedido p ON cl.id = p.id_cliente
          LEFT JOIN comercial c ON p.id_comercial = c.id
          WHERE cl.id = ?
          
        """;

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			Comercial comercial = new Comercial();
			comercial.setId(rs.getInt("id"));
			comercial.setNombre(rs.getString("nombre"));
			comercial.setApellido1(rs.getString("apellido1"));
			comercial.setApellido2(rs.getString("apellido2"));
			comercial.setComision(rs.getFloat("comisión"));
			return comercial;
		}, id);

	}

	@Override
	public int conteoUltimoTrimestre(Cliente  cliente) {

		String sql = """
                SELECT COUNT(*)
                FROM pedido
                JOIN comercial  ON comercial.id = pedido.id_comercial
                WHERE id_cliente = ?
                AND fecha >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH)
                """;

		return jdbcTemplate.queryForObject(sql, Integer.class, cliente.getId());
	}

	@Override
	public int conteoUltimoSemestre(Cliente cliente) {

		String sql = """
                SELECT COUNT(*)
                FROM pedido
                JOIN comercial  ON comercial.id = pedido.id_comercial
                WHERE id_cliente = ?
                AND fecha >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)
                """;

		return jdbcTemplate.queryForObject(sql, Integer.class, cliente.getId());
	}

	@Override
	public int conteoUltimoAnio(Cliente cliente) {

		String sql = """
                SELECT COUNT(*)
                FROM pedido
                JOIN comercial ON comercial.id = pedido.id_comercial
                WHERE id_cliente = ?
                AND fecha >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)
                """;

		return jdbcTemplate.queryForObject(sql, Integer.class, cliente.getId());
	}

	@Override
	public int conteoUltimoLustro(Cliente cliente) {

		String sql = """
                SELECT COUNT(*)
                FROM pedido
                JOIN comercial ON comercial.id = pedido.id_comercial
                WHERE id_cliente = ?
                AND fecha >= DATE_SUB(CURDATE(), INTERVAL 5 YEAR)
                """;

		return jdbcTemplate.queryForObject(sql, Integer.class, cliente.getId());
	}
	
}
