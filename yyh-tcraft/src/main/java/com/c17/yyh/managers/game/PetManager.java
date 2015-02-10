package com.c17.yyh.managers.game;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.db.dao.IConfigDao;
import com.c17.yyh.db.entities.adventure.Pet;

@Service("petManager")
public class PetManager {

    @Autowired
    private IConfigDao configService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<Pet> pets;

    @PostConstruct
    protected void initialize() {
        loadPets();
    }

    public void loadPets() {
        pets = configService.getPets();
        logger.info("Loaded {} pets", pets.size());
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Pet getPetById(int pet_id) {
        for (Pet pet : pets) {
            if (pet.getId() == pet_id) {
                return pet;
            }
        }
        return null;
    }
}
