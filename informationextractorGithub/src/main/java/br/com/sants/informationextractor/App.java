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
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import br.com.sants.controller.ControllerCommitDeveloper;
import br.com.sants.controller.ControllerDevelopersRepository;
import br.com.sants.controller.ControllerDeveloper;
import br.com.sants.controller.ControllerChangesCommit;
import br.com.sants.controller.ControllerListDevelopersRepository;
import br.com.sants.controller.ControllerListRepositoriesDeveloper;
import br.com.sants.controller.ControllerSearchCommitDeveloper;
import br.com.sants.controller.ControllerSearchRepositories;
import br.com.sants.data.CommitKeyDAO;
import br.com.sants.data.DAORepositories;
import br.com.sants.data.DevelopersRepositoryDAO;
import br.com.sants.data.RepositoriesDAO;
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
	static int index = 0;
	static List<Commit> commits;
	public static void main(String[] args) {
		commits = getCommitSHA();
		//new App().ControllerCommitDeveloper();
		
		final long time = 2400; 
		Timer timer = new Timer();
		TimerTask tarefa = new TimerTask() {
			public void run() {
				if(index < commits.size()) {
					new App().returnChangesCommitTime();
				}else {
					System.out.println("Execução finalizada!");
					System.exit(0);
				}
				try {
					System.out.println("Executando!");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		timer.scheduleAtFixedRate(tarefa, time, time);
		
		//new App().returnChangesCommit();

	}
	
	// Retorna as modificações no arquivos comitados dos desenvolvedores
		public void returnChangesCommitTime() {
			Commit c =  commits.get(index);
			if( index <= commits.size())
				index ++;
			ControllerChangesCommit controller = new ControllerChangesCommit(c);
			controller.start();
		}

	// Novo método para retorna a lista de desenvolvedores de um repositório
	public void ControllerDevelopersRepository() {
		for (Repository r : getRepositories()) {
			ControllerDevelopersRepository cRepository = new ControllerDevelopersRepository(r);
			cRepository.start();
		}

	}

	// Retorna os dados dos desenvolvedores
	public void ControllerDeveloper() {
		for (Contributor d : getDevelopers()) {
			ControllerDeveloper controller = new ControllerDeveloper(d);
			controller.start();
		}
	}

	// Novo método retorna os SHA(chave de identificação) dos commits que um desenvolvedor
		// realizou no repositório
		public void ControllerCommitDeveloper() {
			for (Contributor d : getDevelopers()) {
				ControllerCommitDeveloper cCommitDeveloper = new ControllerCommitDeveloper(d);
				cCommitDeveloper.start();
			}
		}
	/*
	 * Retorna a lista de desenvolvedores de um repositório public void
	 * returnDevelopersRepository() { for (Repository r : getRepositories()) {
	 * ControllerListDevelopersRepository cRepository = new
	 * ControllerListDevelopersRepository(r); //
	 * cRepository.events.addObserver("getdevelopers", new //
	 * ControllerSearchDeveloper()); cRepository.start(); }
	 * 
	 * }
	 */

	/*
	 * Retorna os dados dos desenvolvedores public void returnDeveloper() { for
	 * (Contributor d : getDevelopers()) { ControllerSearchDeveloper controller =
	 * new ControllerSearchDeveloper(d); controller.start(); } }
	 */

	// Lista os repositórios de um desenvolvedor
	public void returnRepositorieDeveloper() {
		for (Contributor d : getDevelopers()) {
			ControllerListRepositoriesDeveloper listRepositoriesDeveloper = new ControllerListRepositoriesDeveloper(d);
			listRepositoriesDeveloper.start();
		}
	}

	// Retorna os SHA(chave de identificação) dos commits que um desenvolvedor
	// realizou no repositório
	public void returnCommitDevelope() {
		for (Contributor d : getDevelopers()) {
			ControllerSearchCommitDeveloper controllerSearchCommit = new ControllerSearchCommitDeveloper(d);
			controllerSearchCommit.start();
		}
	}

	// Retorna as modificações no arquivos comitados dos desenvolvedores
	public void returnChangesCommit() {
		for (Commit commit : getCommitSHA()) {
			ControllerChangesCommit controller = new ControllerChangesCommit(commit);
			controller.start();
		}
	}

	// Retorna lista de repositórios
	public List<Repository> getRepositories() {
		RepositoriesDAO repositoriesDAO = new RepositoriesDAO();
		return repositoriesDAO.getRepository();
	}

	// Retorna lista de Contributor(desenvolvedores)
	public List<Contributor> getDevelopers() {
		DevelopersRepositoryDAO developersRepositoryDAO = new DevelopersRepositoryDAO();
		return developersRepositoryDAO.getDeveloper();
	}

	// Lista de commits
	public static List<Commit> getCommitSHA() {
		CommitKeyDAO commitKeyDAO = new CommitKeyDAO();
		return commitKeyDAO.getLista();
	}

}
