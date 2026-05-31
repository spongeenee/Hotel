package com.example.hotel.service;

import com.example.hotel.dao.HuespedDAO;
import com.example.hotel.models.Huesped;

import java.util.ArrayList;
import java.util.List;

public class HuespedService extends GenericService<Huesped>{
    public HuespedService() {
        this.dao = new HuespedDAO();
    }

    public int registrar(Huesped huesped) {
        if (huesped != null)
            return dao.registrar(huesped);
        else return 0;
    }

    public int actualizar(Huesped huesped) {
        if (huesped != null)
            return dao.actualizar(huesped);
        else return 0;
    }

    public int eliminar(Huesped huesped) {
        if (huesped != null)
            return dao.eliminar(huesped.ID());
        else return 0;
    }

    public List<Huesped> listar() {
        return dao.listar();
    }

    public List<Huesped> listarPorNombre(String nombre) {
        List<Huesped> filtrado = new ArrayList<>();
        for (Huesped huesped : listar()) {
            if(huesped.nombre().contains(nombre))
                filtrado.add(huesped);
        }
        return filtrado;
    }

    public List<Huesped> listarPorMail(String mail) {
        List<Huesped> filtrado = new ArrayList<>();
        for (Huesped huesped : listar()) {
            if(huesped.mail().contains(mail))
                filtrado.add(huesped);
        }
        return filtrado;
    }
}
