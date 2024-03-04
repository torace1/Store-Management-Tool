package com.smt.project.service;

import com.smt.project.dto.ProfileDto;
import com.smt.project.model.Profile;

import java.util.List;
import java.util.UUID;

public interface ProfileService {
     void sendOrder(String email);

    void addProductToCart(UUID productId, String email);

     void removeProductFromCart(UUID productId, String email);

     void removeAllProductsFromCart(String email);

     void createProfile(ProfileDto profileDto);

     Profile getProfileOfUser(String email);

     List<Profile> getAllProfiles();

     void updateProfile(ProfileDto profileDto);

     void deleteProfile(String email);

}
