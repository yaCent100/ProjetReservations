package be.iccbxl.pid.reservationsSpringBoot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import be.iccbxl.pid.reservationsSpringBoot.config.StripeConfig;
import be.iccbxl.pid.reservationsSpringBoot.dto.RepresentationDTO;
import be.iccbxl.pid.reservationsSpringBoot.dto.UserDTO;
import be.iccbxl.pid.reservationsSpringBoot.service.ReservationService;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaiemenRestController {

    private final StripeConfig stripeConfig;
    private ReservationService reservationService;

    
    public PaiemenRestController(StripeConfig stripeConfig, ReservationService reservationService) {
        this.stripeConfig = stripeConfig;
        this.reservationService = reservationService;
        
    }

    @PostMapping("/create-checkout-session")
    public String createCheckoutSession(@SessionAttribute("nbPlaces") int nbPlaces,
            @SessionAttribute("representationId") Long representationId,
            @SessionAttribute("total") double total,@SessionAttribute("userId") Long userId) throws JsonProcessingException {
    	
        try {
            Stripe.apiKey = stripeConfig.getApiKey();

            
            RepresentationDTO representationDTO = new RepresentationDTO();
            representationDTO.setId(representationId);

            UserDTO userDTO = new UserDTO();
            userDTO.setId(userId);

            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                    		.setCurrency("eur")
                    		.setUnitAmount((long) total * 100L)
                            .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    		.setName("4564")
                                    		.build()
                            )
                            .build();
            
            SessionCreateParams params =
                    SessionCreateParams.builder()
                    		.setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                            .setMode(SessionCreateParams.Mode.PAYMENT)                        
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                            .setReturnUrl("http://localhost:8080/stripe/return?session_id={CHECKOUT_SESSION_ID}")
                            .addLineItem( 
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(priceData)
                                            .build()
                            )
                            .build();

            Session session = Session.create(params);

               reservationService.createReservation(nbPlaces, representationDTO, userDTO);
            	
                Map<String, String> responseData = new HashMap<>();
                responseData.put("clientSecret", session.getClientSecret());

                ObjectMapper mapper = new ObjectMapper();   
                String jsonResponse = mapper.writeValueAsString(responseData);
               
                return jsonResponse;


        } catch (StripeException e) {
            e.printStackTrace();
            return "{\"error\": \"Erreur lors de la création de la session de paiement.\"}";
        }
        
        
        
    }
    
    
    @GetMapping("/session-status")
    public String handleStripeReturn(@RequestParam("session_id") String sessionId) {
        String jsonResponse = null;
        
        try {
            Session session = Session.retrieve(sessionId);

            Map<String, String> map = new HashMap<>();
            map.put("status", session.getRawJsonObject().getAsJsonPrimitive("status").getAsString());
            map.put("customer_email", session.getRawJsonObject().getAsJsonObject("customer_details").getAsJsonPrimitive("email").getAsString());

            ObjectMapper mapper = new ObjectMapper();
            jsonResponse = mapper.writeValueAsString(map);
        } catch (JsonProcessingException | StripeException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }
    
   
    
    }
    



