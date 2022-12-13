package com.example.BotApi.item;

import org.springframework.data.annotation.Id;

public class Item {
    private final Long id;
    private final String title;
    private final String link;
    private final String description;
    private final String image;

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

    @Id
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
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
