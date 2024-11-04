package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.GuideDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.Category;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GuideDAO implements IDAO<GuideDTO, Integer> {

    private static GuideDAO instance;
    private static EntityManagerFactory emf;

    public static GuideDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuideDAO();
        }
        return instance;
    }

    @Override
    public GuideDTO read(Integer integer) throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, integer);
            return guide != null ? new GuideDTO(guide) : null;
        }
    }

    @Override
    public List<GuideDTO> readAll() throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            TypedQuery<GuideDTO> query = em.createQuery("SELECT new dat.dtos.GuideDTO(g) FROM Guide g", GuideDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = new Guide(guideDTO);
            em.persist(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);
        }
    }

    @Override
    public GuideDTO update(Integer integer, GuideDTO guideDTO) throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, integer);
            if(guide == null) {
                return null;
            }
            guide.setEmail(guideDTO.getEmail());
            guide.setFirstname(guideDTO.getFirstname());
            guide.setLastname(guideDTO.getLastname());
            guide.setPhone(guideDTO.getPhone());
            guide.setYearsOfExperience(guideDTO.getYearsOfExperience());
            Guide updatedGuide = em.merge(guide);
            em.getTransaction().commit();
            return new GuideDTO(updatedGuide);

        }
    }

    @Override
    public void delete(Integer integer) throws ApiException {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, integer);
            if(guide != null) {
                em.remove(guide);
            }
            em.getTransaction().commit();
        }
    }
}
