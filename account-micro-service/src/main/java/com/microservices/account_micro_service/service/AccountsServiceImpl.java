package com.microservices.account_micro_service.service;

import com.microservices.account_micro_service.constants.AccountsConstants;
import com.microservices.account_micro_service.dto.AccountsDto;
import com.microservices.account_micro_service.dto.CustomerDto;
import com.microservices.account_micro_service.entity.Accounts;
import com.microservices.account_micro_service.entity.Customer;
import com.microservices.account_micro_service.exceptions.CustomerAlreadyExistsException;
import com.microservices.account_micro_service.exceptions.ResourceNotFoundException;
import com.microservices.account_micro_service.mapper.AccountsMapper;
import com.microservices.account_micro_service.mapper.CustomerMapper;
import com.microservices.account_micro_service.repository.AccountsRepository;
import com.microservices.account_micro_service.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService{

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());
        String mobileNumber = customer.getMobileNumber();
        Optional<Customer> customerCheck = customerRepository.findByMobileNumber(mobileNumber);
        if(customerCheck.isPresent()){
            throw new CustomerAlreadyExistsException("Customer with " + mobileNumber + " already exists in database.");
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer  fetchedCustomer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                             new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );


        Accounts account = accountsRepository.findByCustomerId(fetchedCustomer.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account","customerId",fetchedCustomer.getCustomerId().toString()));


        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(fetchedCustomer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account,new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account","accountNumber",accountsDto.getAccountNumber().toString()));

            AccountsMapper.mapToAccounts(accountsDto,new Accounts());
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                                    .orElseThrow(() ->
                                            new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    public Accounts createNewAccount(Customer savedCustomer){
        Accounts accounts = new Accounts();
        accounts.setCustomerId(savedCustomer.getCustomerId());
        long accountNo = 10000000000L + new Random().nextInt(90000000);
        accounts.setAccountNumber(accountNo);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        return accounts;
    }

}
