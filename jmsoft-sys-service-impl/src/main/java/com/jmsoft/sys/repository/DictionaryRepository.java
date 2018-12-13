package com.jmsoft.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmsoft.sys.entity.Dictionary;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long>{

	List<Dictionary> findByStatusOrderBySortAsc(Integer status);

	long countByIdAndStatus(Long id, Integer status);

	long countByTokenAndStatus(String token, Integer status);

	Dictionary findTop1ByTokenAndStatus(String token, Integer status);

	Dictionary findTop1ByIdAndStatus(Long id, Integer status);

}
