//package com.codeup.autoconnect.controllers;
//
//import com.codeup.autoconnect.models.Service;
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.view.RedirectView;
//
//@Controller
//public class PaymentController {
//
//    @Value("${STRIPE_API_KEY}")
//    private String secretKey;
//
//    @GetMapping("/create_payment")
//    public String createPaymentForm(Model model){
//        model.addAttribute("service", new Service());
//        return "payments/createPayment";
//    }
//
//    @PostMapping("/create_payment")
//    public RedirectView createPayment(@ModelAttribute Service service, Model model) throws StripeException {
//        Stripe.apiKey = secretKey;
//
//        SessionCreateParams params = SessionCreateParams.builder()
//                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setSuccessUrl("http://localhost:8080/payment/success")
//                .setCancelUrl("http://localhost:8080/payment/cancel")
//                .addLineItem(SessionCreateParams.LineItem.builder()
//                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
//                                .setCurrency("usd")
//                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                        .setName(service.getTitle())
//                                        .build())
//                                .setUnitAmount((long) (service.getPrice() * 100))
//                                .build())
//                        .setQuantity(1L)
//                        .build())
//                .build();
//        model.addAttribute("service", service);
//        Session session = Session.create(params);
//        RedirectView redirectView = new RedirectView(session.getUrl());
//        redirectView.setContextRelative(false);
//        return new RedirectView(session.getUrl());
//    }
//    @GetMapping("/payment/success")
//    public String successPaymentForm() {
//        return "/payments/success";
//    }
//    @GetMapping("/payment/cancel")
//    public String cancelPaymentForm() {
//        return "redirect:/profile";
//    }
//}
