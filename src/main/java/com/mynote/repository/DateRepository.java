package com.mynote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mynote.model.DateModel;


@Repository
public interface DateRepository extends CrudRepository<DateModel, Long> {
	
	
	@Query("select d.id from DateModel d where d.date like %?1%")
	Long findByDate(String date);

	@Query("select d from DateModel d where d.date like %?1%")
	DateModel findByData(String date);
	
	@Query("select d from DateModel d order by d.date ASC")
	List<DateModel> findAllInOrder();
}
