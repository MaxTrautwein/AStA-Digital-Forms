package de.PSWTM.DigitalForms.controller;

import de.PSWTM.DigitalForms.api.FavouritesApiDelegate;
import de.PSWTM.DigitalForms.model.Favourite;
import de.PSWTM.DigitalForms.repository.FavouriteRepository;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Void> favouritesFavIdDelete(String favId) {
        repository.deleteById(favId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Favourite> favouritesFavIdGet(String favId) {
        return FavouritesApiDelegate.super.favouritesFavIdGet(favId);
    }

    @Override
    public ResponseEntity<List<Favourite>> favouritesGet() {
        List<Favourite> favList = repository.findAllOwnedFavouritess(getUserID());
        return ResponseEntity.ok(favList);
    }

    @Override
    public ResponseEntity<Favourite> favouritesPost(Favourite favourite) {
        if(repository.findDuplicates(getUserID(), favourite.getFormId()).isEmpty()) {;
        favourite.setOwner(getUserID());
        favourite = repository.save(favourite);
        return ResponseEntity.status(HttpStatus.CREATED).body(favourite);
        }
        return ResponseEntity.badRequest().build();
    }
}
