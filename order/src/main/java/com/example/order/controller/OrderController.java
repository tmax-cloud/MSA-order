package com.example.order.controller;
import com.example.order.model.OrderInfo;
import com.example.order.repo.OrderRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


import org.json.simple.JSONObject;



@RestController
@RequestMapping("/order")
public class OrderController {
    @Value("${upstream.payment.serviceAddr}")
    private String paymentAddr;
    @Value("${upstream.payment.servicePort}")
    private String paymentPort;

    final RestTemplate restTemplate = new RestTemplate();
    private final OrderRepo orderRepo;
    public int ticket_number = 0;

    public OrderController(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/health/ready")
    @ResponseStatus(HttpStatus.OK)
    public void ready(){}

    @GetMapping("/health/live")
    @ResponseStatus(HttpStatus.OK)
    public void live(){}

    @GetMapping
    public List<OrderInfo> getOrder(){
        return orderRepo.findAll();
    }
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderInfo order){
        // TODO : SERVICE
        order.setPayment(ticket_number);
        ticket_number++;
        orderRepo.save(order);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE", order.getType());
        jsonObject.put("ID", order.getId());
        final ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://" + paymentAddr + ":" + paymentPort + "/payment",jsonObject, String.class);
        final String response = responseEntity.getBody();
        return ResponseEntity.ok(String.format(response.trim()));
    }
    @PostMapping("/update")
    public String updateQuantity(@RequestBody int id) {
        OrderInfo order = orderRepo.findById(id).orElse(null);
        if(order == null){
            // TODO:
            return "SOMETHING WRONG";
        }
        String type = order.getType();
        if (type.equals("lend") || type.equals("sell")){
            // TODO: publish book-info.book.quantity += 1
            return "SUCCEED DEDUCT QUANTITY";
        }else{
            // TODO: publish book-info.book.quantity -= 1
            return "SUCCEED INCREASE QUANTITY";
        }
    }

}