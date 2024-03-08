	package com.generation.blogPessoal.model;
	
	import java.time.LocalDateTime;
	
	import org.hibernate.annotations.UpdateTimestamp;
	
	import jakarta.persistence.Entity;
	import jakarta.persistence.Table;
	import jakarta.validation.constraints.NotBlank;
	import jakarta.validation.constraints.Size;
	import jakarta.persistence.Id;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	
	
	
	
		@Entity
		@Table(name = "tb_postagens")
		public class Postagem {
		
			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private Long id;
			
			@NotBlank(message = "o Atriburo titulo é Obrigatório")	
			@Size(min =5, max= 100 , message ="O atriburo deve conter no mínimo 05 e no máximo 100 caracteres")
			private String titulo;
			
			
			@NotBlank(message = "o Atriburo texto é Obrigatório")	
			@Size(min =50, message ="O atriburo deve conter no mínimo 05 caracteres")
			private String texto;
			
			@UpdateTimestamp
			private LocalDateTime data;
		
		public Long getId() {
			return id;
		}
	
		public void setId(Long id) {
			this.id = id;
		}
	
		public String getTitulo() {
			return titulo;
		}
	
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
	
		public String getTexto() {
			return texto;
		}
	
		public void setTexto(String texto) {
			this.texto = texto;
		}
	
		public LocalDateTime getData() {
			return data;
		}
	
		public void setData(LocalDateTime data) {
			this.data = data;
		}
		
		
		
		
		
		
	}
