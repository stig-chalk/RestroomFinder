package com.Kinghao.mapper;

import com.Kinghao.bean.Place;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PlaceMapper {
    @Select(value = "select * from Restrooms as r where r.id=#{id}")
    Place findPlaceById(@Param("id") String id);

    @Insert(value =
            "insert into Restrooms values(" +
                "#{id}, " +
                "#{clean}, " +
                "#{busy}, " +
                "#{rating}, " +
                "#{userTotalRatings}, " +
                "#{accessTlt}, " +
                "#{paper}, " +
                "#{soap}, " +
                "#{genInclus}" +
            ");")
    void insertRecord(Place place);

    @Update(value =
            "update Restrooms " +
            "set clean=#{clean}, " +
                "busy=#{busy}, " +
                "accessTlt=#{accessTlt}, " +
                "paper=#{paper}, " +
                "soap=#{soap}, " +
                "genInclus=#{genInclus}, " +
                "rating=#{rating}, " +
                "userTotalRatings=#{userTotalRatings} " +
            "where id=#{id};")
    void updateRecord(Place place);
}

