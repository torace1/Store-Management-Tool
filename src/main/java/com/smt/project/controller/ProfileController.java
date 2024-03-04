package com.smt.project.controller;


import com.smt.project.dto.ProfileDto;
import com.smt.project.model.Profile;
import com.smt.project.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/profiles")
@RestController
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("/send-order")
    @Secured("USER")
    public ResponseEntity<Void> sendOrder(@RequestParam String email) {
        profileService.sendOrder(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-product/{productId}")
    @Secured("USER")
    public ResponseEntity<Void> addProductToCart(@PathVariable UUID productId, @RequestParam String email) {
        profileService.addProductToCart(productId, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove-product/{productId}")
    @Secured("USER")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable UUID productId, @RequestParam String email) {
        profileService.removeProductFromCart(productId, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove-all-products")
    @Secured("USER")
    public ResponseEntity<Void> removeAllProductsFromCart(@RequestParam String email) {
        profileService.removeAllProductsFromCart(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @Secured("USER")
    public ResponseEntity<Void> createProfile(@RequestBody ProfileDto profileDto) {
        profileService.createProfile(profileDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/byEmail")
    public ResponseEntity<Profile> getProfileOfUser(@RequestParam String email) {
        return new ResponseEntity<>(profileService.getProfileOfUser(email), HttpStatus.OK);
    }

    @GetMapping()
    @Secured("ADMIN")
    public ResponseEntity<List<Profile>> getAllUsers() {
        return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatus.OK);
    }

    @PutMapping
    @Secured("USER")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileDto profileDto) {
        profileService.updateProfile(profileDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @Secured("USER")
    public ResponseEntity<Void> deleteProfile(@RequestParam String email) {
        profileService.deleteProfile(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
