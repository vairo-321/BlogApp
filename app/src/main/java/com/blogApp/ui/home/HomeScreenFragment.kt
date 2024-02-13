package com.blogApp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.blogApp.R
import com.blogApp.data.model.Post
import com.blogApp.databinding.FragmentHomeScreenBinding
import com.blogApp.ui.home.adapter.HomeScreenAdapter
import com.google.firebase.Timestamp

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        //HardCode
        val postList: List<Post> = listOf(
            Post(
                "https://upload.wikimedia.org/wikipedia/commons/8/8a/DelarocheNapoleon.jpg",
                "Napoleon",
                Timestamp.now(),
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Bouchot_-_Le_general_Bonaparte_au_Conseil_des_Cinq-Cents.jpg/800px-Bouchot_-_Le_general_Bonaparte_au_Conseil_des_Cinq-Cents.jpg"
            ),
            Post(
                "https://cdn.pixabay.com/photo/2024/02/02/13/37/enduro-8548196_1280.jpg",
                "Matt",
                Timestamp.now(),
                "https://cdn.pixabay.com/photo/2023/10/06/15/12/motorcycle-8298499_1280.jpg"
            ),
            Post(
                "https://images.unsplash.com/photo-1707162740880-d814829a0679?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Naty",
                Timestamp.now(),
                "https://images.unsplash.com/photo-1706702511694-35246e0e47d0?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            ),
            Post(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Jos%C3%A9_de_San_Mart%C3%ADn_%28retrato%2C_c.1828%29.jpg/330px-Jos%C3%A9_de_San_Mart%C3%ADn_%28retrato%2C_c.1828%29.jpg",
                "Jose Martin",
                Timestamp.now(),
                "https://upload.wikimedia.org/wikipedia/commons/4/4f/Remedios_de_Escalada.jpg"
            ),
            Post(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Cristina_Fern%C3%A1ndez_de_Kirchner_y_Javier_Milei_%28cropped%29.jpg/330px-Cristina_Fern%C3%A1ndez_de_Kirchner_y_Javier_Milei_%28cropped%29.jpg",
                "Javi Milei",
                Timestamp.now(),
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/La_Libertad_Avanza_lanzamiento.jpg/330px-La_Libertad_Avanza_lanzamiento.jpg"
            ),
        )

        binding.rvHome.adapter = HomeScreenAdapter(postList)
    }

}