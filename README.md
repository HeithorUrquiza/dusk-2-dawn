### Diagrama de Classes
```mermaid
classDiagram
    class Jogo {
    Long id
    String titulo
    String descricao
    String genero
    double preco
    Desenvolvedor desenvolvedor
}

    class Desenvolvedor {
        Long id
        String nome
        String website
    }

    Jogo "1" -- "N" Desenvolvedor
```
