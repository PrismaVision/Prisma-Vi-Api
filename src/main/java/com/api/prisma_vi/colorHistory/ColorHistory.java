package com.api.prisma_vi.colorHistory;

import com.api.prisma_vi.colors.Colors;
import com.api.prisma_vi.user.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ColorHistory {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "users_id")
//    @Setter(AccessLevel.NONE)
    protected Users user;

    @ManyToMany
    private List<Colors> colors;

}
