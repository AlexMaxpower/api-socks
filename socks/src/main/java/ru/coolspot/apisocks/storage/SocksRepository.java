package ru.coolspot.apisocks.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.coolspot.apisocks.dto.Quantity;
import ru.coolspot.apisocks.entity.Socks;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {

    List<Socks> findAll();
    Optional<Socks> findSocksByColorAndCotton(String color, Byte Cotton);

    @Query(value = "SELECT SUM(quantity) FROM socks", nativeQuery = true)
    Integer getQuantity();



}