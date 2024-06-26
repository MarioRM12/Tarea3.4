package org.iesvdm.service;

import java.util.List;
import java.util.Optional;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.domain.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComercialService {

    @Autowired
    private ComercialDAO ComercialDAO;

    public ComercialService(ComercialDAO comercialDAO) {
        this.ComercialDAO = comercialDAO;
    }

    public List<Comercial> listAll() {

        return ComercialDAO.getAll();

    }

    public Comercial one(Integer id) {
        Optional<Comercial> optCom = ComercialDAO.find(id);

        //2 formas de hacerlo 1º:
        /*if (optCom.isPresent())
            return optCom.get();
        else
            return null;*/

        //2º :
        return optCom.orElse(null);
    }

    public void newcomercial(Comercial comercial) {

        ComercialDAO.create(comercial);

    }

    public void replaceComercial(Comercial comercial) {

        ComercialDAO.update(comercial);

    }

    public void deleteComercial(int id) {

        ComercialDAO.delete(id);

    }


}
