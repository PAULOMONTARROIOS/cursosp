package com.prm.cursosp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prm.cursosp.domain.Categoria;
import com.prm.cursosp.repositories.CategoriaRepository;
import com.prm.cursosp.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria find(int id) {
		Categoria obj = categoriaRepository.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado. Id: " + id + ", Tipo : " + Categoria.class.getName());
		}
		return obj;
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null); // Garante que o objeto tem ID nulo e com isso vai se tratar de uma inserção
		return categoriaRepository.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId()); // Busca o objeto no banco, caso não exista já lança uma exceção do método find
		return categoriaRepository.save(obj);
	}
}
