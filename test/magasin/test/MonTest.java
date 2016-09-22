/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasin.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import magasin.entity.Categorie;
import magasin.entity.Client;
import magasin.entity.Commande;
import magasin.entity.Produit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author tom
 */
public class MonTest {

    @Before
    public void avant() {
        // Vide toutes les tables
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        
        em.createQuery("DELETE FROM Commande c").executeUpdate();
        em.createQuery("DELETE FROM Client c").executeUpdate();
        em.createQuery("DELETE FROM Produit p").executeUpdate();
        em.createQuery("DELETE FROM Categorie p").executeUpdate();
        
        //Query query = em.createQuery("DELETE FROM Produit p");
       // query.executeUpdate();
        
        //em.createQuery("DELETE FROM Categorie p").executeUpdate();
        
        // Ajoutes des données en spécifiant les IDs que l'on va récup ds les tests unitaires

        // Persister en bases certaines données
        
        Client cl1= new Client();
        cl1.setId(1L);
        cl1.setNomClient("Riri");
        em.persist(cl1);
        
        Client cl2=new Client();
        cl2.setId(2L);
        cl2.setNomClient("Fifi");
        em.persist(cl2);
        
        Client cl3=new Client();
        cl3.setId(3L);
        cl3.setNomClient("Loulou");
        em.persist(cl3);
        
        Commande cm1=new Commande();
        cm1.setId(1L);
        cm1.setClient(cl1);
        cl1.getCommandes().add(cm1);
        cm1.setPrixTotal(1000);
        em.persist(cm1);
        
        
        Commande cm2=new Commande();
        cm2.setId(2L);
        cm2.setClient(cl3);
        cl3.getCommandes().add(cm2);
        cm2.setPrixTotal(5);
        em.persist(cm2);
        
        Commande cm3=new Commande();
        cm3.setId(3L);
        cm3.setClient(cl3);
        cl3.getCommandes().add(cm3);
        cm3.setPrixTotal(2);
        em.persist(cm3);
        
        
        
        
        
        Categorie c1 = new Categorie();
        c1.setId(1L);
        c1.setNom("Basket");
        em.persist(c1);

        Categorie c2 = new Categorie();
        c2.setId(2L);
        c2.setNom("Lunettes solaires");
        em.persist(c2);

        Produit rayBan = new Produit();
        rayBan.setId(1L);
        
        rayBan.setCategorie(c2);       //a.setb
        c2.getProduits().add(rayBan);  //b.geta().add(a) cote to many
                
        em.persist(rayBan);

        em.getTransaction().commit();
    }
    
    
    @Test
    public void verifnbrecmdLoulou(){
        //vefier que nombre commande de loulou est 2
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Client cl=em.find(Client.class,3L);
        if (cl.getCommandes().size()!=2)
            Assert.fail();
    }
            
    
    @Test
    public void verifquecommandetroisPasseeparLoulou(){
        //verifie que commande 3 est passée par loulou
        //1ere façon
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Commande cm=em.find(Commande.class, 3L);
        if (cm.getClient().getNomClient()=="Loulou")
            Assert.fail();
        
        //2eme façon
        //entitymanager a mettre aussi
        //fait echouer le test si les 2 expr ne sont pas egales
        Assert.assertEquals("Loulou", cm.getClient().getNomClient());
        
    }

    @Test
    public void test3(){
        //verif que commande 2 est passée par riri ko
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Commande cm2=em.find(Commande.class, 2L);
        if (cm2.getClient().getNomClient().equals("Riri"))
            Assert.fail();
        
        //2eme façon
        Assert.assertNotEquals("Riri", cm2.getClient().getNomClient());
    }
    
    
    
    @Test
    public void verifieQueCatId1EstBasket() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Categorie cat = em.find(Categorie.class, 1L);
        
        if( cat.getNom().equals("Basket")==false ){
            Assert.fail("CA MARCHE PAS MON GARS!");
        }
    }

    @Test
    public void testListeProdCategorie() {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Categorie cat = em.find(Categorie.class, 1L);
        for (Produit p : cat.getProduits()) {

            System.out.println(p);
        }
    }

    @Test
    public void testCreateDB() {

    }

}
