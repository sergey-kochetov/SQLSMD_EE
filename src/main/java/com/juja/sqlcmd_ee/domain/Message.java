package com.juja.sqlcmd_ee.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "Message > 2048")
    private String text;

    @NotBlank(message = "Please fill title")
    @Length(max = 255, message = "Title > 255")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer author;

    private String filename;

    public Message() {
    }

    public Message(String text, String tag, Customer author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthor() {
        return author != null ? author.getUsername() : "<none>";
    }
}
