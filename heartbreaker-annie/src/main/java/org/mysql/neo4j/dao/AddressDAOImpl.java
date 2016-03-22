package org.mysql.neo4j.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mysql.neo4j.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller("addressDAO")
public class AddressDAOImpl implements AddressDAO {
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	//@Transactional
	public List<Address> findAddressesByCity(int cityId) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM address WHERE city_id = ?";
		
		Connection conn = null;
		List<Address> addresses = new ArrayList<Address>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cityId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Address address = new Address();
				address.setAddressId(rs.getInt("address_id"));
				address.setAddress(rs.getString("address"));
				address.setPostalCode(rs.getString("postal_code"));
				addresses.add(address);
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
		return addresses;
	}

}
