package persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(schema="bookstore", name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private long isbn;

	@Column(nullable = false, name = "author")
	private String autor;


	public int getId () {
		return id;
	}


	public void setId (int id) {
		this.id = id;
	}


	public String getTitle () {
		return title;
	}


	public void setTitle (String title) {
		this.title = title;
	}


	public long getIsbn () {
		return isbn;
	}


	public void setIsbn (long isbn) {
		this.isbn = isbn;
	}


	public String getAutor () {
		return autor;
	}


	public void setAutor (String autor) {
		this.autor = autor;
	}


	@Override
	public String toString () {
		return "Book [id=" + id + ", title=" + title + ", isbn=" + isbn + ", autor=" + autor + "]";
	}
}