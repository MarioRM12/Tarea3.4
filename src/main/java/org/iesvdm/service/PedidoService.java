package org.iesvdm.service;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.domain.Cliente;
import org.iesvdm.domain.Comercial;
import org.iesvdm.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PedidoService {
    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private ComercialDAO comercialDAO;

    public List<Cliente> getAllClientes() {return this.clienteDAO.getAll();}
    public List<Comercial> getAllComercial() { return this.comercialDAO.getAll(); }

    public List<Pedido> getAll() {
        return this.pedidoDAO.getAll();
    }

    public List<Pedido> listAll() {return this.pedidoDAO.getAll();}

    public Pedido one(int id) {
        Optional<Pedido> pedidoOptional =this.pedidoDAO.find(id);
        if (pedidoOptional.isPresent()) return pedidoOptional.get();
        return null;
    }


    public void newpedido(Pedido pedido) {

        pedidoDAO.create(pedido);

    }

    public void replacePedido(Pedido pedido) {

        pedidoDAO.update(pedido);

    }

    public void create(Pedido pedido) {

        this.pedidoDAO.create(pedido);
        log.info("Creado pedido con id {}", pedido.getId());

    }

    public void delete(int id) {

        this.pedidoDAO.delete(id);
        log.info("Borrado pedido con id {}", id);

    }

    public List<Pedido> getAllPedidosByIdComercial(int id){

        List<Pedido> listPedido = this.pedidoDAO.getAll().stream()
                .filter(pedido -> pedido.getComercial().getId() == id)
                .toList();

        return listPedido;
    }

    public void replace(Pedido pedido) {

        if (pedido.getComercial().getId() > 0) this.pedidoDAO.update(pedido);
        else this.pedidoDAO.updateSinComercial(pedido);
        log.info("Actualizado pedido con id {}", pedido.getId());
        log.debug("Pedido Actualizaro:\n{}", pedido.toString());
    }


    public List<Cliente> clientesByIdPedido(int id) {
        return this.pedidoDAO.getAllClientesByIdPedido(id);
    }


}
