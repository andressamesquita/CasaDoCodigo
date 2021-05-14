package autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.transaction.Transactional

import javax.validation.Valid

@Validated
@Controller("/autores")
class AutoresController (@Inject val autorRepository: AutorRepository){

    @Post
    @Transactional
    fun cadastra(@Valid @Body request: AutorRequest): HttpResponse<Any> {
        val autor: Autor = request.paraAutor()
        autorRepository.save(autor)
        return HttpResponse.ok()
    }

    @Get
    @Transactional
    fun lista(@QueryValue(defaultValue = "") email: String): HttpResponse<Any>{
        if (email.isBlank()){
            val autores = autorRepository.findAll()
            val resposta = autores.map {autor -> AutorResponse(autor)}
            return HttpResponse.ok(resposta)
        }
        val possivelAutor = autorRepository.findByEmail(email)
        if (possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }
        val autor = possivelAutor.get()
        return HttpResponse.ok(AutorResponse(autor))
    }


}