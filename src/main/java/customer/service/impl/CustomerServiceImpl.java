package customer.service.impl;

import customer.data.*;
import customer.service.AdditionalDetailsService;
import customer.service.CustomerService;
import customer.service.impl.db.AdditionalDetailsDbServiceImpl;
import customer.service.impl.db.CustomerDbServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerServiceImpl implements CustomerService {

    static {
        System.out.println("CustomerServiceImpl initialized");
    }
    private final CustomerDbServiceImpl customerDbService;
    private final AdditionalDetailsService additionalDetailsService;

    @Autowired

    public CustomerServiceImpl(CustomerDbServiceImpl customerDbService,AdditionalDetailsService additionalDetailsService) {
        this.customerDbService = customerDbService;
       this.additionalDetailsService=additionalDetailsService;
    }// we have to initialize them within same constructor ?? why : think and answer(GN) so that in customerServiceImpl both are injected

    @Override
    public String addCustomer(AddCustomerRequest addCustomerRequest) {
        UUID userId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(userId);
        customer.setEmail(addCustomerRequest.getEmail());
        customer.setName(addCustomerRequest.getName());
        customer.setIsActive(true);
        customerDbService.addCustomer(customer);
        return userId.toString();

//        if(addCustomerRequest.getAdditionalDetails()!=null){
//            //check this ///
//        }
    }

 @Override
    public Customer getCustomer(String userId) {
     return customerDbService.GetCustomerByUserId(UUID.fromString(userId));
    }


    @Override
    public void updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerDbService.GetCustomerByUserId(updateCustomerRequest.getId());
        if (customer != null) {
            if (updateCustomerRequest.getUpdatedName() != null) {
                customer.setName(updateCustomerRequest.getUpdatedName());

            }
            if (updateCustomerRequest.getUpdatedEmail() != null) {
                customer.setEmail(updateCustomerRequest.getUpdatedEmail());

            }
            if (updateCustomerRequest.getUpdatedIsActive() != null) {
                customer.setIsActive(updateCustomerRequest.getUpdatedIsActive());
            }
            customerDbService.updatedCustomerInDb(customer);
        } else {
            throw new RuntimeException("Customer not there" + updateCustomerRequest.getId());
        }

    }


    @Override
    public void deleteCustomer(DeleteCustomerRequest deleteCustomerRequest){
        UUID customerId= deleteCustomerRequest.getId();

        customerDbService.deleteCustomerByUserId(customerId);
    }




}
