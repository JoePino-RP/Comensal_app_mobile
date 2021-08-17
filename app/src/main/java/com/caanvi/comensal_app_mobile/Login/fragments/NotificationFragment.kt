package www.sanju.customtabbar.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.caanvi.comensal_app_mobile.Login.Activities.EXTRA_RESTAURANTLIST
import com.caanvi.comensal_app_mobile.Login.Activities.MapsActivity
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.Modals.RestaurantResponse
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetFavorites.FavoriteAdapter
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant.RestaurantAdapter
import com.caanvi.comensal_app_mobile.Login.Storage.usuarioData
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityPruebasTodoBinding
import com.caanvi.comensal_app_mobile.databinding.FragmentHomeBinding
import com.caanvi.comensal_app_mobile.databinding.FragmentNotificationBinding
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {

    private  var _binding : FragmentNotificationBinding? = null
    private val binding get()= _binding!!

    //Llenar nuestras listas
    private lateinit var adapter: FavoriteAdapter
    val restaurantList = mutableListOf<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        //getFavorites(usuarioData.idGeneral)
        getFavorites("1")
        initRecyclerRestaurant()

        return binding.root
    }


    fun initRecyclerRestaurant(){
        binding.recyclerRestaurant.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = FavoriteAdapter(restaurantList, object:FavoriteAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(requireActivity().applicationContext, MapsActivity::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, restaurantList[position])
                startActivity(intent)
            }
            override fun onItemClick1(position: Int) {
                val intent = Intent(requireActivity().applicationContext, ActivityPruebasTodoBinding::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, restaurantList[position])
                startActivity(intent)
            }
        })
        binding.recyclerRestaurant.adapter = adapter
    }

    fun getFavorites(id:String){
        RetrofitClient.instance.getFavorite(id)
            .enqueue(object: Callback<RestaurantResponse> {

                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                    Toast.makeText(requireActivity().applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RestaurantResponse>, response: Response<RestaurantResponse>) {

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


}
