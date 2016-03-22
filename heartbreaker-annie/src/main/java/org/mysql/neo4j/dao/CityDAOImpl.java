package org.mysql.neo4j.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mysql.neo4j.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller("cityDAO")
public class CityDAOImpl implements CityDAO {
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	//@Transactional
	public List<City> findCitiesByCountry(int countryId) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM city WHERE country_id = ?";
		
		Connection conn = null;
		List<City> cities = new ArrayList<City>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, countryId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				City city = new City();
				city.setCityId(rs.getInt("city_id"));
				city.setCity(rs.getString("city"));
				cities.add(city);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
		return cities;
	}

}
