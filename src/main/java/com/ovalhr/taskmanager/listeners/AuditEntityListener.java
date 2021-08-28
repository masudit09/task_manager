package com.ovalhr.taskmanager.listeners;

import com.ovalhr.taskmanager.entity.User;
import com.ovalhr.taskmanager.util.Util;
import com.ovalhr.taskmanager.entity.Audit;
import com.ovalhr.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
/**
 * Created by rana on 8/27/21.
 */
@Component
public class AuditEntityListener {

    @Autowired
    UserRepository userRepository;
    @PrePersist
    public void prePersist(Audit audit) {
//        User user = userRepository.findByUsername(Util.getCurrentUsername());
        Date currentDate = Util.getCurrentDate();
        String username = Util.getCurrentUsername();
        String fullName = Util.getCurrentUsername();
        if(!StringUtils.isEmpty(username) ){
            if(StringUtils.isEmpty(audit.getCreatedBy())){
                audit.setCreatedBy(username);
            }
            if(StringUtils.isEmpty(audit.getUpdatedBy())){
                audit.setUpdatedBy(username);
            }
            if(StringUtils.isEmpty(audit.getCreatorFullName())){
                audit.setCreatorFullName(fullName);
            }
        }else{
            if(StringUtils.isEmpty(audit.getCreatedBy())){
                audit.setCreatedBy("Syetem");
            }
            if(StringUtils.isEmpty(audit.getUpdatedBy())){
                audit.setUpdatedBy("Syetem");
            }
            if(StringUtils.isEmpty(audit.getUpdatedBy())){
                audit.setUpdatedBy("Syetem");
            }

        }
        if(audit.getCreatedOn() == null){
            audit.setCreatedOn(currentDate);
        }
        if(audit.getUpdatedOn() == null){
            audit.setUpdatedOn(currentDate);
        }

//        audit.setCreatedUser(user);

    }

    @PreUpdate
    public void preUpdate(Audit audit) {
        Date currentDate = Util.getCurrentDate();
        String username = Util.getCurrentUsername();
        audit.setUpdatedBy(username == null? "System":username);
        audit.setUpdatedOn(currentDate);
    }


}
