package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Appointment;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.AppointmentRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PaymentController {

    @Value("${STRIPE_API_KEY}")
    private String secretKey;
    private final AppointmentRepository appointmentDao;
    private final UserRepository userDao;

    public PaymentController(AppointmentRepository appointmentDao, UserRepository userDao) {
        this.appointmentDao = appointmentDao;
        this.userDao = userDao;
    }
    @GetMapping("/paymentDetail/{id}")
    public String createPaymentForm(Model model, @PathVariable Long id, Authentication authentication) {
        Appointment appointment = appointmentDao.findById(id).orElse(null);
        User currentUser = userDao.findByUsername(authentication.getName());

        if (appointment == null) {
            // Handle appointment not found scenario
            return "redirect:/profile";
        }
        model.addAttribute("appointment", appointment);
        model.addAttribute("currentUser", currentUser);
        return "payments/paymentDetail";
    }

    @PostMapping("/paymentDetail/{id}")
    public RedirectView createPayment(@PathVariable long id, @ModelAttribute Appointment appointment, Model model) throws StripeException {
        Appointment appointmentId = appointmentDao.findById(id).get();

        Stripe.apiKey = secretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/payment/success")
                .setCancelUrl("http://localhost:8080/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(appointmentId.getTitle())
                                        .build())
                                .setUnitAmount((long) (appointmentId.getPrice() * 100))
                                .build())
                        .setQuantity(1L)
                        .build())
                .build();
        model.addAttribute("appointment", appointmentId);
        Session session = Session.create(params);
        return new RedirectView(session.getUrl());
    }

    @GetMapping("/payment/success")
    public String successPaymentForm() {
        return "payments/payment_success";
    }
    //user these dummy card to process the payment
//    Visa	4242424242424242	Any 3 digits	Any future date
//    Visa (debit)	4000056655665556	Any 3 digits	Any future date
//    Mastercard	5555555555554444	Any 3 digits	Any future date
//    Mastercard (2-series)	2223003122003222	Any 3 digits	Any future date
    @GetMapping("/payment/cancel")
    public String cancelPaymentForm() {
        return "redirect:/profile";
    }
}
