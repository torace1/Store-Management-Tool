package com.smt.project.service;

import com.smt.project.dto.ProfileDto;
import com.smt.project.model.Profile;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface ProfileService {
     void sendOrder(HttpServletRequest request);

    void addProductToCart(UUID productId,int noOfPieces, HttpServletRequest request);

     void removeProductFromCart(UUID productId, HttpServletRequest request);

     void removeAllProductsFromCart(HttpServletRequest request);

     void createProfile(HttpServletRequest request,ProfileDto profileDto);

     Profile getProfileOfUser(String email);

     List<Profile> getAllProfiles();

     void updateProfile(ProfileDto profileDto);

     void deleteProfile(HttpServletRequest request);

}
