package com.api.prisma_vi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

     UserDetails findByEmail(String email);

     Boolean existsByEmail(String email);
}
