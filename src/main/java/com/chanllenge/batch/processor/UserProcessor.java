package com.chanllenge.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.chanllenge.batch.entity.user.User;

public class UserProcessor implements ItemProcessor<User, User> {
	@Override
	public User process(User user) throws Exception {
	    if (user.getActive() == null || !user.getActive()) {
	        return null;
	    }

	    if (user.getName() != null) {
	        user.setName(user.getName().trim());
	    }

	    if (user.getScore() != null && user.getScore() < 0) {
	        user.setScore(0);
	    }
	    
	    if (user.getAge() != null && user.getAge() < 18) {
	        user.setStatus("UNDERAGE");
	    }
	    
	    return user;
	}
}
