package www.sanju.customtabbar.Fragments



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.caanvi.comensal_app_mobile.Login.Activities.EXTRA_RESTAURANTLIST
import com.caanvi.comensal_app_mobile.Login.Activities.FiltroBusqueda
import com.caanvi.comensal_app_mobile.Login.Activities.MapsActivity
import com.caanvi.comensal_app_mobile.Login.Activities.ReservaRestaurante
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.Categorias
import com.caanvi.comensal_app_mobile.Login.Modals.CategoriasResponse
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.Modals.RestaurantResponse
import com.caanvi.comensal_app_mobile.Login.RecyclerView.CenterZoomLayoutManager
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetCategorias.CategoriasAdapter
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant.RestaurantAdapter
import com.caanvi.comensal_app_mobile.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

   // ASI SE UTILIZA EL BINDIN EN FRGMENTS
    private  var _binding : FragmentHomeBinding? = null
    private val binding get()= _binding!!
    /////////////////////////////////////////

    private lateinit var adapter: RestaurantAdapter
    private lateinit var adapter1: CategoriasAdapter
    val restaurantList = mutableListOf<Restaurant>()
    val categoriasList = mutableListOf<Categorias>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // ASI SE UTILIZA EL BINDIN EN FRGMENTS, se cambia algo de la linea de codigo en esta parte
        _binding = FragmentHomeBinding.inflate(inflater, container, false)






        //Cambiar de Vista de un activitie a otro
        binding.btnBusqueda.setOnClickListener(){
            val intent = Intent(requireActivity().applicationContext, FiltroBusqueda::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }




        getRestaurant()
        initRecyclerRestaurant()

        getCategorias()
        initRecyclerCategorias()


        return binding.root
////////////////////////////////////////////////////////////////////////







    }



    fun initRecyclerRestaurant(){
        binding.recyclerRestaurant.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        adapter = RestaurantAdapter(restaurantList, object:RestaurantAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(requireActivity().applicationContext, MapsActivity::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, restaurantList[position])
                startActivity(intent)
            }


            override fun onItemClick1(position: Int) {


                val intent = Intent(requireActivity().applicationContext, ReservaRestaurante::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, restaurantList[position])
                startActivity(intent)


                /*

                //Pasar de fragment a fragment
                val addFragment = AddFragment()
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.ver_fragments , addFragment)
                transaction.commit()
                */

            }



        })
        binding.recyclerRestaurant.adapter = adapter
    }







    fun getRestaurant(){
        RetrofitClient.instance.getRestaurant()
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




    fun initRecyclerCategorias(){


        val linearLayoutManager = CenterZoomLayoutManager(requireActivity().applicationContext)


        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)



        //auto center views
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.isNestedScrollingEnabled = false


        adapter1 = CategoriasAdapter(categoriasList, object:CategoriasAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(requireActivity().applicationContext, MapsActivity::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, categoriasList[position])
                startActivity(intent)
            }


        })
        binding.recyclerView.adapter = adapter1
    }


    fun getCategorias(){
       // LinearLayoutManager(requireActivity().applicationContext).orientation = LinearLayoutManager.HORIZONTAL
        RetrofitClient.instance.getCategorias()
            .enqueue(object: Callback<CategoriasResponse> {

                override fun onFailure(call: Call<CategoriasResponse>, t: Throwable) {
                    Toast.makeText(activity?.applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<CategoriasResponse>, response: Response<CategoriasResponse>) {

                    if(response.body()?.conecto!!){

                        val platosGot : CategoriasResponse? = response.body()
                        val addPlatos = platosGot?.categorias?: emptyList()
                        categoriasList.clear()
                        categoriasList.addAll(addPlatos)
                        adapter1.notifyDataSetChanged()

                    }else{

                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(activity?.applicationContext, "NO hay platos", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }








}




