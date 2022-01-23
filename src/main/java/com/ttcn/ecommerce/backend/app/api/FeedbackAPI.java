package com.ttcn.ecommerce.backend.app.api;

import com.ttcn.ecommerce.backend.app.dto.FeedbackDTO;
import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.entity.Feedback;
import com.ttcn.ecommerce.backend.app.service.IFeedbackService;
import com.ttcn.ecommerce.backend.app.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin
public class FeedbackAPI {

    @Autowired
    private IFeedbackService feedbackService;

    @GetMapping("")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Feedback>> findAll(@RequestParam(value = "q", required = false) Integer rating,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int limit,
                                                  @RequestParam(defaultValue = "id,ASC") String[] sort){

        try {

            Pageable pagingSort = CommonUtils.sortItem(page, limit, sort);
            Page<Feedback> feedbackPage;

            if (rating == null) {
                feedbackPage = feedbackService.findAllPageAndSort(pagingSort);
            } else {
                feedbackPage = feedbackService.findByRatingContaining(rating, pagingSort);
            }

            return new ResponseEntity<>(feedbackPage.getContent(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Feedback> findById(@PathVariable("id") Long theId){

        Feedback thFeedback = feedbackService.findById(theId);
        return new ResponseEntity<>(thFeedback, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<MessageResponse> createFeedback(@Valid @RequestBody FeedbackDTO theFeedbackDto, BindingResult theBindingResult){

        if(theBindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for create feedback", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = feedbackService.createFeedback(theFeedbackDto);
        return new ResponseEntity<MessageResponse>(messageResponse, messageResponse.getStatus());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<MessageResponse> updateFeedback(@PathVariable("id") Long theId,
                                                          @Valid @RequestBody FeedbackDTO theFeedbackDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for update Feedback", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = feedbackService.updateFeedback(theId, theFeedbackDto);
        return new ResponseEntity<MessageResponse>(messageResponse, messageResponse.getStatus());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteFeedback(@PathVariable("id") Long theId){

        feedbackService.deleteFeedback(theId);
        return new ResponseEntity<>(new MessageResponse("Delete successfully!", HttpStatus.OK, LocalDateTime.now()), HttpStatus.OK);
    }

    @GetMapping("/count")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<?> count(){
        return new ResponseEntity<>(feedbackService.count(), HttpStatus.OK);
    }
}
