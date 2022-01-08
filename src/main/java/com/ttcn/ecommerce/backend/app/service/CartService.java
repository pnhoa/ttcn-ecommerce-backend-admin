package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.CartDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Cart;
import com.ttcn.ecommerce.backend.app.entity.Customer;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.repository.CartRepository;
import com.ttcn.ecommerce.backend.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService implements ICartService{

    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public List<Cart> findAll() {
        return cartRepo.findAll();
    }

    @Override
    public Page<Cart> findAllPageAndSort(Pageable pagingSort) {
        return cartRepo.findAll(pagingSort);
    }

    @Override
    public Cart findById(Long theId) {

        return cartRepo
                .findById(theId).orElseThrow(() -> new ResourceNotFoundException("can't find cart with ID=" + theId));
    }


    @Override
    public List<Cart> findByCustomerId(long customerId) {
        return cartRepo.findByCustomer(customerId);
    }

    @Override
    public MessageResponse createCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setTotalCost(cartDTO.getTotalCost());
        cart.setNote(cartDTO.getNote());
        cart.setCustomer(customerRepo.getById(cartDTO.getCustomerId()));
        cart.setAddress(cartDTO.getAddress());
        cart.setCartItems(cartDTO.getCartItems());
        cart.setCreatedBy("");
        cart.setCreatedDate(new Date());
        cart.setModifiedBy(cartDTO.getModifiedBy());
        cart.setModifiedDate(cartDTO.getCreatedDate());

        cartRepo.save(cart);
        return new MessageResponse("Create cart successfully!", HttpStatus.CREATED, LocalDateTime.now());
    }

    @Override
    public MessageResponse updateCart(Long theId, CartDTO cartDTO) {
        Optional<Cart> cart = cartRepo.findById(theId);

        if(!cart.isPresent()) {
            throw new ResourceNotFoundException("Can't find Cart with ID=" + theId);
        } else {
            cart.get().setTotalCost(cartDTO.getTotalCost());
            cart.get().setNote(cartDTO.getNote());
            cart.get().setCustomer(customerRepo.getById(cartDTO.getCustomerId()));
            cart.get().setAddress(cartDTO.getAddress());
            cart.get().setCartItems(cartDTO.getCartItems());
            cart.get().setCreatedBy(cartDTO.getCreatedBy());
            cart.get().setCreatedDate(cartDTO.getCreatedDate());
            cart.get().setModifiedBy("");
            cart.get().setModifiedDate(new Date());
            cartRepo.save(cart.get());
        }

        return new MessageResponse("Update cart successfully!" , HttpStatus.OK, LocalDateTime.now());
    }

    @Override
    public void deleteCart(Long theId) {
        Cart cart = cartRepo.findById(theId).orElseThrow(
                () -> new ResourceNotFoundException("can't find cart with ID=" + theId));

        cartRepo.delete(cart);
    }

    @Override
    public Page<Cart> findByIdContaining(Long id, Pageable pagingSort) {
        return cartRepo.findById(id, pagingSort);
    }

    @Override
    public Page<Cart> findByCustomerIdPageAndSort(Long customerId, Pageable pagingSort) {

        Optional<Customer> customer = customerRepo.findById(customerId);

        if(!customer.isPresent()){
            throw  new ResourceNotFoundException("Not found customer with ID= " + customerId);
        }
        else
        {
            Page<Cart> cartPage = cartRepo.findByCustomerId(customerId,pagingSort);
            return cartPage;
        }


    }


}
