package com.trans.utils;

import com.trans.dto.UserDTO;
import com.trans.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public ModelMapperConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToUserDTO(User user){
        return  modelMapper.map(user,UserDTO.class);
    }
}
