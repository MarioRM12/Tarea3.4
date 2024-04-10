package org.iesvdm.controlador;

import jakarta.validation.Valid;
import org.iesvdm.domain.Cliente;
import org.iesvdm.domain.Comercial;
import org.iesvdm.domain.Pedido;
import org.iesvdm.dto.PedidoFormDTO;
import org.iesvdm.mapper.PedidoMapper;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private PedidoMapper pedidoMapper;

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

        PedidoFormDTO pedidoFormDTO = new PedidoFormDTO();
        model.addAttribute("pedidoFormDTO", pedidoFormDTO);

        List<Cliente> listaClientes = this.pedidoService.getAllClientes();
        model.addAttribute("listaClientes", listaClientes);

        List<Comercial> listaComerciales = this.pedidoService.getAllComercial();
        model.addAttribute("listaComerciales", listaComerciales);

        return "crear-pedido";

    }

    @PostMapping("/pedido/crear")
    public String submitCrear(@Valid @ModelAttribute("pedidoFormDTO") PedidoFormDTO pedidoFormDTO, BindingResult bindingResult, Model model, @PathVariable Integer id) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pedidoFormDTO", pedidoFormDTO);

            List<Cliente> listaClientes = this.pedidoService.getAllClientes();
            model.addAttribute("listaClientes", listaClientes);

            List<Comercial> listaComerciales = this.pedidoService.getAllComercial();
            model.addAttribute("listaComerciales", listaComerciales);

            return "crear-pedido";
        }

        Pedido pedido = pedidoMapper.pedidoFormDTOAPedido(pedidoFormDTO);

        pedidoService.create(pedido);

        return "redirect:/pedidos?newPedidoID="+pedido.getId();

    }


    @GetMapping("/pedido/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Pedido pedido = pedidoService.one(id);
        PedidoFormDTO pedidoFormDTO = this.pedidoMapper.pedidoAPedidoFormDTO(pedido);
        model.addAttribute("pedidoFormDTO", pedidoFormDTO);

        List<Cliente> listaClientes = this.pedidoService.getAllClientes();
        model.addAttribute("listaClientes", listaClientes);

        List<Comercial> listaComerciales = this.pedidoService.getAllComercial();
        model.addAttribute("listaComerciales", listaComerciales);

        return "editar-pedido";

    }



    @PostMapping("/pedido/editar/{id}")
    public String submitEditar(@Valid @ModelAttribute("pedidoFormDTO") PedidoFormDTO pedidoFormDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pedidoFormDTO", pedidoFormDTO);

            List<Cliente> listaClientes = this.pedidoService.getAllClientes();
            model.addAttribute("listaClientes", listaClientes);

            List<Comercial> listaComerciales = this.pedidoService.getAllComercial();
            model.addAttribute("listaComerciales", listaComerciales);

            return "editar-pedido";
        }

        Pedido pedido = this.pedidoMapper.pedidoFormDTOAPedido(pedidoFormDTO);
        pedidoService.replace(pedido);

        return "redirect:/pedidos?editPedidoID="+pedido.getId();
    }

    @PostMapping("/pedidos/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        pedidoService.delete(id);

        return new RedirectView("/pedidos?borradoPedidoID="+id);
    }


}
