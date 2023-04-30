package ftn.uns.ac.rs.NVTKTS20222023.controller;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MessageDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.CitizenBlockDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Message;
import ftn.uns.ac.rs.NVTKTS20222023.service.BlockService;
import ftn.uns.ac.rs.NVTKTS20222023.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService cs;


    @MessageMapping("/chat")
    @SendTo("/topic")
    public String saveMessageCitizenzz(String message) throws Exception {
        System.out.println("STIGLA MESSAGE");
        System.out.println(message);
        return "USPEO";
    }
//    @MessageMapping("/chat/admin")
//    @SendTo("/topic/ADMIN")
//    public MessageDTO returnToAdmin(MessageDTO message) throws Exception {
//        System.out.println("STIGLA MESSAGE222");
//        System.out.println(message);
//        return message;
//    }

    @MessageMapping("/chat/citizen/{chatName}")
    @SendTo("/topic/{chatName}")
    public Message saveMessageCitizen(MessageDTO message ,  @DestinationVariable String chatName) throws Exception {
        System.out.println("STIGLA MESSAGE");
        System.out.println(message);
        return cs.saveMessageCitizen(message);
    }

    @MessageMapping("/chat/driver/{chatName}")
    @SendTo("/topic/{chatName}")
    public Message saveMessageDriver(MessageDTO message ,  @DestinationVariable String chatName) throws Exception {
        System.out.println("PORUKICA");
        return cs.saveMessageDriver(message);
    }



}
