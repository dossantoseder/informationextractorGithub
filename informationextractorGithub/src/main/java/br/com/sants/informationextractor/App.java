package br.com.sants.informationextractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import br.com.sants.controller.ControllerChangesCommit;
import br.com.sants.controller.ControllerListDevelopersRepository;
import br.com.sants.controller.ControllerListRepositoriesDeveloper;
import br.com.sants.controller.ControllerSearchCommitDeveloper;
import br.com.sants.controller.ControllerSearchRepositories;
import br.com.sants.model.Author;
import br.com.sants.model.Commit;
import br.com.sants.model.Owner;
import br.com.sants.model.Repository;
import br.com.sants.controller.ControllerSearchDeveloper;

/**
 * Éder Pereira 05 de junho de 2020
 */
public class App {
	List<String> kOwner;
	App (){
		kOwner = new ArrayList<String>();
	}
	public static void main(String[] args) {
		// Pesquisa repositórios por linguagem de programação
		// ControllerSearchRepositories controllerRepository = new
		// ControllerSearchRepositories();
		// controllerRepository.start();

		String fileName = "/home/pereira/Documentos/dados_git/repositories.xls";

		// Início do processamento
		/*
		 * //Lista os desenvolvedores de um repositório
		 * ControllerListDevelopersRepository cRepository = new
		 * ControllerListDevelopersRepository(); cRepository.start();
		 * 
		 * //Pesquisa por um desenvolvedor ControllerSearchDeveloper controller = new
		 * ControllerSearchDeveloper(); controller.start();
		 * 
		 * //Lista os repositórios de um desenvolvedor
		 * ControllerListRepositoriesDeveloper listRepositoriesDeveloper = new
		 * ControllerListRepositoriesDeveloper(); listRepositoriesDeveloper.start();
		 * 
		 * //Pesquisa commits por desenvolvedor ControllerSearchCommitDeveloper
		 * controllerSearchCommit = new ControllerSearchCommitDeveloper();
		 * controllerSearchCommit.start();
		 *
		 * Pesquisa dados de um commit ControllerChangesCommit controllerChangesCommit =
		 * new ControllerChangesCommit(); controllerChangesCommit.start();
		 */

		/*
		 * Map<String, String> mapaNomes = new App().readFileRepository(fileName); for
		 * (String key : mapaNomes.keySet()) { System.out.println(key + " : " +
		 * mapaNomes.get(key)); }
		 */

		new App().returnDevelopersRepository(0, fileName);

	}

	public void returnDevelopersRepository(int nextKey, String file) {
		Map<String, String> mapaNomes = readFileRepository(file);
		
		String owner = kOwner.get(nextKey);
		String repository = mapaNomes.get(kOwner.get(nextKey));
		
		ControllerListDevelopersRepository cRepository = new ControllerListDevelopersRepository(repository);
		cRepository.events.addObserver("getdevelopers", new ControllerSearchDeveloper());
		cRepository.start(owner, repository);
		
	}

	// Ler arquivos de commits dos colaboradores
	public Map<String, String> readFileCommit(String fileName) {
		Map<String, String> mapaCommits = new HashMap<String, String>();
		try {
			FileInputStream arquivo = new FileInputStream(new File(fileName));
			HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
			HSSFSheet sheetCommits = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheetCommits.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				Commit commit = new Commit();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getColumnIndex()) {
					case 0:
						commit.setSha(cell.getStringCellValue());
						break;
					case 2:
						Author author = new Author();
						author.setLogin(cell.getStringCellValue());
						commit.setAuthor(author);
						break;
					}
				}
				if (!commit.getSha().equals("Sha"))
					mapaCommits.put(commit.getSha(), commit.getAuthor().getLogin());
			}
			arquivo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo Excel não encontrado!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapaCommits;

	}

	// Ler arquivos com os projetos dos softwares
	public Map<String, String> readFileRepository(String fileName) {
		Map<String, String> mapaRepository = new HashMap<String, String>();
		try {
			FileInputStream arquivo = new FileInputStream(new File(fileName));
			HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
			HSSFSheet sheetCommits = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheetCommits.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				Repository repository = new Repository();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getColumnIndex()) {
					case 1:
						repository.setName(cell.getStringCellValue());
						// System.out.println(cell.getCellType());
						// Chamar método para escrever xls com nova tabela de dados de um contribuitor

						break;
					case 2:
						Owner owner = new Owner();
						owner.setLogin(cell.getStringCellValue());
						repository.setOwner(owner);
						break;
					}
				}
				if ((!repository.getName().equals("Name")) && (!repository.getName().equals(""))) {
					mapaRepository.put(repository.getOwner().getLogin(), repository.getName());
					kOwner.add(repository.getOwner().getLogin());
				}
			}
			arquivo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo Excel não encontrado!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapaRepository;

	}
}
