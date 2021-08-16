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
import com.caanvi.comensal_app_mobile.Login.Modals.Platos
import com.caanvi.comensal_app_mobile.Login.Modals.PlatosResponse
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetPlatos.PlatosAdapter
import com.caanvi.comensal_app_mobile.databinding.FragmentAddBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class AddFragment : Fragment() {

    // ASI SE UTILIZA EL BINDIN EN FRGMENTS
    private  var _binding : FragmentAddBinding? = null
    private val binding get()= _binding!!
    /////////////////////////////////////////



    private lateinit var adapter1: PlatosAdapter
    val platosList = mutableListOf<Platos>()

    private lateinit var restaurante :  Restaurant

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // ASI SE UTILIZA EL BINDIN EN FRGMENTS, se cambia algo de la linea de codigo en esta parte
        _binding = FragmentAddBinding.inflate(inflater, container, false)


        ////////////////////////////////////////
        //val intent = Intent()

         //restaurante = intent.getSerializableExtra(EXTRA_RESTAURANTLIST) as Restaurant
        //////////////////////////////////////////

        val intent = arguments
        if (intent !=null) {
            restaurante = intent.getSerializable(EXTRA_RESTAURANTLIST) as Restaurant
        }


        getPlatos("1") //restaurante.id_res//
        initRecyclerPlatos()

        return binding.root
    }

    fun initRecyclerPlatos(){
        binding.recyclerRestaurant.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        adapter1 = PlatosAdapter(platosList, object:PlatosAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(requireActivity().applicationContext, MapsActivity::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, platosList[position])
                startActivity(intent)
            }
        })
        binding.recyclerRestaurant.adapter = adapter1
    }


    fun getPlatos(id: String){ //id : String
        RetrofitClient.instance.getPlatos(id) //id
            .enqueue(object: Callback<PlatosResponse> {

                override fun onFailure(call: Call<PlatosResponse>, t: Throwable) {
                    Toast.makeText(activity?.applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<PlatosResponse>, response: Response<PlatosResponse>) {

                    if(response.body()?.conecto!!){

                        val platosGot : PlatosResponse? = response.body()
                        val addPlatos = platosGot?.platos?: emptyList()
                        platosList.clear()
                        platosList.addAll(addPlatos)
                        adapter1.notifyDataSetChanged()

                    }else{

                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(activity?.applicationContext, "NO hay platos", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }




}
