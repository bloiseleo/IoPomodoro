package io.pomodoro.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profiles_id_seq")
    @SequenceGenerator(name = "profiles_id_seq", allocationSize = 1, sequenceName = "profiles_id_seq")
    private int id;
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;
    @Column(name = "profile_photo", nullable = false) // Matches the table definition
    private String profilePhoto;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
