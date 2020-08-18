package br.com.sants.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.sants.model.Commit;
import br.com.sants.model.CommitChanges;
import br.com.sants.model.Files;

public class ChangesCommitDAO {
	private PreparedStatement stmt;
	private Connection con;

	public ChangesCommitDAO() {
		this.con = ConnectionSingleton.getInstance().getConnection();
	}

	public void add(CommitChanges commitChanges, String commit) {
		//public void add(CommitChanges commitChanges, Commit commit) {
		String sql = "INSERT INTO public.changescommit(iddeveloper, changes, status, filename, sha, project) VALUES (?, ?, ?, ?, ?, ?)";
		String language = ".java";
		try {
			for (Files changes : commitChanges.getFiles()) {

				PreparedStatement stmt = this.con.prepareStatement(sql);
				if (changes.getFilename().toLowerCase().contains(language.toLowerCase())) {
					stmt.setLong(1, commitChanges.getAuthor().getId());
					stmt.setString(2, changes.getChanges());
					stmt.setString(3, changes.getStatus());
					stmt.setString(4, changes.getFilename());
					stmt.setString(5, commitChanges.getSha());
					stmt.setString(6, commit);

					stmt.execute();
					stmt.close();
				}
			}
			System.out.println("Dados salvo!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
