package br.com.sants.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.sants.data.DevelopersDAO;
import br.com.sants.model.Contributor;
import br.com.sants.model.User;
import br.com.sants.service.IServiceDeveloper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerDeveloper implements Callback<User> {
	private Contributor developer;
	private String API_VERSION_SPEC;
	private String ACCESSTOKEN;

	static final String BASE_URL = "https://api.github.com/";
	
	public ControllerDeveloper(Contributor d){
		this.developer = d;
		this.API_VERSION_SPEC = "application/vnd.github.v3+json";
		this.ACCESSTOKEN  = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	}

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IServiceDeveloper gerritAPI = retrofit.create(IServiceDeveloper.class);

        Call<User> call = gerritAPI.loadChanges(ACCESSTOKEN, API_VERSION_SPEC, this.developer.getLogin());
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
    	DevelopersDAO developersDAO = new DevelopersDAO();
    	System.out.println("URL: " + response.raw().request().url() + "\n");
        if(response.isSuccessful()) {
            User user = response.body();
            	//System.out.println("Identifilde: "+user.getId()+" Name: "+ user.getLogin()+" Name: "+ user.getCreated_at());
            	developersDAO.add(user);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        t.printStackTrace();
    }
}


