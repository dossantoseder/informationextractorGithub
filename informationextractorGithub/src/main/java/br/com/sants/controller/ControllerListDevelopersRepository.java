package br.com.sants.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.sants.service.RetrofitLauncher;
import br.com.sants.service.ServiceRepository;
import br.com.sants.util.EventManager;
import br.com.sants.util.GenerateXLS;
import br.com.sants.model.Contributor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerListDevelopersRepository implements Callback<List<Contributor>> {
	//private GenerateXLS generateXLS = new GenerateXLS();
	private GenerateXLS generateXLS;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet = null;
	private HSSFRow row;
	public String repository;
	public br.com.sants.util.EventManager events;
	private int count = 0;
	
	public ControllerListDevelopersRepository(String repository){
		this.repository = repository;
		this.events = new EventManager("getdevelopers", "getrepository");
		workbook = new HSSFWorkbook();
		generateXLS = new GenerateXLS(workbook);
	} 
	//private String fullName = "MohGovIL"; 
	//private String repository = "rn-contact-tracing";
	//private int page = 1;
	private String API_VERSION_SPEC = "application/vnd.github.v3+json";
	private String accessToken  = "9ab5ff381ba2c6510a5662b8de793f0b123cb939"; 
	private String repo;

    public void start(String owner, String repository, Map<String, String> mapaNomes) {
    	//workbook = generateXLS.getInstance();
    	int perPage = 100;
    	
    	//events.addObserver("getdevelopers", new ControllerSearchDeveloper());
    	
    	Iterator<String> itr2 = mapaNomes.keySet().iterator();
    	while (itr2.hasNext()) {
    		String kOwner = itr2.next();
    		String vRepository = mapaNomes.get(kOwner);
    		System.out.println(kOwner + " = " + vRepository);
    		this.repository = vRepository;
    		
    		//System.out.println("CONTADOR: "+this.count);
    		//this.count++;
    	}
			  ServiceRepository  serviceRepository = new RetrofitLauncher().getContributorsRepository();
			  
			  Call<List<Contributor>>  call = serviceRepository.listContributorsRepository(accessToken, API_VERSION_SPEC, owner, repository, perPage);
			  //Call<List<Contributor>>  call = serviceRepository.listContributorsRepository(fullName, repository, page, perPage);
			  
			  call.enqueue(this);
			  
			//  this.count++;
			  
		
    }

    @Override
    public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
    	System.out.println("URL: " + response.raw().request().url()+ "\n");
    	//String filename = "/home/pereira/Documentos/dados_git/list_developers/"+repository+"_listDevelopersRepository_"+page+".xls";
    	String filename = "/home/pereira/Documentos/dados_git/tests/"+this.repository+"_listDevelopersRepository.xls";
    	int iterator = 1;
    	System.out.println("TOTAL: "+ response.body().size());
    	int val = response.body().size() - 1;
    	
        if(response.isSuccessful()) {
            List<Contributor> listDeveloper = response.body();
            sheet = generateXLS.openXLS(rowhead(), this.repository+val);
            fillLine(listDeveloper, iterator);
			generateXLS.createXLS(filename);
            //listDeveloper.forEach(developers -> System.out.println(developers.toString()));
			events.notifyObservers("getdevelopers");
            
        } else {
            System.out.println("Error" + response.errorBody());
        }
        System.out.println("CONTADOR FIM: "+ this.count);
        /*if(count == 2) {
        	generateXLS.createXLS(filename);
        	events.notifyObservers("getdevelopers");
        }*/
    }

    @Override
    public void onFailure(Call<List<Contributor>> call, Throwable t) {
        t.printStackTrace();
    }
    
    public void fillLine(List<Contributor> listDeveloper, int iterator) {
		for (Contributor developer : listDeveloper) {
			row = sheet.createRow((short) iterator);
			
			row.createCell(0).setCellValue(this.repository);
			row.createCell(1).setCellValue(developer.getId());
			row.createCell(2).setCellValue(developer.getLogin());
			row.createCell(3).setCellValue(developer.getContributions());
			//row.createCell(4).setCellValue(developer.getRepos_url());

			iterator++;
		}
	}
    
    public Map<Integer, String> rowhead() {
		Map<Integer, String> map = new HashMap<Integer, String>();

		map.put(0, "Project");
		map.put(1, "Identifier");
		map.put(2, "Name");
		map.put(3, "Contributions");
		//map.put(4, "Repositories URL");

		return map;
	}
}
