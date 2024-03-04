package com.smt.project.service.impl;

import com.smt.project.dto.ProfileDto;
import com.smt.project.exception.SmtException;
import com.smt.project.mapper.ProfileMapper;
import com.smt.project.model.*;
import com.smt.project.repository.ProfileRepository;
import com.smt.project.service.ProductService;
import com.smt.project.service.ProfileService;
import com.smt.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public void sendOrder(String email) {
        Profile profile = getProfileOfUser(email);
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
        log.info(String.format("order with id :: %s was sent", order.getId()));
    }

    @Override
    public void addProductToCart(UUID productId, String email) {
        Product product = productService.getProductById(productId);
        Profile profile = getProfileOfUser(email);
        Cart cart = profile.getShoppingCart();
        List<Product> products = cart.getProducts();
        products.add(product);
        cart.setProducts(products);
        cart.setPriceSum(cart.getPriceSum() + product.getPrice());
        profile.setShoppingCart(cart);
        profileRepository.save(profile);
    }

    @Override
    public void removeProductFromCart(UUID productId, String email) {
        Product product = productService.getProductById(productId);
        Profile profile = getProfileOfUser(email);
        Cart cart = profile.getShoppingCart();
        List<Product> products = cart.getProducts();
        products.remove(product);
        cart.setProducts(products);
        cart.setPriceSum(cart.getPriceSum() - product.getPrice());
        profile.setShoppingCart(cart);
        profileRepository.save(profile);
    }

    @Override
    public void removeAllProductsFromCart(String email) {
        Profile profile = getProfileOfUser(email);
        Cart cart = profile.getShoppingCart();
        cart.setProducts(Collections.emptyList());
        cart.setPriceSum(0.0);
        profile.setShoppingCart(cart);
        profileRepository.save(profile);

    }

    @Override
    public void createProfile(ProfileDto profileDto) {
        if (userService.getUserWithEmail(profileDto.email()) != null) {
            throw new SmtException(400, String.format("Profile with email {%s} already exists", profileDto.email()));
        }
        Profile profile = ProfileMapper.profileDtoToProfile(profileDto);
        User user = new User();
        user.setUsername(profileDto.email());
        Cart shoppingCart = new Cart();
        shoppingCart.setPriceSum(0.0);
        user.setProfile(profile);
        shoppingCart.setProfile(profile);
        profile.setUser(user);
        profile.setShoppingCart(shoppingCart);
        profileRepository.save(profile);
        log.info(String.format("profile was created for user :: %s ", profileDto.email()));
    }

    @Override
    public Profile getProfileOfUser(String email) {
        return userService.getUserWithEmail(email).getProfile();
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public void updateProfile(ProfileDto profileDto) {
        Profile updatedProfile = getProfileOfUser(profileDto.email());
        updatedProfile.setFirstName(profileDto.firstName());
        updatedProfile.setLastName(profileDto.lastName());
        updatedProfile.setAge(profileDto.age());

        profileRepository.save(updatedProfile);

        log.info(String.format("profile was updated for user :: %s ", profileDto.email()));
    }

    @Override
    public void deleteProfile(String email) {
        profileRepository.delete(getProfileOfUser(email));
    }
}
