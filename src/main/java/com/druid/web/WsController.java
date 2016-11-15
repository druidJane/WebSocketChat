package com.druid.web;

import com.druid.domain.DruidMessage;
import com.druid.domain.DruidResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Created by 1115 on 2016/11/15.
 */
@Controller
public class WsController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void handleChat(Principal principal,String msg){
        if(principal.getName().equals("zz")){
            messagingTemplate.convertAndSendToUser("druid","/queue/notifications",principal.getName()+"-send:"+msg);
        }else{
            messagingTemplate.convertAndSendToUser("zz","/queue/notifications",principal.getName()+"-send:"+msg);
        }
    }
    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public DruidResponse say(DruidMessage msg) throws InterruptedException {
        Thread.sleep(3000);
        return new DruidResponse("Welcome, "+msg.getName()+"!");
    }
}
