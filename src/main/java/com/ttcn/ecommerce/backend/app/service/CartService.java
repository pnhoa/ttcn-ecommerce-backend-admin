package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.CartDTO;
import com.ttcn.ecommerce.backend.app.dto.CartItemDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Cart;
import com.ttcn.ecommerce.backend.app.entity.CartItem;
import com.ttcn.ecommerce.backend.app.entity.Customer;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.repository.CartItemRepository;
import com.ttcn.ecommerce.backend.app.repository.CartRepository;
import com.ttcn.ecommerce.backend.app.repository.CustomerRepository;
import com.ttcn.ecommerce.backend.app.repository.ProductRepository;
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
    private CartItemRepository cartItemRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ProductRepository productRepo;

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
        cart.setCreatedBy(cartDTO.getCreatedBy());
        cart.setCreatedDate(new Date());

        Cart savedCart = cartRepo.save(cart);

        for( CartItemDTO cartItemDTO : cartDTO.getCartItems()){
            CartItem cartItem = new CartItem();
            cartItem.setCart(savedCart);
            cartItem.setProduct(productRepo.findById(cartItemDTO.getProductId()).get());
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setStatus(cartItemDTO.getStatus());
            cartItem.setCreatedBy(cartItemDTO.getCreatedBy());
            cartItem.setCreatedDate(new Date());

            cartItemRepo.save(cartItem);
        }

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

            cart.get().setModifiedBy(cartDTO.getModifiedBy());
            cart.get().setModifiedDate(cartDTO.getModifiedDate());
            Cart savedCart = cartRepo.save(cart.get());

            System.out.println("\nSize of incoming cartItemList : " + cartDTO.getCartItems().size());

            for(CartItem previousCartItem : cart.get().getCartItems()){
                cartItemRepo.delete(previousCartItem);
            }
            cart.get().getCartItems().clear();

            for(int i = 0; i < cartDTO.getCartItems().size(); i++){
                CartItemDTO changedCartItemDTO = cartDTO.getCartItems().get(i);

                CartItem cartItemToChange = new CartItem();
                cartItemToChange.setCart(savedCart);
                cartItemToChange.setProduct(productRepo.findById(changedCartItemDTO.getProductId()).get());
                cartItemToChange.setQuantity(changedCartItemDTO.getQuantity());
                cartItemToChange.setStatus(changedCartItemDTO.getStatus());

                cartItemToChange.setModifiedBy(changedCartItemDTO.getModifiedBy());
                cartItemToChange.setModifiedDate(changedCartItemDTO.getModifiedDate());
                cartItemRepo.save(cartItemToChange);
            }

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
