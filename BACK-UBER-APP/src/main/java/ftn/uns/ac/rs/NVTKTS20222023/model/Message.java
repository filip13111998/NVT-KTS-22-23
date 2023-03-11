package ftn.uns.ac.rs.NVTKTS20222023.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender; //Ako salje citizen onda je citizen...ili ako salje admin onda je admin.

    private String receiver; //primalac

    private String message; //Tekst poruke

    private String type; //BLOCK ili CHAT ili NOTIFICATION

    private boolean answered;// Kada admin odgovori setuje ovo polje na true

    private Long date; //Datum kada je poruka poslata

    //private Boolean active;

}
