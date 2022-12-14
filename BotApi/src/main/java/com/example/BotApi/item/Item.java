package com.example.BotApi.item;

import javax.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String link;
    private String description;
    private String image;

    public void setId(Long id) {
        this.id = id;
    }

    public Item(
            Long id,
            String title,
            String link,
            String description,
            String image
    ) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.image = image;
    }

    public Item() {

    }


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Item updateWith(Item item) {
        return new Item(
                this.id,
                item.title,
                item.link,
                item.description,
                item.image
        );
    }
}
