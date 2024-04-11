package org.iesvdm.mapper;

import javax.annotation.processing.Generated;
import org.iesvdm.domain.Cliente;
import org.iesvdm.domain.Comercial;
import org.iesvdm.domain.Pedido;
import org.iesvdm.dto.PedidoFormDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-11T10:52:57+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Override
    public PedidoFormDTO pedidoAPedidoFormDTO(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoFormDTO pedidoFormDTO = new PedidoFormDTO();

        pedidoFormDTO.setIdCliente( (int) pedidoClienteId( pedido ) );
        pedidoFormDTO.setIdComercial( pedidoComercialId( pedido ) );
        if ( pedido.getId() != null ) {
            pedidoFormDTO.setId( pedido.getId() );
        }
        pedidoFormDTO.setTotal( pedido.getTotal() );
        pedidoFormDTO.setFecha( pedido.getFecha() );

        return pedidoFormDTO;
    }

    @Override
    public Pedido pedidoFormDTOAPedido(PedidoFormDTO pedidoFormDTO) {
        if ( pedidoFormDTO == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setCliente( pedidoFormDTOToCliente( pedidoFormDTO ) );
        pedido.setComercial( pedidoFormDTOToComercial( pedidoFormDTO ) );
        pedido.setId( pedidoFormDTO.getId() );
        pedido.setTotal( pedidoFormDTO.getTotal() );
        pedido.setFecha( pedidoFormDTO.getFecha() );

        return pedido;
    }

    private long pedidoClienteId(Pedido pedido) {
        if ( pedido == null ) {
            return 0L;
        }
        Cliente cliente = pedido.getCliente();
        if ( cliente == null ) {
            return 0L;
        }
        long id = cliente.getId();
        return id;
    }

    private int pedidoComercialId(Pedido pedido) {
        if ( pedido == null ) {
            return 0;
        }
        Comercial comercial = pedido.getComercial();
        if ( comercial == null ) {
            return 0;
        }
        int id = comercial.getId();
        return id;
    }

    protected Cliente pedidoFormDTOToCliente(PedidoFormDTO pedidoFormDTO) {
        if ( pedidoFormDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( pedidoFormDTO.getIdCliente() );

        return cliente;
    }

    protected Comercial pedidoFormDTOToComercial(PedidoFormDTO pedidoFormDTO) {
        if ( pedidoFormDTO == null ) {
            return null;
        }

        Comercial comercial = new Comercial();

        comercial.setId( pedidoFormDTO.getIdComercial() );

        return comercial;
    }
}
