package org.iesvdm.controlador;
import java.util.List;

import org.iesvdm.domain.Comercial;
import org.iesvdm.domain.Pedido;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;
    @Autowired
    private PedidoService pedidoService;

    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/comerciales") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Comercial> listaComercial =  comercialService.listAll();
        model.addAttribute("listaComerciales", listaComercial);

        return "comerciales";

    }

    @GetMapping("/comercial/{id}")
    public String detalle(Model model, @PathVariable Integer id ) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);
        List<Pedido> listPedido = pedidoService.getAllPedidosByIdComercial(id);
        model.addAttribute("listPedido", listPedido);
        //List<Double> TotalMedia =

        return "detalle-comercial";
    }

    @GetMapping("/comerciales/crear")
    public String crear(Model model) {

        Comercial comercial = new Comercial();
        model.addAttribute("comercial", comercial);

        return "crear-comercial";

    }

    @PostMapping("/comerciales/crear")
    public RedirectView submitCrear(@ModelAttribute("comerciales") Comercial comercial) {

        comercialService.newcomercial(comercial);

        return new RedirectView("/comerciales") ;

    }


    @GetMapping("/comerciales/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        return "editar-comerciales";

    }


    @PostMapping("/comerciales/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.replaceComercial(comercial);

        return new RedirectView("/comerciales");
    }

    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        comercialService.deleteComercial(id);

        return new RedirectView("/comerciales");
    }

}
