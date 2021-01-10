package com.elasticsearch.demo.dto;

import com.elasticsearch.demo.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO extends DocumentDTO implements Serializable {

   private User user;

}
