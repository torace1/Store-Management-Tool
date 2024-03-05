package com.smt.project.controller;


import com.smt.project.dto.ProfileDto;
import com.smt.project.model.Profile;
import com.smt.project.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Void> sendOrder(HttpServletRequest request) {
        profileService.sendOrder(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-product/{productId}")
    @Secured("USER")
    public ResponseEntity<Void> addProductToCart(@PathVariable UUID productId, @RequestParam int noOfPieces, HttpServletRequest request) {
        profileService.addProductToCart(productId,noOfPieces, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove-product/{productId}")
    @Secured("USER")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable UUID productId, HttpServletRequest request) {
        profileService.removeProductFromCart(productId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove-all-products")
    @Secured("USER")
    public ResponseEntity<Void> removeAllProductsFromCart(HttpServletRequest request) {
        profileService.removeAllProductsFromCart(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @Secured("USER")
    public ResponseEntity<Void> createProfile(@RequestBody ProfileDto profileDto,HttpServletRequest request) {
        profileService.createProfile(request,profileDto);
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
    public ResponseEntity<Void> deleteProfile(HttpServletRequest request) {
        profileService.deleteProfile(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
