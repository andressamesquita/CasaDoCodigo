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

    @Put("/{id}")
    @Transactional
    fun atualiza(@PathVariable id: Long, descricao: String): HttpResponse<Any>{
        //buscar o objeto no banco
        val possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }
        val autor = possivelAutor.get()

        //alterar a descricao
        autor.descricao = descricao

        //atualizar o objeto no banco
        autorRepository.update(autor) //com a anotacao transactional, se essa linha for excluida, a atualizacao sera feita implicitamente
        //pois a entidade encontra-se gerenciavel pela JPA -- ciclo de vida

        //retorna uma resposta de que tudo correu bem
        return HttpResponse.ok(AutorResponse(autor))
    }

    @Delete("/{id}")
    @Transactional
    fun deleta(@PathVariable id: Long): HttpResponse<Any>{
        var possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }
        autorRepository.deleteById(id)
        return HttpResponse.ok()
    }


}