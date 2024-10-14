package com.api.prisma_vi.palette;

import com.api.prisma_vi.colors.Colors;
import com.api.prisma_vi.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Palette {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "users_id")
    protected Users user;

    @ManyToMany
    protected List<Colors> colors;

}
