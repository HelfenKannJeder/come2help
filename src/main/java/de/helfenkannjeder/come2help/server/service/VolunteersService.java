package de.helfenkannjeder.come2help.server.service;

import com.google.common.collect.Lists;
import de.helfenkannjeder.come2help.server.domain.Volunteer;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VolunteersService {

    public VolunteersService() {
    }

    public List<Volunteer> findAll() {
        return Lists.newArrayList();
    }

    public Volunteer findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Volunteer updateVolunteer(Volunteer volunteer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteVolunteer(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
