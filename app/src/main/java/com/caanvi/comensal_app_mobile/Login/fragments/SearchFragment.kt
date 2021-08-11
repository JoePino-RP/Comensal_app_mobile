package www.sanju.customtabbar.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.Modals.RestaurantResponse
import com.caanvi.comensal_app_mobile.Login.RecyclerView.SearchRestaurant.SearchRestaurantAdapter
import com.caanvi.comensal_app_mobile.databinding.FragmentSearchBinding





/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() , SearchView.OnQueryTextListener {

    // ASI SE UTILIZA EL BINDIN EN FRGMENTS
    private  var _binding : FragmentSearchBinding? = null
    private val binding get()= _binding!!
    /////////////////////////////////////////

    private lateinit var adapter: SearchRestaurantAdapter
    val restaurantList = mutableListOf<Restaurant>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        // ASI SE UTILIZA EL BINDIN EN FRGMENTS, se cambia algo de la linea de codigo en esta parte
        //return inflater.inflate(R.layout.fragment_search, container, false)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)



        binding.txtBusqueda.setOnQueryTextListener(this)
        initRecyclerRestaurant()


        return binding.root
    }






    fun initRecyclerRestaurant(){
        binding.recyclerRestaurant.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        adapter = SearchRestaurantAdapter(restaurantList)
        binding.recyclerRestaurant.adapter = adapter


    }

    fun searchRestaurantByName(query: String){
        //var sto:String = "Pampa"


        RetrofitClient.instance.searchRestaurant(query)
            .enqueue(object: retrofit2.Callback<RestaurantResponse> {

                override fun onFailure(call: retrofit2.Call<RestaurantResponse>, t: Throwable) {
                    Toast.makeText(requireActivity().applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: retrofit2.Call<RestaurantResponse>, response: retrofit2.Response<RestaurantResponse>) {

                    if(response.body()?.conecto!!){

                        val restaurantGot : RestaurantResponse? = response.body()
                        val addRestaurant = restaurantGot?.restaurant?: emptyList()
                        restaurantList.clear()
                        restaurantList.addAll(addRestaurant)
                        adapter.notifyDataSetChanged()

                    }else{

                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(requireActivity().applicationContext, "NO hay restaurantes", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }






    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchRestaurantByName(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return true
    }




}
