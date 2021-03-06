package com.tehnomarket.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.tehnomarket.controller.DBManager;
import com.tehnomarket.model.Characteristics;

@Component
public class CharacteristicsDao {

	private Connection connection;
	
	private CharacteristicsDao() {
		connection = DBManager.getInstance().getConnection();
	}	
	
	public ArrayList<Characteristics> getAllProductChar(int productId) throws SQLException{
		ArrayList<Characteristics> all = new ArrayList<Characteristics>();
		//\r\n
		String sql = "SELECT P.products_id,P.characteristics_id,C.name,P.input " + 
					"FROM product_characteristics P " + 
					"LEFT JOIN characteristics C " + 
					"ON ( P.characteristics_id = C.characteristics_id) " + 
					"WHERE P.products_id=?;";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		
		ps.setInt(1, productId);
		ResultSet result = ps.executeQuery();
		
		
		// handle with an empty arraylist if no characteristics are in db
		if(result==null || result.wasNull()) {
			return all;
		}
		
		while(result.next()) {
			
			Characteristics c = new Characteristics(
							result.getInt("products_id"),
							result.getInt("characteristics_id"),
							result.getString("name"),
							result.getString("input")
					);
			all.add(c);
			
		}
		
		return all;
	}

	
	public ArrayList<Characteristics> getCategoryCharacteristics(int catId) throws SQLException {
			ArrayList<Characteristics> all = new ArrayList<Characteristics>();
		
		String sql = "SELECT characteristics_id,name From characteristics Where categories_id = ?";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		
		ps.setInt(1, catId);
		ResultSet result = ps.executeQuery();
		
		while(result.next()) {
			
			Characteristics c = new Characteristics(
							0,
							result.getInt("characteristics_id"),
							result.getString("name"),
							""
					);
			all.add(c);
			
		}
		
		return all;
	}
	
}
