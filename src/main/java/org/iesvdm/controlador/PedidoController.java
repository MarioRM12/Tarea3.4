package org.iesvdm.controlador;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
@Controller
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/pedidos")
    public String listar(Model model) {

        List<Pedido> listaPedidos =  pedidoService.getAll();
        model.addAttribute("listaPedidos", listaPedidos);

        return "pedidos";

    }

    @GetMapping("/pedido/{id}")
    public String detalle(Model model, @PathVariable Integer id ) {

        Pedido pedido = pedidoService.one(id);
        model.addAttribute("pedido", pedido);

        return "detalle-cliente";

    }
    @GetMapping("/pedido/crear")
    public String crear(Model model) {

        Pedido pedido = new Pedido();
        model.addAttribute("pedido", pedido);

        return "crear-pedido";

    }

    @PostMapping("/pedido/crear")
    public RedirectView submitCrear(@ModelAttribute("pedido") Pedido pedido) {

        pedidoService.newpedido(pedido);

        return new RedirectView("/pedidos") ;

    }


    @GetMapping("/pedido/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Pedido pedido = pedidoService.one(id);
        model.addAttribute("pedido", pedido);

        return "editar-pedido";

    }


    @PostMapping("/pedido/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("pedido") Pedido pedido) {

        pedidoService.replacePedido(pedido);

        return new RedirectView("/clientes");
    }

    @PostMapping("/pedido/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        pedidoService.delete(id);

        return new RedirectView("/pedidos");
    }


}
