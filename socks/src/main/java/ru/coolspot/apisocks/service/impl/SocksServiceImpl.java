package ru.coolspot.apisocks.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coolspot.apisocks.dto.Quantity;
import ru.coolspot.apisocks.dto.SocksDto;
import ru.coolspot.apisocks.entity.Socks;
import ru.coolspot.apisocks.exception.NotFoundException;
import ru.coolspot.apisocks.mapper.SocksMapper;
import ru.coolspot.apisocks.other.Operation;
import ru.coolspot.apisocks.service.SocksService;
import ru.coolspot.apisocks.storage.SocksRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SocksServiceImpl implements SocksService {

    private final SocksRepository repository;
    private final SocksMapper mapper;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public SocksServiceImpl(SocksRepository repository, SocksMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Collection<SocksDto> getAllSocks() {
        return repository.findAll().stream().map(mapper::socksToSocksDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SocksDto addSocks(SocksDto socksDto) {
        Socks socks = repository.findSocksByColorAndCotton(socksDto.getColor(), socksDto.getCotton())
                .orElse(new Socks(null, socksDto.getColor(), socksDto.getCotton(), 0));
        socks.setQuantity(socks.getQuantity() + socksDto.getQuantity());
        repository.save(socks);
        log.info("Socks ={} - успешно добавлены на склад", socksDto);
        return mapper.socksToSocksDto(socks);
    }

    @Override
    @Transactional
    public SocksDto subSocks(SocksDto socksDto) {
        Socks socks = repository.findSocksByColorAndCotton(socksDto.getColor(), socksDto.getCotton())
                .orElseThrow(() -> new NotFoundException("Socks with color=" + socksDto.getColor() +
                        " and cottonPart =" + socksDto.getCotton() + " not found."));
        int quantity = socks.getQuantity() - socksDto.getQuantity();
        if (quantity < 0) {
            throw new RuntimeException();
        }
        socks.setQuantity(quantity);
        repository.save(socks);
        log.info("Socks ={}  - успешно отданы со склада", socksDto);
        return mapper.socksToSocksDto(socks);
    }

    @Override
    public Quantity getSocks(String color, Operation operation, Integer cottonPart) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> cr = builder.createQuery(Integer.class);
        Root<Socks> root = cr.from(Socks.class);
        List<Predicate> predicates = new ArrayList<>();

        if (color != null) {
            predicates.add(
                    builder.equal(root.get("color"), color));
        }
        if (cottonPart != null) {
            switch (operation)
                    {
                        case equal -> predicates.add(builder.equal(root.get("cotton"), cottonPart));
                        case lessThan -> predicates.add(builder.lt(root.get("cotton"), cottonPart));
                        case moreThan -> predicates.add(builder.gt(root.get("cotton"), cottonPart));
                    }
        }

        cr.select(builder.sum(root.get("quantity"))).where(predicates.toArray(new Predicate[]{}));
        Integer result = entityManager.createQuery(cr).getSingleResult();
        result = (result != null) ? result : 0;
        log.info("Количество Socks на складе с указанными параметрами = {}", result);
        return new Quantity(result);
    }
}