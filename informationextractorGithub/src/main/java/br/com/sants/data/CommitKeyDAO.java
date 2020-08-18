package br.com.sants.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.sants.model.Author;
import br.com.sants.model.Commit;

public class CommitKeyDAO {
	private PreparedStatement stmt;
	private Connection con;

	public CommitKeyDAO() {
		this.con = ConnectionSingleton.getInstance().getConnection();
	}

	public void add(List<Commit> listCommitDeveloper, String repo, String owner) {
		String sql = "INSERT INTO public.commit(sha, iddeveloper, developer, repository, owner) VALUES (?, ?, ?, ?, ?);";
		try {
			for (Commit commit : listCommitDeveloper) {

				PreparedStatement stmt = this.con.prepareStatement(sql);

				stmt.setString(1, commit.getSha());
				stmt.setInt(2, commit.getAuthor().getId());
				stmt.setString(3, commit.getAuthor().getLogin());
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

	public List<Commit> getLista() {
		try {
			List<Commit> commits = new ArrayList<Commit>();
			PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM commit WHERE idcommit IN(1265, 1266, 1267)");
			ResultSet rs = stmt.executeQuery();
			Author developer;
			while (rs.next()) {

				Commit commit = new Commit();
				commit.setSha(rs.getString("sha"));
				developer = new Author();
				developer.setId(rs.getInt("iddeveloper"));
				developer.setLogin(rs.getString("developer"));
				commit.setAuthor(developer);
				commit.setRepository(rs.getString("repository"));
				commit.setOwner(rs.getString("owner"));

				commits.add(commit);
			}
			rs.close();
			stmt.close();
			return commits;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
