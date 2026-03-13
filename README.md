# 📚 Sistema de Gestão de Biblioteca Online
### Spring Boot + Spring MVC + Spring Data JPA + Thymeleaf + H2

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Passos

1. **Abrir o terminal na pasta do projecto**

2. **Compilar e executar:**
```bash
mvn spring-boot:run
```

3. **Abrir no browser:**
```
http://localhost:8080
```

4. **Aceder à base de dados H2 (opcional):**
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:bibliotecadb
Username: sa
Password: (deixar vazio)
```

---

## 🗂️ Estrutura do Projecto (Arquitectura MVC)

```
src/
├── main/
│   ├── java/com/biblioteca/
│   │   ├── BibliotecaApplication.java     ← Classe principal
│   │   ├── model/                          ← CAMADA MODEL
│   │   │   ├── Livro.java
│   │   │   ├── Utilizador.java
│   │   │   └── Emprestimo.java
│   │   ├── repository/                     ← ACESSO À BASE DE DADOS
│   │   │   ├── LivroRepository.java
│   │   │   ├── UtilizadorRepository.java
│   │   │   └── EmprestimoRepository.java
│   │   ├── service/                        ← LÓGICA DE NEGÓCIO
│   │   │   ├── LivroService.java
│   │   │   ├── UtilizadorService.java
│   │   │   └── EmprestimoService.java
│   │   └── controller/                     ← CAMADA CONTROLLER
│   │       ├── HomeController.java
│   │       ├── LivroController.java
│   │       ├── UtilizadorController.java
│   │       └── EmprestimoController.java
│   └── resources/
│       ├── templates/                      ← CAMADA VIEW (HTML)
│       │   ├── index.html                  ← Dashboard
│       │   ├── livros/
│       │   │   ├── lista.html
│       │   │   └── formulario.html
│       │   ├── utilizadores/
│       │   │   ├── lista.html
│       │   │   └── formulario.html
│       │   └── emprestimos/
│       │       ├── lista.html
│       │       └── formulario.html
│       └── application.properties          ← Configuração BD
```

---

## ⚙️ Funcionalidades

| Módulo | Funcionalidade |
|--------|----------------|
| Livros | Cadastrar, listar, editar, eliminar, pesquisar |
| Utilizadores | Cadastrar, listar, editar, eliminar |
| Empréstimos | Registar empréstimo, registar devolução |
| Dashboard | Estatísticas em tempo real |

---

## 🏛️ Arquitectura MVC com Spring

| Camada | Tecnologia | Função |
|--------|-----------|--------|
| Model | JPA / Hibernate | Representa os dados e mapeia para BD |
| View | Thymeleaf (HTML) | Interface que o utilizador vê |
| Controller | Spring MVC | Recebe pedidos e coordena resposta |
| Repositório | Spring Data JPA | Acesso à base de dados sem SQL |
| Base de Dados | H2 (em memória) | Guarda todos os dados |

---

## 📌 Notas
- A base de dados H2 é **em memória** — os dados perdem-se ao reiniciar
- Para usar MySQL, alterar o `application.properties` com as credenciais MySQL
- O prazo de devolução é automaticamente definido em **14 dias**
