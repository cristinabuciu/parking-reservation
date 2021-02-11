package com.acs.parking.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column (nullable = false)
    private String password;
    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column (nullable = false)
    private Boolean enabled;
    @Column (nullable = false)
    private String name;
    @Column (unique = true, nullable = false)
    private String email;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = UserRole.USER;
        this.enabled = true;
    }

    public UserEntity(String username, String password, UserRole role, String name, String email, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.name = name;
        this.email = email;
    }
}