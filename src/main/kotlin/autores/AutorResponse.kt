package autores

import java.time.LocalDateTime

class AutorResponse (autor: Autor){
    val nome: String = autor.nome
    val email: String = autor.email
    val descricao: String = autor.descricao
    //val instanteDoCadastro: LocalDateTime = autor.instanteDoCadastro
}