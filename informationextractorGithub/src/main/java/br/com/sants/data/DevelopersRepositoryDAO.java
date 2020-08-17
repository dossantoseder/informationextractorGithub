package br.com.sants.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sants.model.Contributor;

public class DevelopersRepositoryDAO {
	private PreparedStatement stmt;
	private Connection con;

	public DevelopersRepositoryDAO() {
		this.con = ConnectionSingleton.getInstance().getConnection();
	}
	
	public void add(List<Contributor> listDeveloper, String repo, String owner) {
		String sql = "INSERT INTO public.developersrepo( iddeveloper, developer, contributions, repository, owner)VALUES (?, ?, ?, ?, ?)";
		try {
			for (Contributor developer : listDeveloper) {

				PreparedStatement stmt = this.con.prepareStatement(sql);

				stmt.setLong(1, developer.getId());
				stmt.setString(2, developer.getLogin());
				stmt.setInt(3, developer.getContributions());
				stmt.setString(4, repo);
				stmt.setString(5, owner);

				stmt.execute();
				stmt.close();
			}
			System.out.println("Dados salvo!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Contributor> getDeveloper() {
		try {
			List<Contributor> developers = new ArrayList<Contributor>();
			PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM developersrepo");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				Contributor developer = new Contributor();
				developer.setId(rs.getLong("iddeveloper"));
				developer.setLogin(rs.getString("developer"));
				developer.setContributions(rs.getInt("contributions"));
				developer.setRepository(rs.getString("repository"));
				developer.setOwner(rs.getString("owner"));

				developers.add(developer);
			}
			rs.close();
			stmt.close();
			return developers;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
