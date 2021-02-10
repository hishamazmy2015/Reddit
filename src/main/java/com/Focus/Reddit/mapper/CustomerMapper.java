package com.Focus.Reddit.mapper;


import com.Focus.Reddit.dto.CustomerDto;
import com.Focus.Reddit.dto.CustomerResponse;
import com.Focus.Reddit.model.Country;
import com.Focus.Reddit.model.Customer;
import com.Focus.Reddit.repository.CustomerRepository;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public interface CustomerMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "country", source = "country")
    Customer mapCustDtoToCustomer(CustomerDto commentsDto, Country country);


    @Mapping(target = "countryName", source = "country.countryName")
//    @Mapping(target = "customerCount", expression = "java(customerCount())")
    @Mapping(target = "duration", expression = "java(getDuration(customer))")
    public abstract CustomerResponse mapCustomerToDto(Customer customer);



    default String getDuration(Customer customer) {
        return TimeAgo.using(customer.getCreatedDate().toEpochMilli());
    }

}
