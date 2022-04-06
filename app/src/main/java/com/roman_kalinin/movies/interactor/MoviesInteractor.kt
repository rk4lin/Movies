package com.roman_kalinin.movies.interactor

import com.roman_kalinin.movies.database.MoviesDataBase
import com.roman_kalinin.movies.database.entity.Movies
import com.roman_kalinin.movies.domain.Movie
import com.roman_kalinin.movies.repository.MainRepository
import retrofit2.Response
import javax.inject.Inject


class MoviesInteractor @Inject constructor(
    private val repository: MainRepository,
    private val dataBase: MoviesDataBase
) {

    suspend fun getMovies(fromCache: Boolean) : List<Movie>?{

      if(fromCache) {
          val result =  getMoviesFromDatabase()
          if(result != null && result.isNotEmpty()){
              return result
          }
          else{
              val resultNetwork = getMoviesFromRepository()
              if(resultNetwork.isSuccessful && resultNetwork.body() != null){
                  val movies = resultNetwork.body()!!.map{
                      Movies(
                          name = it.name,
                          imageUrl = it.imageUrl,
                          category = it.category,
                          desc = it.desc
                      )
                  }
                  dataBase.moviesDao().insertMovies(movies)
                  return resultNetwork.body()
              }
          }
        }
        else {
            val result = getMoviesFromRepository()
            if(result.isSuccessful && result.body() != null){
                return result.body()
            }
        }
      return null
    }

    private suspend fun getMoviesFromRepository() : Response<List<Movie>> {
        return repository.getMovies()
    }

    private fun getMoviesFromDatabase() : List<Movie>? {
     val pep = dataBase.moviesDao().getMovies()
       val result = pep?.map{
            Movie(
                name = it.name,
                imageUrl = it.imageUrl,
                category = it.category,
                desc = it.desc
            )
        }
        return result
    }


}