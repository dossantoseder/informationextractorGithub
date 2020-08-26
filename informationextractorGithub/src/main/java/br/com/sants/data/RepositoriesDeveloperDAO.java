package br.com.sants.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.sants.model.Contributor;
import br.com.sants.model.Repository;

public class RepositoriesDeveloperDAO {
	private PreparedStatement stmt;
	private Connection con;

	public RepositoriesDeveloperDAO() {
		this.con = ConnectionSingleton.getInstance().getConnection();
	}

	public void add(List<Repository> listRepositoriesDeveloper, Contributor developer) {
		String sql = "INSERT INTO public.repodeveloper(iddeveloper, developer, language)VALUES (?, ?, ?)";
		try {
			for (Repository r : listRepositoriesDeveloper) {

				PreparedStatement stmt = this.con.prepareStatement(sql);

				stmt.setLong(1, developer.getId());
				stmt.setString(2, developer.getLogin());
				if (!r.getLanguage().equals(""))
					stmt.setString(3, r.getLanguage());

				stmt.execute();
				stmt.close();
			}
			System.out.println("Dados salvo!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
