package com.muscat.cards.repository;

import com.muscat.cards.entity.Cards;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {

  Optional<Cards> findByMobileNumber(String mobileNumber);

  Optional<Cards> findByCardNumber(String loanNumber);

}
