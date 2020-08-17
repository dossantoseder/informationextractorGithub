package br.com.sants.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.sants.model.Commit;
import br.com.sants.model.User;

public class DevelopersDAO {
	private PreparedStatement stmt;
	private Connection con;

	public DevelopersDAO() {
		this.con = ConnectionSingleton.getInstance().getConnection();
	}
	
	public void add(User developer) {
		String sql = "INSERT INTO public.developer( iddeveloper, developer, numberrepository, createdat, updatedat) VALUES (?, ?, ?, ?,?)";
		try {

				PreparedStatement stmt = this.con.prepareStatement(sql);

				stmt.setLong(1,developer.getId() );
				stmt.setString(2, developer.getLogin());
				stmt.setInt(3, developer.getPublic_repos());
				stmt.setString(4, developer.getCreated_at());
				stmt.setString(5, developer.getUpdated_at());

				stmt.execute();
				stmt.close();
			System.out.println("Dados salvo!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
