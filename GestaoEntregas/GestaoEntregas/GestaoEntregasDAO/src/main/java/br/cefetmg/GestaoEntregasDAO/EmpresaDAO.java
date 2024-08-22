/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.GestaoEntregasDAO;

/**
 *
 * @author Aluno
 */

import br.cefetmg.GestaoEntregasEntidades.Empresa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

public class EmpresaDAO {

    public void inserirEmpresa(Empresa empresa) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(empresa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }
    
    public Empresa findById(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Empresa.class, id);
    }

    public List<Empresa> selecionarTodasEmpresas() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaQuery<Empresa> criteria = entityManager.getCriteriaBuilder().createQuery(Empresa.class);
        criteria.select(criteria.from(Empresa.class));
        List<Empresa> empresas = entityManager.createQuery(criteria).getResultList();
        entityManager.close();
        
        return empresas;
    }

    public void excluirEmpresa(int idEmpresa) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Empresa empresa = entityManager.find(Empresa.class, idEmpresa);
            if (empresa != null) {
                entityManager.remove(empresa);
            } else {
                System.out.println("Não foi possível encontrar a empresa com o id '" + idEmpresa + "'");
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public void atualizarEmpresa(Empresa empresa) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Empresa empresaPersistido = entityManager.find(Empresa.class, empresa.getId());
            if (empresaPersistido != null) {
                empresaPersistido.setId(empresa.getId());
                empresaPersistido.setNome(empresa.getNome());
                empresaPersistido.setCpf(empresa.getCpf());
                empresaPersistido.setCnpj(empresa.getCnpj());
                empresaPersistido.setPorcentagemComissaoEntregador(empresa.getPorcentagemComissaoEntregador());
            } else {
                System.out.println("Não foi possível encontrar a empresa com o id '" + empresa.getId() + "'");
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }
}