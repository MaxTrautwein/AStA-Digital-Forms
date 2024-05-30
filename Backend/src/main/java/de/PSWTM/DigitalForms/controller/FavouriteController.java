package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.api.FavouritesApiDelegate;
import de.PSWTM.DigitalForms.model.Favourite;
import de.PSWTM.DigitalForms.repository.FavouriteRepository;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavouriteController implements FavouritesApiDelegate {
    @Autowired
    private FavouriteRepository repository;

    private String getUserID(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<List<Favourite>> favouritesGet() {
        return FavouritesApiDelegate.super.favouritesGet();
    }

    @Override
    public ResponseEntity<Favourite> favouritesPost(Favourite favourite) {
        return FavouritesApiDelegate.super.favouritesPost(favourite);
    }
}
