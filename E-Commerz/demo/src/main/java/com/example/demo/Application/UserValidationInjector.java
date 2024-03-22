package com.example.demo.Application;

import com.example.demo.Infrastructure.Ports.AssetConsumerValidation;
import com.example.demo.Infrastructure.Ports.AssetValidationInjector;
import com.example.demo.Infrastructure.Repositories.OrderRepository;
import com.example.demo.Infrastructure.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserValidationInjector implements AssetValidationInjector {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrderRepository orderRepository;

    public AssetConsumerValidation getAssetConsumerValidation(){
        return new UserValidationApplication(
                new UserServiceImpl(userRepository,orderRepository));
    }
}
