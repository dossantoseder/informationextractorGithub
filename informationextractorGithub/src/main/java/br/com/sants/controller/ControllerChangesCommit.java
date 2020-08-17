package br.com.sants.controller;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import br.com.sants.data.ChangesCommitDAO;
import br.com.sants.model.Author;
import br.com.sants.model.Commit;
import br.com.sants.model.CommitChanges;
import br.com.sants.model.Files;
import br.com.sants.service.RetrofitLauncher;
import br.com.sants.service.ServiceCommit;
import br.com.sants.util.GenerateXLS;

public class ControllerChangesCommit implements Callback<CommitChanges> {
	private GenerateXLS generateXLS = new GenerateXLS();
	private HSSFWorkbook workbook;
	private HSSFSheet sheet = null;
	private HSSFRow row;
	private String API_VERSION_SPEC = "application/vnd.github.v3+json";
	private String accessToken = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	String ref;
	private String language = ".java";
	private static final String fileName = "/home/pereira/Documentos/dados_git/commit/nome_arquivo.xls";
	Map<String, String> mapaNomes = new HashMap<String, String>();

	public void start() {
		workbook = generateXLS.getInstance();
		String owner = "fiqryq";
		String repo = "Pantaucovid";
		int perPage = 100;
		ref = "fa9405848bf9e306a01a101fc1c1ece1b698ec07";

		ServiceCommit serviceCommit = new RetrofitLauncher().getServiceCommit();
		Call<CommitChanges> call = serviceCommit.referenceCommit(accessToken, API_VERSION_SPEC, owner, repo, ref,
				perPage);
		call.enqueue(this);
	}

	@Override
	public void onResponse(Call<CommitChanges> call, Response<CommitChanges> response) {
		System.out.println("URL: " + response.raw().request().url() + "\n");
		String filename = "/home/pereira/Documentos/dados_git/commit/" + ref + "_.xls";
		int iterator = 1;
		CommitChanges commitChanges = null;
		if (response.isSuccessful()) {
			commitChanges = response.body();
			
		} else {
			System.out.println("ERROR: " + response.errorBody());
		}
		/*sheet = generateXLS.openXLS(rowhead(), "Commits");
		fillLine(commitChanges, iterator);
		generateXLS.createXLS(filename);*/
		ChangesCommitDAO changesCommitDAO = new ChangesCommitDAO();
		changesCommitDAO.add(commitChanges);
		
	}

	@Override
	public void onFailure(Call<CommitChanges> call, Throwable t) {
		t.printStackTrace();
	}
}
