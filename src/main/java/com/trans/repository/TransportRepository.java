package com.trans.repository;


import com.trans.model.Cargo;
import com.trans.model.Transport;
import com.trans.model.enums.TypeTransport;
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
public interface TransportRepository extends JpaRepository<Transport,Integer> {
    List<Transport> findAll();

    @Query("select c from Transport c where c.isDelete = false and c.isFree = true")
    Page<Transport> findAllByDeleteIsFalseAndFreeIsTrue(Pageable pageable);

    @Query("select t from Transport t where t.user.id = ?1 and t.isDelete <> true")
    Page<Transport> findAllByUserIdAndDeleteIsNot(int user_id, Pageable pageable);

    Page<Transport> findAllByUserId(int user_id, Pageable pageable);
    Transport findById(int id);
    void deleteById(int id);
    boolean deleteAllByUserId(int user_id);
    Page<Transport> findAllByType(TypeTransport type, Pageable pageable);
//        List<Transport> findAllByTypeAndFree(TypeTransport type,boolean isFree);

    @Query("select t from Transport t where concat(t.name,t.maxCapacityLoad,t.type,t.user.activity) " +
            "like %?1% and t.isFree = true and t.isDelete = false")
    Page<Transport> searchAllByKeyword(String keyword,Pageable pageable);


    @Query("select t from Transport t where t.isDelete = false and t.isFree = true")
    List<Transport> findAllByDeleteIsFalseAndFreeIsTrue();

    Page<Transport>findAll(Specification<Transport> transportSpecification, Pageable pageable);

    static Specification<Transport> transportFilter(
                                            String name,
                                            TypeTransport type,
                                            Double maxCapacityLoad){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(name)) {
//                predicates.add(cb.equal(root.get("name"), name));
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (type != null && !type.getTypeValue().equalsIgnoreCase("any")) {
//                predicates.add(cb.equal(root.get("cityFrom"), cityFrom));
                predicates.add(cb.equal(root.get("type"), type));

            }
            if (maxCapacityLoad != null) {
                predicates.add(cb.equal(root.get("maxCapacityLoad"), maxCapacityLoad));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}