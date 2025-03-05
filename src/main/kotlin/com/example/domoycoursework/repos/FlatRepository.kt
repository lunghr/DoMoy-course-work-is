package com.example.domoycoursework.repos

import com.example.domoycoursework.exceptions.InvalidUserDataException
import com.example.domoycoursework.models.Flat
import com.example.domoycoursework.models.House
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class FlatRepository(
    private var jdbcTemplate: JdbcTemplate
) {

    fun save(flat: Flat): Flat =
        jdbcTemplate.query(
            "SELECT * FROM create_flat(?, ?, ?, ?)", arrayOf(
                flat.houseId,
                flat.flatNumber,
                flat.cadastralNumber,
                flat.ownerId
            )
        ) { rs, _ ->
            toFlat(rs)
        }.first() ?: throw InvalidUserDataException("Failed to save flat")

    fun findFlatByHouseAndFlatNumber(house: House, flatNumber: Int): Flat? =
        jdbcTemplate.query(
            "SELECT * FROM find_flat_by_house_and_flat_number(?, ?)", arrayOf(
                house.address,
                flatNumber
            )
        ) { rs, _ ->
            toFlat(rs)
        }.firstOrNull()

    fun toFlat(rs: java.sql.ResultSet): Flat {
        return Flat(
            id = rs.getInt("id"),
            houseId = rs.getInt("house_id"),
            flatNumber = rs.getInt("flat_number"),
            cadastralNumber = rs.getString("cadastral_number"),
            ownerId = rs.getInt("owner_id")
        )
    }


}