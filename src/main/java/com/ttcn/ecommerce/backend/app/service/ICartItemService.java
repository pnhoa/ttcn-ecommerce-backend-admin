package com.ttcn.ecommerce.backend.app.service;

import com.ttcn.ecommerce.backend.app.dto.CartItemDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.CartItem;
import com.ttcn.ecommerce.backend.app.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICartItemService {
    List<CartItem> findAll();

    Page<CartItem> findAllPageAndSort(Pageable pagingSort);

    CartItem findById(Long theId);

    List<CartItem> findByCartId(Long cartId);

    MessageResponse createCartItem(CartItemDTO CartItemDTO);

    MessageResponse updateCartItem(Long theId, CartItemDTO CartItemDTO);

    void deleteCartItem(Long theId);


}
