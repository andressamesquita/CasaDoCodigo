package autores

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity //eh por padrao uma Introspected
class Autor (val nome:String,
             val email:String,
             val descricao: String){

    @Id
    @GeneratedValue
    var id: Long? = null
    var instanteDoCadastro: LocalDateTime = LocalDateTime.now()
}