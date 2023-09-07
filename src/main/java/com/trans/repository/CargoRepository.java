package com.trans.repository;


import com.trans.model.Cargo;
import com.trans.model.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    Page<Cargo> findAllByUserId(int user_id, Pageable pageable);

    @Query("select c from Cargo c where c.user.id = ?1 and c.isDelete <> true")
    Page<Cargo> findAllByUserIdAndDeleteIsNot(int user_id, Pageable pageable);

    Cargo findById(int id);

    void deleteById(int id);

    void deleteAllByUserId(int user_id);

    Page<Cargo> findAllByCityFromContaining(String cityFrom, Pageable pageable);
    @Query("select c from Cargo c where" +
            " concat(c.name,c.cityFrom,c.cityTo,c.localDateCreated,c.price,c.volume,c.weight,"+
            "c.localDateDeadline,c.typeTransport,c.user.activity,c.countryFrom,c.countryTo) " +
            "like %?1% and c.isFree = true and c.isDelete = false")
    Page<Cargo> searchAllByKeyword(String keyword,Pageable pageable);

    @Query("select c from Cargo c where c.isDelete = false and c.isFree = true")
    Page<Cargo> findAllByDeleteIsFalseAndFreeIsTrue(Pageable pageable);
    @Query("select c from Cargo c where c.isDelete = false and c.isFree = true")
    List<Cargo> findAllByDeleteIsFalseAndFreeIsTrue();

    Page<Cargo>findAll(Specification<Cargo> cargoSpecification, Pageable pageable);

    static Specification<Cargo> cargoFilter(String name,
                                            String countryFrom,
                                            String countryTo,
                                            String cityFrom,
                                            String cityTo,
                                            Double price,
                                            Double volume,
                                            Double weight
    ){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(name)) {
//                predicates.add(cb.equal(root.get("name"), name));
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(cityFrom)) {
//                predicates.add(cb.equal(root.get("cityFrom"), cityFrom));
                predicates.add(cb.like(cb.lower(root.get("cityFrom")), "%" + cityFrom.toLowerCase() + "%"));

            }

            if (StringUtils.hasText(cityTo)) {
//                predicates.add(cb.equal(root.get("cityTo"), cityTo));
                predicates.add(cb.like(cb.lower(root.get("cityTo")), "%" + cityTo.toLowerCase() + "%"));

            }
            if (price != null) {
                predicates.add(cb.equal(root.get("price"), price));
            }
            if (volume != null) {
                predicates.add(cb.equal(root.get("volume"), volume));
            }
            if (weight != null) {
                predicates.add(cb.equal(root.get("weight"), weight));
            }

//            if (StringUtils.hasText(phoneNumber)) {
//                predicates.add(cb.equal(root.get("phoneNumber"), phoneNumber));
//            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
