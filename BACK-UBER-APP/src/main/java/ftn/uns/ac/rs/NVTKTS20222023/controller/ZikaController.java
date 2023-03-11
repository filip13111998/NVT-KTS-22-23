package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.model.Zika;
import ftn.uns.ac.rs.NVTKTS20222023.service.ZikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping(value = "api")
public class ZikaController {

    @Autowired
    private ZikaService zs;

    @Transactional
    @GetMapping(value = "zz")
    public String saveZika(){
        System.out.println("STIGAO ZAHTEV");


        zs.save();


        return "BRAVOzzzz!";
    }

}
