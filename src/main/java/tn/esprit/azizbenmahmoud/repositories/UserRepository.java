package tn.esprit.azizbenmahmoud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.azizbenmahmoud.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
