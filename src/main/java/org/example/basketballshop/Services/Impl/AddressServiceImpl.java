package org.example.basketballshop.Services.Impl;

import jakarta.transaction.Transactional;
import org.example.basketballshop.DTO.AddressDto;
import org.example.basketballshop.DTO.Forms.AddressForm;
import org.example.basketballshop.Models.Address;
import org.example.basketballshop.Models.User;
import org.example.basketballshop.Repositories.AddressRepository;
import org.example.basketballshop.Repositories.UserRepository;
import org.example.basketballshop.Services.AddressService;
import org.example.basketballshop.Services.BadgesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BadgesService badgesService;

    @Override
    public AddressDto addAddress(AddressForm addressForm) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            logger.info("Adding address for user: {}", email);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        logger.error("User not found with email: {}", email);
                        return new UsernameNotFoundException("user not found");
                    });

            Address address = Address.builder()
                    .city(addressForm.getCity())
                    .country(addressForm.getCountry())
                    .street(addressForm.getStreet())
                    .postalCode(addressForm.getPostalCode())
                    .user(user)
                    .build();

            user.setAddress(address);
            logger.info("Saving new address for user: {}", email);
            addressRepository.save(address);

            logger.info("Awarding 'Знаток доставки' badge to user: {}", email);
            badgesService.awardBadgeToUser(user, "Знаток доставки");

            logger.info("Successfully added address for user: {}", email);
            return AddressDto.in(address);
        } catch (DataAccessException e) {
            logger.error("Database error while adding address", e);
            throw new RuntimeException("Error adding address: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error adding address", e);
            throw e;
        }
    }

    @Override
    public boolean updateAddress(Long addressId, AddressForm addressForm) {
        try {
            logger.info("Updating address with ID: {}", addressId);
            
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> {
                        logger.error("Address not found with ID: {}", addressId);
                        return new RuntimeException("Address not found");
                    });

            if (addressForm.getCity() != null) {
                logger.debug("Updating city to: {}", addressForm.getCity());
                address.setCity(addressForm.getCity());
            }
            if (addressForm.getCountry() != null) {
                logger.debug("Updating country to: {}", addressForm.getCountry());
                address.setCountry(addressForm.getCountry());
            }
            if (addressForm.getStreet() != null) {
                logger.debug("Updating street to: {}", addressForm.getStreet());
                address.setStreet(addressForm.getStreet());
            }
            if (addressForm.getPostalCode() != null) {
                logger.debug("Updating postal code to: {}", addressForm.getPostalCode());
                address.setPostalCode(addressForm.getPostalCode());
            }

            addressRepository.save(address);
            logger.info("Successfully updated address with ID: {}", addressId);
            return true;
        } catch (DataAccessException e) {
            logger.error("Database error while updating address with ID: {}", addressId, e);
            throw new RuntimeException("Error updating address: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating address with ID: {}", addressId, e);
            throw e;
        }
    }

    @Override
    public Optional<AddressDto> getAddress(Long addressId) {
        try {
            logger.info("Retrieving address with ID: {}", addressId);
            Optional<Address> address = addressRepository.findById(addressId);
            
            if (address.isPresent()) {
                logger.info("Successfully retrieved address with ID: {}", addressId);
            } else {
                logger.info("Address not found with ID: {}", addressId);
            }
            
            return Optional.of(address.map(AddressDto::in)).orElse(Optional.empty());
        } catch (Exception e) {
            logger.error("Error retrieving address with ID: {}", addressId, e);
            throw e;
        }
    }

    @Override
    public boolean deleteAddress(Long addressId) {
        try {
            logger.info("Deleting address with ID: {}", addressId);
            
            Optional<Address> addressOptional = addressRepository.findById(addressId);
            if (addressOptional.isPresent()) {
                Address address = addressOptional.get();
                User user = address.getUser();
                logger.info("Found address for user: {}", user.getEmail());

                user.setAddress(null);
                userRepository.save(user);
                logger.info("Removed address reference from user: {}", user.getEmail());

                addressRepository.delete(address);
                logger.info("Successfully deleted address with ID: {}", addressId);
                return true;
            }
            
            logger.info("Address not found with ID: {}", addressId);
            return false;
        } catch (DataAccessException e) {
            logger.error("Database error while deleting address with ID: {}", addressId, e);
            throw new RuntimeException("Error deleting address: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error deleting address with ID: {}", addressId, e);
            throw e;
        }
    }

    @Override
    public boolean hasAddress(String email) {
        try {
            logger.info("Checking if user has address: {}", email);
            
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        logger.error("User not found with email: {}", email);
                        return new RuntimeException("User not found");
                    });
            
            boolean hasAddress = user.getAddress() != null;
            logger.info("User {} {} an address", email, hasAddress ? "has" : "does not have");
            return hasAddress;
        } catch (Exception e) {
            logger.error("Error checking address for user: {}", email, e);
            throw e;
        }
    }
}
