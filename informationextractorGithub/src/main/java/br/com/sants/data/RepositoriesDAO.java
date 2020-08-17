package br.com.sants.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sants.model.Author;
import br.com.sants.model.Commit;
import br.com.sants.model.Owner;
import br.com.sants.model.Repositories;
import br.com.sants.model.Repository;
import br.com.sants.model.User;

public class RepositoriesDAO {
	private PreparedStatement stmt;
	private Connection con;

	public RepositoriesDAO() {
		this.con = ConnectionSingleton.getInstance().getConnection();
	}
	
	public List<Repository> getRepository() {
		try {
			List<Repository> repositories = new ArrayList<Repository>();
			PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM repositories");
			ResultSet rs = stmt.executeQuery();
			Owner owner;
			while (rs.next()) {

				Repository repository = new Repository();
				repository.setId(rs.getInt("idrepository"));
				repository.setName(rs.getString("repository"));
				owner = new Owner();
				owner.setLogin(rs.getString("owner"));
				repository.setOwner(owner);

				repositories.add(repository);
			}
			rs.close();
			stmt.close();
			return repositories;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
