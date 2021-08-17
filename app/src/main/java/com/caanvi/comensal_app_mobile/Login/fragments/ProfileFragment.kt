package www.sanju.customtabbar.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.caanvi.comensal_app_mobile.Login.Activities.MainActivity
import com.caanvi.comensal_app_mobile.Login.SQLite.DatabaseHelper
import com.caanvi.comensal_app_mobile.Login.Storage.usuarioData
import com.caanvi.comensal_app_mobile.databinding.FragmentProfileBinding


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    // ASI SE UTILIZA EL BINDIN EN FRGMENTS
    private  var _binding : FragmentProfileBinding? = null
    private val binding get()= _binding!!
    /////////////////////////////////////////

    lateinit var handler: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        handler = DatabaseHelper(requireActivity().applicationContext)

        binding.logout.setOnClickListener(){
            if(usuarioData.idGeneral != "")
            {
                eliminarSQLite(usuarioData.idGeneral)
            }
            else{
                val intent = Intent(requireActivity().applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(usuarioData.idGeneral != "")
        {
            binding.logout.text = usuarioData.emailGeneral
        }
    }

    fun eliminarSQLite(id:String){
        handler.deleteDB(id)
        usuarioData.idGeneral = ""
        val intent = Intent(requireActivity().applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


}
