package com.ef.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ef.dto.LogAccessDTO;
import com.ef.model.LogAccess;

@Repository
public interface LogAccessRepository extends CrudRepository<LogAccess, Long> {
	
	 @Query("SELECT new com.ef.dto.LogAccessDTO(l2.ipAddress, count(*)) FROM LogAccess l2 "
	 		+ " where (DATE_FORMAT(l2.date, '%Y-%m-%d %T.%f') >= :startDate and DATE_FORMAT(l2.date, '%Y-%m-%d %T.%f') <= :finishDate )"
	 		+ " group by l2.ipAddress having count(*) > :threshold)")
	 List<LogAccessDTO> findIpAddressMoreThan(@Param("startDate") String startDate,
				   @Param("finishDate") String finishDate,
				   @Param("threshold") long threshold);

	
}
