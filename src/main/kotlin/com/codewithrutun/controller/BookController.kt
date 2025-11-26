package com.codewithrutun.controller

import com.codewithrutun.library.entity.AddNewBookRequest
import com.codewithrutun.library.entity.Author
import com.codewithrutun.library.entity.Book
import com.codewithrutun.library.entity.BookResponse
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

// test

@Path("/hello")
class BookController {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    suspend fun hello(): String = "Hello to my new Kotlin Programming World!"

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun helloJson(): Map<String, String> =
        mapOf(
            "message" to "Hello to my new Kotlin Programming World!",
            "language" to "Kotlin",
            "framework" to "Quarkus",
        )

    @POST
    @Path("/addNewBook")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun addNewBook(request: AddNewBookRequest): BookResponse {
        val id = nextBookId++

        val author = Author(name = request.bookAuthorName)
        val book =
            Book(
                title = request.bookTitle,
                author = author,
            )

        books.add(book)

        return BookResponse(
            bookId = id,
            bookName = book.title,
            bookAuthor = book.author.name,
        )
    }

    @GET
    @Path("/getAllBooks")
    @Produces(MediaType.APPLICATION_JSON)
    fun showAllBooks(): List<BookResponse> {
        return books.mapIndexed {
                index, book ->
            BookResponse(
                bookId = index + 1,
                bookName = book.title,
                bookAuthor = book.author.name,
            )
        }
    }

    @GET
    @Path("/getBookInformation/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun showBookInfoUsingId(
        @PathParam("id") id: Int,
    ): BookResponse {
        val index = id - 1
        if (index < 0 || index >= books.size) {
            throw NotFoundException("Book with id $id not found")
        }

        val book = books[index]
        return BookResponse(
            bookId = id,
            bookName = book.title,
            bookAuthor = book.author.name,
        )
    }

    companion object {
        private val books = mutableListOf<Book>()
        private var nextBookId = 1
    }
}
