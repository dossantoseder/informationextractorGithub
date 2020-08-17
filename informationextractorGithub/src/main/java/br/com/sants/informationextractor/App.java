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
import br.com.sants.data.DAORepositories;
import br.com.sants.model.Author;
import br.com.sants.model.Commit;
import br.com.sants.model.Contributor;
import br.com.sants.model.Owner;
import br.com.sants.model.Repository;
import br.com.sants.controller.ControllerSearchDeveloper;

/**
 * Éder Pereira 05 de junho de 2020
 */
public class App {
	List<String> kOwner;
	String fileName ;
	int countRepository = 0;
	App (){
		kOwner = new ArrayList<String>();
	}
	public static void main(String[] args) {
		// Pesquisa repositórios por linguagem de programação
		// ControllerSearchRepositories controllerRepository = new
		// ControllerSearchRepositories();
		// controllerRepository.start();

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

		new App().returnChangesCommit();
		//new App().returnDeveloper();
		
		/*List<Contributor> listDevelopers = DAOListDevelopersRepository.readFileDevelopers("/home/pereira/Documentos/dados_git/repo/listDevelopersRepository.xls");
		for(Contributor developers : listDevelopers) {
			System.out.println(developers.getLogin());
		}*/
		
		/*HashMap<String, Map<String, String>> developers = DAOListDevelopersRepository.readFileDevelopersRepository("/home/pereira/Documentos/dados_git/repo/listDevelopersRepository_bkp.xls");
		Map<String, String> repositoryOwner = null;
		int numConsultations = developers.size();
		System.out.println(numConsultations);
		for (String key : developers.keySet()) {
            //Capturamos o valor a partir da chave
			repositoryOwner = developers.get(key);
			for (String keyRepositoryOwner : repositoryOwner.keySet()) {
				
					new App().returnCommitDevelope(repositoryOwner.get(keyRepositoryOwner), keyRepositoryOwner, key, numConsultations);
			}
		}*/
		
	}
	
	//Retorna os SHA(chave de identificação) dos commits que um desenvolvedor realizou no repositório
	public void returnCommitDevelope(String owner, String repository, String developer, int numConsultations) {
		ControllerSearchCommitDeveloper controllerSearchCommit = new ControllerSearchCommitDeveloper(owner, repository, developer, numConsultations);
		controllerSearchCommit.start();
	}
	//Retorna a lista de desenvolvedores de um repositório
	public void returnDevelopersRepository(int nextKey) {
		ControllerListDevelopersRepository cRepository = new ControllerListDevelopersRepository( "rn-contact-tracing", "MohGovIL");
		//cRepository.events.addObserver("getdevelopers", new ControllerSearchDeveloper());
		cRepository.start();
		
	}
	//Retorna os dados dos desenvolvedores
	public void returnDeveloper() {
		fileName = "/home/pereira/Documentos/dados_git/repositories.xls";
		ControllerSearchDeveloper controller = new ControllerSearchDeveloper(); 
		controller.start();
	}
	
	//Retorna os dados dos desenvolvedores
	public void returnChangesCommit() {
		ControllerChangesCommit controller = new ControllerChangesCommit(); 
		controller.start();
	}
	
}
