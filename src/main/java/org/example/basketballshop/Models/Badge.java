package org.example.basketballshop.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Например: "Первый заказ"
    private String description; // Описание: "Совершите первый заказ"
    private int requiredPoints; // Можно использовать для условий получения


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "icon_image_info_id")
    private ImageInfo iconImageInfo;

    @ManyToMany
    @JoinTable(
            name = "user_badge",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    private List<User> users = new ArrayList<>();


    // Getters and setters

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "requiredPoints = " + requiredPoints + ", " +
                "iconImageInfo = " + iconImageInfo + ")";
    }
}