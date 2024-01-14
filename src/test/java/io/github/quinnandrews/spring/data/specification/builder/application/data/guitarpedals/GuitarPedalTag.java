package io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "guitar_pedal_tag")
@Entity
public class GuitarPedalTag {

    @Id
    @Column(name = "id",
            columnDefinition = "BIGINT",
            nullable = false,
            updatable = false)
    private Long id;

    @Column(name = "tag",
            columnDefinition = "VARCHAR(16)",
            nullable = false)
    private String tag;

    public GuitarPedalTag() {
        // no-op
    }

    public Long getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }
}
