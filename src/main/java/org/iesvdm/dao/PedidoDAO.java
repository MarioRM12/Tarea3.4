package org.iesvdm.dao;

import org.iesvdm.domain.Cliente;
import org.iesvdm.domain.Comercial;
import org.iesvdm.domain.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO {

    public void create(Pedido pedido);

    Optional<Cliente> findClienteBy(int pedidoId);

    Optional<Comercial> findComercialBy(int pedidoId);

    List<Cliente> getAllClientesByIdPedido(int pedidoId);

    public List<Pedido> getAll();
    public Optional<Pedido> find(int id);

    public void update(Pedido pedido);

    public void delete(long id);
    public void updateSinComercial(Pedido pedido);

    public List<Pedido> listaPedidosIdComercial (int id);

    public List<Pedido> listaPedidosPorComercial(Comercial comercial);

}
