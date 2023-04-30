package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.request.MessageDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Citizen;
import ftn.uns.ac.rs.NVTKTS20222023.model.Driver;
import ftn.uns.ac.rs.NVTKTS20222023.model.Message;
import ftn.uns.ac.rs.NVTKTS20222023.repository.CitizenRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.DriverRepository;
import ftn.uns.ac.rs.NVTKTS20222023.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService {

    @Autowired
    private MessageRepository mr;

    @Autowired
    private CitizenRepository cr;

    @Autowired
    private DriverRepository dr;

    public Message saveMessageCitizen(MessageDTO mdto){

        if(cr.findByUsername(mdto.getSender()) == null && cr.findByUsername(mdto.getReceiver()) == null){
            return null;
        }

        Message message = Message.builder()
                .message(mdto.getMessage())
                .sender(mdto.getSender())
                .receiver(mdto.getReceiver())
                .date(new Date().getTime())
                .build();

        mr.save(message);

        return message;
    }

    public Message saveMessageDriver(MessageDTO mdto){

        if(dr.findByUsername(mdto.getSender()) == null && dr.findByUsername(mdto.getReceiver()) == null){
            return null;
        }

        Message message = Message.builder()
                .message(mdto.getMessage())
                .sender(mdto.getSender())
                .receiver(mdto.getReceiver())
                .date(new Date().getTime())
                .build();

        mr.save(message);

        return message;
    }


    public List<String> getCitizens(){
        Set<String> senderSet = mr.findAll().stream().map(Message::getSender).collect(Collectors.toSet());
        Set<String> receiverSet = mr.findAll().stream().map(Message::getReceiver).collect(Collectors.toSet());
        senderSet.addAll(receiverSet);
        System.out.println(senderSet);
        senderSet.remove("ADMIN");
        System.out.println(senderSet.size());
        List<String> users = new ArrayList<>();

        senderSet.stream().forEach(u->{
            Citizen c = cr.findByUsername(u);
            System.out.println(c);
            if(c != null){
                users.add(u);
            }
        });

        return users;
    }

    public List<String> getDrivers(){
        Set<String> senderSet = mr.findAll().stream().map(Message::getSender).collect(Collectors.toSet());
        Set<String> receiverSet = mr.findAll().stream().map(Message::getReceiver).collect(Collectors.toSet());
        senderSet.addAll(receiverSet);
        senderSet.remove("ADMIN");

        List<String> users = new ArrayList<>();

        senderSet.stream().forEach(u->{
            Driver d = dr.findByUsername(u);
            if(d != null){
                users.add(u);
            }
        });


        return users;
    }

    public List<MessageDTO> findAllMessagesOfCitizen(String username) {

        List<Message> senderMessages = mr.findAll().stream().filter(m->m.getSender().equals(username)).collect(Collectors.toList());
        List<Message> receiverMessages = mr.findAll().stream().filter(m->m.getReceiver().equals(username)).collect(Collectors.toList());
        senderMessages.addAll(receiverMessages);
        Collections.sort(senderMessages, Comparator.comparingLong(Message::getDate));

        return senderMessages.stream()
                .map(m->MessageDTO.builder()
                        .sender(m.getSender())
                        .receiver(m.getReceiver())
                        .date(m.getDate())
                        .message(m.getMessage())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<MessageDTO>  findAllMessagesOfDriver(String username) {
        List<Message> senderMessages = mr.findAll().stream().filter(m->m.getSender().equals(username)).collect(Collectors.toList());
        List<Message> receiverMessages = mr.findAll().stream().filter(m->m.getReceiver().equals(username)).collect(Collectors.toList());
        senderMessages.addAll(receiverMessages);
        Collections.sort(senderMessages, Comparator.comparingLong(Message::getDate));

        return senderMessages.stream()
                .map(m->MessageDTO.builder()
                        .sender(m.getSender())
                        .receiver(m.getReceiver())
                        .date(m.getDate())
                        .message(m.getMessage())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
