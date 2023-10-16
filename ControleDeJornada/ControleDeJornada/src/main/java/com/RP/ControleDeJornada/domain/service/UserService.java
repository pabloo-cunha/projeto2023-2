package com.RP.ControleDeJornada.domain.service;

import com.RP.ControleDeJornada.domain.dto.RegistrationUserRecord;
import com.RP.ControleDeJornada.domain.dto.UpdateUserRecord;
import com.RP.ControleDeJornada.domain.entitys.ResultCenter.ResultCenter;
import com.RP.ControleDeJornada.domain.entitys.user.User;
import com.RP.ControleDeJornada.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RcService rcService;

    public void register(RegistrationUserRecord data){
        User user = new User(data);
        ResultCenter rc = rcService.findById(data.codeRc());
        user.setResultCenter(rc);

        userRepository.save(user);
    }

    public void updateUser(Integer id, UpdateUserRecord data){
        User user = userRepository.getReferenceById(id);
        if(data.jobRole() != null){
            user.setJobrole(data.jobRole());
        }
        if (data.codeRc() != null){
            ResultCenter resultCenter = rcService.findById(data.codeRc());
            user.setResultCenter(resultCenter);
        }
        if (data.status() != null){
            user.setStatus(data.status());
        }
        userRepository.save(user);
    }

    public List<ResultCenter> findAllResultCenter() {
        List<ResultCenter> rc = rcService.findAll();
        return rc;
    }

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public List<User> findUserByRc(String codeRc) {
        ResultCenter rc = rcService.findById(codeRc);
        return userRepository.findByResultCenter(rc);

    }
}