/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.GestaoEntregasDAO;

/**
 *
 * @author Aluno
 */

import br.cefetmg.GestaoEntregasEntidades.Funcionario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

public class FuncionarioDAO {

    public void inserirFuncionario(Funcionario funcionario) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(funcionario);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }
    
    public Funcionario findById(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Funcionario.class, id);
    }
    
    public List<Funcionario> selecionarTodosFuncionarios() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaQuery<Funcionario> criteria = entityManager.getCriteriaBuilder().createQuery(Funcionario.class);
        criteria.select(criteria.from(Funcionario.class));
        List<Funcionario> funcionarios = entityManager.createQuery(criteria).getResultList();
        entityManager.close();
        
        return funcionarios;
    }

    public void excluirFuncionario(int idFuncionario) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Funcionario funcionario = entityManager.find(Funcionario.class, idFuncionario);
            if (funcionario != null) {
                entityManager.remove(funcionario);
            } else {
                System.out.println("Não foi possível encontrar a funcionario com o id '" + idFuncionario + "'");
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public void atualizarFuncionario(Funcionario funcionario) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Funcionario funcionarioPersistido = entityManager.find(Funcionario.class, funcionario.getId());
            if (funcionarioPersistido != null) {
                funcionarioPersistido.setId(funcionario.getId());
                funcionarioPersistido.setNome(funcionario.getNome());
                funcionarioPersistido.setSenha(funcionario.getSenha());
                funcionarioPersistido.setTelefone(funcionario.getTelefone());
                funcionarioPersistido.setPerfil(funcionario.getPerfil());
                
            } else {
                System.out.println("Não foi possível encontrar a funcionario com o id '" + funcionario.getId() + "'");
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