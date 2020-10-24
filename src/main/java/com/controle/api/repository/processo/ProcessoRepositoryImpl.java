package com.controle.api.repository.processo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.controle.api.dto.ProcessoEstatisticaCategoria;
import com.controle.api.dto.ProcessoEstatisticaDia;
import com.controle.api.dto.ProcessoEstatisticaPessoa;
import com.controle.api.model.Processo;
import com.controle.api.model.Processo_;
import com.controle.api.repository.filter.ProcessoFilter;
import com.controle.api.repository.projection.ResumoProcesso;

public class ProcessoRepositoryImpl implements ProcessoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	// Filtro de categorias por Pessoa do inicio ao final do mês vigente
	@Override
	public List<ProcessoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

		CriteriaQuery<ProcessoEstatisticaPessoa> criteriaQuery = criteriaBuilder
				.createQuery(ProcessoEstatisticaPessoa.class);

		Root<Processo> root = criteriaQuery.from(Processo.class);

		criteriaQuery.select(criteriaBuilder.construct(ProcessoEstatisticaPessoa.class, root.get(Processo_.tipo),
				root.get(Processo_.pessoa), criteriaBuilder.sum(root.get(Processo_.valor))));

		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Processo_.conclusao), inicio),
				criteriaBuilder.lessThanOrEqualTo(root.get(Processo_.conclusao), fim));

		criteriaQuery.groupBy(root.get(Processo_.tipo), root.get(Processo_.pessoa));

		TypedQuery<ProcessoEstatisticaPessoa> typedQuery = manager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	// Filtro de categorias por DIA do inicio ao final do mês vigente
	@Override
	public List<ProcessoEstatisticaDia> porDia(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

		CriteriaQuery<ProcessoEstatisticaDia> criteriaQuery = criteriaBuilder.createQuery(ProcessoEstatisticaDia.class);

		Root<Processo> root = criteriaQuery.from(Processo.class);

		criteriaQuery.select(criteriaBuilder.construct(ProcessoEstatisticaDia.class, root.get(Processo_.tipo),
				root.get(Processo_.conclusao), criteriaBuilder.sum(root.get(Processo_.valor))));

		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Processo_.conclusao), primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(Processo_.conclusao), ultimoDia));

		criteriaQuery.groupBy(root.get(Processo_.tipo), root.get(Processo_.conclusao));

		TypedQuery<ProcessoEstatisticaDia> typedQuery = manager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	// Filtro de categorias por conclusão do inicio ao final do mês vigente
	@Override
	public List<ProcessoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

		CriteriaQuery<ProcessoEstatisticaCategoria> criteriaQuery = criteriaBuilder
				.createQuery(ProcessoEstatisticaCategoria.class);

		Root<Processo> root = criteriaQuery.from(Processo.class);

		criteriaQuery.select(criteriaBuilder.construct(ProcessoEstatisticaCategoria.class,
				root.get(Processo_.categoria), criteriaBuilder.sum(root.get(Processo_.valor))));

		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Processo_.conclusao), primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(Processo_.conclusao), ultimoDia));

		criteriaQuery.groupBy(root.get(Processo_.categoria));

		TypedQuery<ProcessoEstatisticaCategoria> typedQuery = manager.createQuery(criteriaQuery);

		return typedQuery.getResultList();
	}

	// Realizar filtro da pesquisa no processo
	@Override
	public Page<Processo> filtrar(ProcessoFilter processoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Processo> criteria = builder.createQuery(Processo.class);
		Root<Processo> root = criteria.from(Processo.class);

		// criar as restrições
		Predicate[] predicates = criarRestricoes(processoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Processo> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(processoFilter));
	}

	private Predicate[] criarRestricoes(ProcessoFilter processoFilter, CriteriaBuilder builder, Root<Processo> root) {

		List<Predicate> predicates = new ArrayList<>();

		// filtro por data de inicio do processo
		if (processoFilter.getDataInicioDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Processo_.inicio), processoFilter.getDataInicioDe()));
		}

		if (processoFilter.getDataInicioAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Processo_.inicio), processoFilter.getDataInicioAte()));
		}

		// filtro por data de conclusão do processo
		if (processoFilter.getDataConclusaoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Processo_.conclusao), processoFilter.getDataConclusaoDe()));
		}

		if (processoFilter.getDataConclusaoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Processo_.conclusao), processoFilter.getDataConclusaoAte()));
		}

		// filtro por data da venda do processo
		if (processoFilter.getDataVendaDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Processo_.inicio), processoFilter.getDataVendaDe()));
		}

		if (processoFilter.getDataVendaAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Processo_.inicio), processoFilter.getDataVendaAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	// Restrições da Paginação de processos
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(ProcessoFilter processoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Processo> root = criteria.from(Processo.class);

		Predicate[] predicates = criarRestricoes(processoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));

		return manager.createQuery(criteria).getSingleResult();
	}

	@Override
	public Page<ResumoProcesso> resumir(ProcessoFilter processoFilter, Pageable pageable) {
		return null;
	}

}
