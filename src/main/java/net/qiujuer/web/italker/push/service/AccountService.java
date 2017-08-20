package net.qiujuer.web.italker.push.service;

import net.qiujuer.web.italker.push.bean.api.account.RegisterModel;
import net.qiujuer.web.italker.push.bean.card.UserCard;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author qiujuer
 */
// 127.0.0.1/api/account/...
@Path("/account")
public class AccountService {

    //GET 127.0.0.1/api/account/login
    @GET
    @Path("/login")
    public String get() {
        return "You get the login.";
    }


    //POST 127.0.0.1/api/account/register
    @POST
    @Path("/register")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserCard register(RegisterModel model) {

        User user = UserFactory.findByPhone(model.getAccount().trim());
        if(user != null){
            UserCard card = new UserCard();
            card.setName("已有了Phone");
            return card;
        }

        user = UserFactory.findByName(model.getName().trim());
        if(user != null){
            UserCard card = new UserCard();
            card.setName("已有了Name");
            return card;
        }

        user = UserFactory.register(model.getAccount(),
                model.getPassword(),
                model.getName());
        if(user != null){
            UserCard card = new UserCard();
            card.setName(user.getName());
            card.setPhone(user.getPhone());
            card.setSex(user.getSex());
            card.setFollow(true);
            card.setModifyAt(user.getUpdateAt());
            return card;
        }
        return null;
    }

}
