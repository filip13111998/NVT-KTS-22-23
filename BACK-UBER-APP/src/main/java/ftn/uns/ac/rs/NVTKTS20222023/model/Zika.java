package ftn.uns.ac.rs.NVTKTS20222023.model;

import javax.persistence.*;

@Entity
@Table(name = "zikas")
public class Zika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    public Zika() {

    }

    public Zika(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Zika{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
