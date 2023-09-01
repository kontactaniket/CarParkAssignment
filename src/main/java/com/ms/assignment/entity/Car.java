package com.ms.assignment.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CAR")
public class Car {
    @Id@Column(name = "ID")
    @GeneratedValue(generator = "car-sequence-generator")
    @GenericGenerator(
            name = "car-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "car_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Integer id;
    @Column(name = "REGISTRATION")
    private String registration;
    @Column(name = "TYPE")
    private String type;
    @OneToOne(mappedBy="car")
    @JoinColumn(name = "CAR_ID")
    private ParkingDetails details;
}