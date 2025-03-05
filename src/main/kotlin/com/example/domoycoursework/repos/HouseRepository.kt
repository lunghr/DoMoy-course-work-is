package com.example.domoycoursework.repos

import com.example.domoycoursework.exceptions.InvalidUserDataException
import com.example.domoycoursework.exceptions.NotFoundException
import com.example.domoycoursework.models.House
import okhttp3.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class HouseRepository(
    private var jdbcTemplate: JdbcTemplate
) {
    fun save(address: String): House {
        val savedHouse = jdbcTemplate.query("SELECT * FROM create_house(?)", arrayOf(address)){rs, _ ->
            House(
                id = rs.getInt("id"),
                address = rs.getString("address")
            )
        }.firstOrNull()
        println(savedHouse)
        return savedHouse ?: throw InvalidUserDataException("House already exist in data base")
    }


    fun findHouseByAddress(address: String): House? = jdbcTemplate.query("SELECT * FROM find_house_by_address(?)", arrayOf(address)){rs, _ ->
        House(
            id = rs.getInt("id"),
            address = rs.getString("address")
        )
    }.firstOrNull()

    fun findHouseById(id: Int): House? = jdbcTemplate.query("SELECT * FROM find_house_by_id(?)", arrayOf(id)){rs, _ ->
        House(
            id = rs.getInt("id"),
            address = rs.getString("address")
        )
    }.firstOrNull()

    fun findFlatsByHouseId(id: Int): List<Int> = jdbcTemplate.query("SELECT * FROM find_flats_by_house_id(?)", arrayOf(id)){rs, _ ->
        rs.getInt("flat_number")
    }

}