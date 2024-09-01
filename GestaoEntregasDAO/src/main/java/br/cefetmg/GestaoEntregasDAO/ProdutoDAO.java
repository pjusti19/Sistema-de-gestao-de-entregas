/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.GestaoEntregasDAO;

/**
 *
 * @author Aluno
 */


import br.cefetmg.GestaoEntregasEntidades.Produto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

public class ProdutoDAO {

    public void inserirProduto(Produto produto) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(produto);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }
    
    public Produto findById(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Produto.class, id);
    }
    
    public List<Produto> selecionarTodosProdutos() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaQuery<Produto> criteria = entityManager.getCriteriaBuilder().createQuery(Produto.class);
        criteria.select(criteria.from(Produto.class));
        List<Produto> produtos = entityManager.createQuery(criteria).getResultList();
        entityManager.close();
        
        return produtos;
    }

    public void excluirProduto(int idProduto) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Produto produto = entityManager.find(Produto.class, idProduto);
            if (produto != null) {
                entityManager.remove(produto);
            } else {
                System.out.println("Não foi possível encontrar o produto com o id '" + idProduto + "'");
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public void atualizarProduto(Produto produto) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Produto produtoPersistido = entityManager.find(Produto.class, produto.getId());
            if (produtoPersistido != null) {
                produtoPersistido.setId(produto.getId());
                produtoPersistido.setNome(produto.getNome());
                produtoPersistido.setLocalizacao(produto.getLocalizacao());
                
            } else {
                System.out.println("Não foi possível encontrar o produto com o id '" + produto.getId() + "'");
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