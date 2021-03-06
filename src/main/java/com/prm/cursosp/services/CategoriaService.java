package com.prm.cursosp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.prm.cursosp.domain.Categoria;
import com.prm.cursosp.dto.CategoriaDTO;
import com.prm.cursosp.repositories.CategoriaRepository;
import com.prm.cursosp.services.exceptions.DataIntegrityException;
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
		Categoria newObj = find(obj.getId()); // Busca o objeto no banco, caso não exista já lança uma exceção do método find
		updateData(newObj,obj);
		return categoriaRepository.save(newObj);
	}

	public void delete(Integer id) {
		Categoria obj = find(id);

		try {
			categoriaRepository.delete(obj.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possue produtos");
		}
	}

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
	
}
