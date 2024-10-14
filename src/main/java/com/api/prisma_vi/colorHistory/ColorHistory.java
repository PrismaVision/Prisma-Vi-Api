package com.api.prisma_vi.colorHistory;

import com.api.prisma_vi.colors.Colors;
import com.api.prisma_vi.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ColorHistory {

    @Id @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "users_id")
    protected Users user;

    private List<Colors> colors;

}
