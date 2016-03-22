package org.mysql.neo4j.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.mysql.neo4j.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller("countryDAO")
public class CountryDAOImpl implements CountryDAO {
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//@Transactional
	public List<Country> findAllCountries() {
		
		String sql = "SELECT * FROM country";
		Connection conn = null;

		List<Country> countries = new ArrayList<Country>();
		try {
			conn = dataSource.getConnection("root", "dakhla");
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Country c = new Country();
				c.setCountryId(rs.getInt("country_id"));
				c.setCountry(rs.getString("country"));
				countries.add(c);
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
		
		return countries;

	}

}
