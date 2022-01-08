package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.CartItemDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.CartItem;
import com.ttcn.ecommerce.backend.app.exception.ResourceNotFoundException;
import com.ttcn.ecommerce.backend.app.repository.CartItemRepository;
import com.ttcn.ecommerce.backend.app.repository.CartRepository;
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
public class CartItemService implements ICartItemService{
    @Autowired
    private CartItemRepository cartItemRepo;
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CartRepository cartRepo;

    @Override
    public List<CartItem> findAll() {
        return cartItemRepo.findAll();
    }

    @Override
    public Page<CartItem> findAllPageAndSort(Pageable pagingSort) {
        return  cartItemRepo.findAll(pagingSort);
    }

    @Override
    public CartItem findById(Long theId) {
        return  cartItemRepo.findById(theId).orElseThrow(() -> new ResourceNotFoundException("can't find cart with ID=" + theId));
    }

    @Override
    public List<CartItem> findByCartId(Long cartId) {
        return cartItemRepo.findCartItemByCartID(cartId);
    }

    @Override
    public MessageResponse createCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cartRepo.findById(cartItemDTO.getCartId()).get());
        cartItem.setProduct(productRepo.findById(cartItemDTO.getProductId()).get());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setStatus(cartItemDTO.getStatus());
        cartItem.setCreatedBy("");
        cartItem.setCreatedDate(new Date());
        cartItem.setModifiedBy(cartItemDTO.getModifiedBy());
        cartItem.setModifiedDate(cartItemDTO.getCreatedDate());

        cartItemRepo.save(cartItem);
        return new MessageResponse("Create CartItem successfully!", HttpStatus.CREATED, LocalDateTime.now());
    }

    @Override
    public MessageResponse updateCartItem(Long theId, CartItemDTO cartItemDTO) {
        Optional<CartItem> cartItem = cartItemRepo.findById(theId);

        if(!cartItem.isPresent()) {
            throw new ResourceNotFoundException("Can't find cartItem with ID=" + theId);
        } else {
            cartItem.get().setCart(cartRepo.findById(cartItemDTO.getCartId()).get());
            cartItem.get().setProduct(productRepo.findById(cartItemDTO.getProductId()).get());
            cartItem.get().setQuantity(cartItemDTO.getQuantity());
            cartItem.get().setStatus(cartItemDTO.getStatus());
            cartItem.get().setCreatedBy(cartItemDTO.getCreatedBy());
            cartItem.get().setCreatedDate(cartItemDTO.getCreatedDate());
            cartItem.get().setModifiedBy("");
            cartItem.get().setModifiedDate(new Date());
            cartItemRepo.save(cartItem.get());
        }

        return new MessageResponse("Update CartItem successfully!" , HttpStatus.OK, LocalDateTime.now());
    }

    @Override
    public void deleteCartItem(Long theId) throws ResourceNotFoundException {

        CartItem cartItem = cartItemRepo.findById(theId).orElseThrow(
                () -> new ResourceNotFoundException("Can't find cartitem with ID=" + theId));

        cartItemRepo.delete(cartItem);
    }

    @Override
    public Page<CartItem> findByIdContaining(Long id, Pageable pagingSort) {
        return cartItemRepo.findById(id, pagingSort);
    }

    @Override
    public Page<CartItem> findByCartIdPageAndSort(Long cartId, Pageable pagingSort) {
        return cartItemRepo.findByCartId(cartId, pagingSort);
    }
}
