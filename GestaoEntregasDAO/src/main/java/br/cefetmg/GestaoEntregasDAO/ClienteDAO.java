/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.GestaoEntregasDAO;

/**
 *
 * @author Aluno
 */

import br.cefetmg.GestaoEntregasEntidades.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

public class ClienteDAO {

    public void inserirCliente(Cliente cliente) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }
    
    public Cliente findById(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Cliente.class, id);
    }
    
    public List<Cliente> selecionarTodosClientes() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaQuery<Cliente> criteria = entityManager.getCriteriaBuilder().createQuery(Cliente.class);
        criteria.select(criteria.from(Cliente.class));
        List<Cliente> clientes = entityManager.createQuery(criteria).getResultList();
        entityManager.close();
        
        return clientes;
    }

    public void excluirCliente(int idCliente) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Cliente cliente = entityManager.find(Cliente.class, idCliente);
            if (cliente != null) {
                entityManager.remove(cliente);
            } else {
                System.out.println("Não foi possível encontrar o cliente com o id '" + idCliente + "'");
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public void atualizarCliente(Cliente cliente) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Cliente clientePersistido = entityManager.find(Cliente.class, cliente.getId());
            if (clientePersistido != null) {
                clientePersistido.setId(cliente.getId());
                clientePersistido.setNome(cliente.getNome());
                clientePersistido.setEndereço(cliente.getEndereço());
                clientePersistido.setBairro(cliente.getBairro());
                clientePersistido.setTelefone(cliente.getTelefone());
                clientePersistido.setCpf(cliente.getCpf());
                clientePersistido.setCnpj(cliente.getCnpj());
            } else {
                System.out.println("Não foi possível encontrar o cliente com o id '" + cliente.getId() + "'");
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
