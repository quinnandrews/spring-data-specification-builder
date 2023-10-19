package org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "manufacturer")
@Entity
public class Manufacturer {

    @Id
    @Column(name = "id",
            columnDefinition = "BIGINT",
            nullable = false,
            updatable = false)
    private Long id;

    @Column(name = "name",
            columnDefinition = "VARCHAR(32)",
            nullable = false)
    private String name;

    public Manufacturer() {
        // no-op
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
