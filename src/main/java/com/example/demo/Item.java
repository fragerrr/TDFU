package com.example.demo;

import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class Item {
    private Integer id;
    private String text;
    private boolean done;
    private Date date;
    private String color;
    private String fColor;

    public Item(Integer id, String text, boolean done, Date date) {
        this.id = id;
        this.text = text;
        this.done = done;
        this.date = date;
    }
}
