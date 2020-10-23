/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package py.una.pol.personas.service;


import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.dao.PersonaDAO;
import py.una.pol.personas.model.Persona;

import java.util.List;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class PersonaService {

    @Inject
    private Logger log;

    @Inject
    private PersonaDAO dao;

    public void crear(Persona p) throws Exception {
        log.info("Creando Persona: " + p.getNombre() + " " + p.getApellido());
        try {
        	dao.insertar(p);
        }catch(Exception e) {
        	log.severe("ERROR al crear persona: " + e.getLocalizedMessage() );
        	throw e;
        }
        log.info("Persona creada con éxito: " + p.getNombre() + " " + p.getApellido() );
    }
    
    public List<Persona> seleccionar() {
    	return dao.seleccionar();
    }
    
    public Persona seleccionarPorCedula(long cedula) {
    	return dao.seleccionarPorCedula(cedula);
    }
    
    public long borrar(long cedula) throws Exception {
    	return dao.borrar(cedula);
    }
}
