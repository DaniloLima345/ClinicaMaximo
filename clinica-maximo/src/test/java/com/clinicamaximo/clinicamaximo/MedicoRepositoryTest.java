package com.clinicamaximo.clinicamaximo;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clinicamaximo.clinicamaximo.model.Medico;
import com.clinicamaximo.clinicamaximo.repository.MedicoRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MedicoRepositoryTest {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Test
	public void deveriaRetornarUmaListaDeMedicosPorEspecialidade() {
		String especialidade = "Testologista";
		List<Medico> medicos = medicoRepository.findByEspecialidade(especialidade, PageRequest.of(0, 1))
				.get().getContent();
		
		Assert.assertNotNull(medicos);
		Assert.assertEquals(especialidade, medicos.get(0).getEspecialidade());
	}
}
