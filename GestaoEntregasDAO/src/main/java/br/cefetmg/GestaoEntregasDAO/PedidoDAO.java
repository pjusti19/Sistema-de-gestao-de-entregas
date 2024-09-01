/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.GestaoEntregasDAO;


import br.cefetmg.GestaoEntregasEntidades.Pedido;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

public class PedidoDAO {

    public void inserirPedido(Pedido pedido) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pedido);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }
    
    public Pedido findById(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Pedido.class, id);
    }
    
    public List<Pedido> selecionarTodosPedidos() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaQuery<Pedido> criteria = entityManager.getCriteriaBuilder().createQuery(Pedido.class);
        criteria.select(criteria.from(Pedido.class));
        List<Pedido> pedidos = entityManager.createQuery(criteria).getResultList();
        entityManager.close();
        
        return pedidos;
    }

    public void excluirPedido(int idPedido) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Pedido pedido = entityManager.find(Pedido.class, idPedido);
            if (pedido != null) {
                entityManager.remove(pedido);
            } else {
                System.out.println("Não foi possível encontrar o pedido com o id '" + idPedido + "'");
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public void atualizarPedido(Pedido pedido) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.cefetmg_GestaoEntregasDAO_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Pedido pedidoPersistido = entityManager.find(Pedido.class, pedido.getId());
            if (pedidoPersistido != null) {
                pedidoPersistido.setId(pedido.getId());
                pedidoPersistido.setNomeProduto(pedido.getNomeProduto());
                pedidoPersistido.setQuantidade(pedido.getQuantidade());
                pedidoPersistido.setValorUnitario(pedido.getValorUnitario());
                pedidoPersistido.setValorTotal(pedido.getValorTotal());
                pedidoPersistido.setMarca(pedido.getMarca());
                pedidoPersistido.setFormaPagamento(pedido.getFormaPagamento());
                pedidoPersistido.setStatus(pedido.getStatus());
                
            } else {
                System.out.println("Não foi possível encontrar o pedido com o id '" + pedido.getId() + "'");
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
