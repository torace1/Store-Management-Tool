package com.smt.project.service;

import com.smt.project.dto.IdentityDto;
import com.smt.project.dto.ProfileDto;
import com.smt.project.exception.SmtException;
import com.smt.project.mapper.ProfileMapper;
import com.smt.project.model.*;
import com.smt.project.repository.ProfileRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final ProductService productService;

    public void sendOrder(String email) {
        Profile profile = getProfileWithIdentity(email);
        Cart cart = profile.getShoppingCart();
        List<Product> products = cart.getProducts();
        List<Order> orders = profile.getOrders();
        Order order = new Order(cart.getPriceSum(), profile, products);
        orders.add(order);
        cart.setProducts(Collections.emptyList());
        cart.setPriceSum(0.0);
        profile.setShoppingCart(cart);
        profile.setOrders(orders);
        profileRepository.save(profile);
    }

    public void addProductToCart(UUID productId, String email) {
        Product product = productService.getProductById(productId);
        Profile profile = getProfileWithIdentity(email);
        Cart cart = profile.getShoppingCart();
        List<Product> products = cart.getProducts();
        products.add(product);
        cart.setProducts(products);
        cart.setPriceSum(cart.getPriceSum() + product.getPrice());
        profile.setShoppingCart(cart);
        profileRepository.save(profile);
    }

    public void removeProductFromCart(UUID productId, String email) {
        Product product = productService.getProductById(productId);
        Profile profile = getProfileWithIdentity(email);
        Cart cart = profile.getShoppingCart();
        List<Product> products = cart.getProducts();
        products.remove(product);
        cart.setProducts(products);
        cart.setPriceSum(cart.getPriceSum() - product.getPrice());
        profile.setShoppingCart(cart);
        profileRepository.save(profile);
    }

    public void removeAllProductsFromCart(String email) {
        Profile profile = getProfileWithIdentity(email);
        Cart cart = profile.getShoppingCart();
        cart.setProducts(Collections.emptyList());
        cart.setPriceSum(0.0);
        profile.setShoppingCart(cart);
        profileRepository.save(profile);
    }

    public void createProfile(ProfileDto profileDto) {
        if (userService.getIdentityWithEmail(profileDto.getEmail()) != null) {
            throw new SmtException(400, String.format("Profile with email {%s} already exists", profileDto.getEmail()));
        }
        Profile profile = ProfileMapper.profileDtoToProfile(profileDto);
        User user = new User();
        user.setUsername(profileDto.getEmail());
        Cart shoppingCart = new Cart();
        shoppingCart.setPriceSum(0.0);
        user.setProfile(profile);
        shoppingCart.setProfile(profile);
        profile.setUser(user);
        profile.setShoppingCart(shoppingCart);
        profileRepository.save(profile);
    }

    public Profile getProfileWithIdentity(String email) {
        return userService.getIdentityWithEmail(email).getProfile();
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public void updateProfile(ProfileDto profileDto) {
        Profile updatedProfile = getProfileWithIdentity(profileDto.getEmail());
        updatedProfile.setFirstName(profileDto.getFirstName());
        updatedProfile.setLastName(profileDto.getLastName());
        updatedProfile.setAge(profileDto.getAge());

        profileRepository.save(updatedProfile);
    }

    public void deleteProfile(String email) {
        profileRepository.delete(getProfileWithIdentity(email));
    }
}
