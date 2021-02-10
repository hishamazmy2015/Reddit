package com.Focus.Reddit.service;

import com.Focus.Reddit.dto.CustomerDto;
import com.Focus.Reddit.dto.CustomerResponse;
import com.Focus.Reddit.exceptions.PostNotFoundException;
import com.Focus.Reddit.exceptions.SubredditNotFoundException;
import com.Focus.Reddit.mapper.CustomerMapper;
import com.Focus.Reddit.model.*;
import com.Focus.Reddit.repository.CountryRepository;
import com.Focus.Reddit.repository.CustomerRepository;
import com.Focus.Reddit.repository.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@org.springframework.transaction.annotation.Transactional
public class CustomerService {

    private static final String POST_URL = "";
    private final CustomerMapper customerMapper;
    private final CountryRepository countryRepository;

    private final CustomerRepository customerRepo;

    private final AuthService authService;
    private final MailService mailService;
    private final    ImageRepository imageRepository;
    private final MailContentBuilder mailContainBuilder;

    @Transactional
    public void saveCountry(String countryName) {
        Country country = new Country();
        country.setCountryName(countryName);
        countryRepository.save(country);
    }

    @Transactional
    public void saveImage(ImageModel imageModel) {
        imageRepository.save(imageModel);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public ImageModel getImage(String imageName) {
        ImageModel retrievedImage = imageRepository.findByName(imageName)
                .orElseThrow(() -> new PostNotFoundException("Not eXist"));
        return retrievedImage;
    }




    @Transactional
    public void save(CustomerDto customerDto) {

        User currentUser = authService.getCurrentUser();

        Country country = countryRepository.findByCountryName(customerDto.getCountryName()).orElseThrow(() -> new SubredditNotFoundException(
                customerDto.getCountryName()));

        Customer customer = customerMapper.mapCustDtoToCustomer(customerDto, country);

        customerRepo.save(customer);

//        String message = mailContainBuilder.build(currentUser + "  post " + POST_URL);
//        sendMailNotification(message,customer.getFirstName());

    }

    private void sendMailNotification(String message, User user) {
        mailService.sendMail(user.getEmail()
                , new NotificationEmail(user.getUsername(), user.getEmail(), message));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CustomerResponse> getCustomersByUserName(String username) {
        Customer customer = customerRepo.findByFirstName(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return customerRepo.findByFirstName(username).stream()
                .map(customerMapper::mapCustomerToDto)
                .collect(Collectors.toList());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepo.findAll().stream()
                .map(customerMapper::mapCustomerToDto)
                .collect(Collectors.toList());
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CustomerResponse getAllCustomerByCountry(String name) {
        Country byName = countryRepository.findByCountryName(name).orElseThrow(() -> new PostNotFoundException(name));
        return customerMapper.mapCustomerToDto(customerRepo.findByCountry(byName));
//        .stream().
//                        map(customerMapper::mapCustomerToDto).
//                        collect(toList());
    }


}