/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.SpecialityRepository;
import org.springframework.samples.petclinic.repository.VetRepository;

/**
 * PetClinic Spring Boot Application.
 * 
 * @author Dave Syer
 *
 */
@SpringBootApplication
public class PetClinicApplication {
	
	private static final Logger log = LoggerFactory.getLogger(PetClinicApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PetClinicApplication.class, args);
    }
    
    @Bean
	public CommandLineRunner demoVetRepository(VetRepository vetRepository, SpecialityRepository specialityRepository) {
		return (args) -> {
			log.info("*****************************************************");
			log.info("BOOTCAMP - Spring y Spring Data - vetRepository");
			log.info("*****************************************************");
			
			//TODO Añade aquí tu código

		    log.info("Creamos un objeto Vet");
		    Vet vet = new Vet();
		    vet.setFirstName("Sergio");
		    vet.setLastName("Raposo Vargas");

		    log.info("Persistimos en BBDD");
		    vet = vetRepository.save(vet);

		    log.info("Comprobamos que se ha creado correctamente");
		    Vet vetAux = vetRepository.findOne(vet.getId());
		    log.info(vetAux.toString());

		    log.info("Editamos el objeto y añadimos una Speciality");
		    Specialty s = specialityRepository.findOne(1);
		    vet.addSpecialty(s);
		    vet = vetRepository.save(vet);
		    log.info(vet.toString());

		    log.info("Listamos todos los veterinarios");
		    for(Vet v: vetRepository.findAll()){
		    	log.info("Vet: "+v.toString());
		    }
		    
			log.info("Obtener una lista de Vets filtrando por lastName");
			List<Vet> vets = vetRepository.findByLastName("Rasposo Vargas");
			for(Vet v: vets)
				log.info("Vet: "+v.toString());
			
			log.info("Obtener una lista de Vets filtrando por firstName y lastName");
			List<Vet> vets2 = vetRepository.findByLastNameAndFirstName("Sergio", "Rasposo Vargas");
			for(Vet v: vets2)
				log.info("Vet: "+v.toString());
		    
			log.info("Obtener una lista de Vets filtrando por firstName o lastName");
			List<Vet> vets3 = vetRepository.findByLastNameOrFirstName("Sergio", "Sergio");
			for(Vet v: vets3)
				log.info("Vet: "+v.toString());
			
			log.info("Obtener una lista de veterinarios dada una especialidad.");
			for(Vet v: vetRepository.findBySpeciality("radiology"))
				log.info("Vet: "+v.toString());
			
		};
	}
    
    @Bean
	public CommandLineRunner demoOwnerRepository(OwnerRepository ownerRepository) {
		return (args) -> {
			log.info("*****************************************************");
		    log.info("BOOTCAMP - Spring y Spring Data - OwnerRepository");
		    log.info("*****************************************************");

		   // Empieza aquí
		   log.info("Obtener owners por nombre o apellidos, por el total o parte del parámetro indicado.");
		   for(Owner o: ownerRepository.findByFirstNameContainingOrLastNameContaining("Ras", "Ser"))
			   log.info("Vet: "+o.toString());

		   log.info("Obtener owners ordenados por apellido.");
		   for(Owner o: ownerRepository.findByOrderByLastName())
				log.info("Vet: "+o.toString());
		};
    }
    
    // Reto
    @Bean
	public CommandLineRunner demoPetRepository(PetRepository petRepository) {
		return (args) -> {
			log.info("*****************************************************");
		    log.info("BOOTCAMP - Spring y Spring Data - OwnerRepository");
		    log.info("*****************************************************");
		    
		    log.info("Obtener las mascotas nacidas en 2010 ordenadas por fecha de nacimiento ascendente.");
			   for(Pet o: petRepository.findByBirthDateBetweenOrderByBirthDateAsc(new Date(2010, 1, 1), new Date(2011, 1, 1)))
					log.info("Vet: "+o.toString());
			   
		    log.info("Crear 5 visitas nuevas para una mascota en meses distintos");
		    Pet p = petRepository.findOne(1);
		    for(int i = 0; i < 5; i++) {
			    Visit v = new Visit();
			    v.setDate(new Date(2010, 1+i, 1));
			    p.addVisit(v);
		    }
		    petRepository.save(p);
			   
		    log.info("Obtener todas las visitas para dicha mascota.");
		    List<Visit> lv = p.getVisits();
		    for(Visit v: lv)
		    	log.info("Visit: "+v.toString());
		    
		    log.info("Obtener las 3 visitas más recientes de todo el sistema.");
		    
		    
		    log.info("Devolver una lista de mascotas con un campo nuevo que indique el número de visitas realizadas en total.");
		};
    }
}
