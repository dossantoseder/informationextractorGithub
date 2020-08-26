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

	public void add(CommitChanges commitChanges, String project) {
		//public void add(CommitChanges commitChanges, Commit commit) {
		String sql = "INSERT INTO public.changescommit(iddeveloper, filename, status, changes, additions, deletions, date, sha, project) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String language = ".java";
		try {
			for (Files changes : commitChanges.getFiles()) {

				PreparedStatement stmt = this.con.prepareStatement(sql);
				System.out.println(changes.getFilename());
				if (changes.getFilename().toLowerCase().contains(language.toLowerCase())) {
					stmt.setLong(1, commitChanges.getAuthor().getId());
					stmt.setString(2, changes.getFilename());
					stmt.setString(3, changes.getStatus());
					stmt.setInt(4, changes.getChanges());
					stmt.setInt(5, changes.getAdditions());
					stmt.setInt(6, changes.getDeletions());
					stmt.setString(7, commitChanges.getCommit().getCommitter().getDate());
					stmt.setString(8, commitChanges.getSha());
					stmt.setString(9, project);

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
